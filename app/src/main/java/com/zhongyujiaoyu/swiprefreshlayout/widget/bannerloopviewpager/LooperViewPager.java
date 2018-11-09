package com.zhongyujiaoyu.swiprefreshlayout.widget.bannerloopviewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lf_lv
 * @date 2018/10/30
 * Description: 无限循环ViewPager
 */

public class LooperViewPager extends ViewPager {

    private LooperAdapter looperAdapter;
    private List<OnPageChangeListener> mOnPageChangeListeners;

    public LooperViewPager(@NonNull Context context) {
        this(context, null);
    }

    public LooperViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        if (mOnPageChangeListener != null) {
            super.removeOnPageChangeListener(mOnPageChangeListener);
        }
        super.addOnPageChangeListener(mOnPageChangeListener);
    }

    @Override
    public void setAdapter(@Nullable PagerAdapter adapter) {
        looperAdapter = new LooperAdapter(adapter);
        super.setAdapter(looperAdapter);
        setCurrentItem(0, false);
    }

    @Nullable
    @Override
    public PagerAdapter getAdapter() {
        return looperAdapter;
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, true);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        //item的被调用者传递过来的位置是没有原始的位置，即切换位置是从0到DataSize-1之间切换
        //但是对于外层ViewPager而言，他需要的位置范围应该是映射后的位置切换，即：除去两边映射的页面
        //应该是从1到映射后的倒数第二个位置
        super.setCurrentItem(looperAdapter.toLooperPosition(item), smoothScroll);
    }

    /**
     * 外层ViewPager中的item是通过内层位置映射关系得到的
     *
     * @return 返回映射后的
     */
    @Override
    public int getCurrentItem() {
        return looperAdapter.getInnerAdapterPostion(super.getCurrentItem());
    }

    @Override
    public void clearOnPageChangeListeners() {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.clear();
        }
    }

    @Override
    public void removeOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.remove(listener);
        }
    }

    @Override
    public void addOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        if (mOnPageChangeListeners == null) {
            mOnPageChangeListeners = new ArrayList<>();
        }
        mOnPageChangeListeners.add(listener);
    }

    private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {

        //上一次的偏移量
        private float mPreviousOffset = -1;
        //上一次的位置
        private float mPreviousPosition = -1;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (looperAdapter != null) {
                int innerPositon = looperAdapter.getInnerAdapterPostion(position);
                /*
                    positionOffset =0:滚动完成，
                    position =0 :开始的边界
                    position =looperAdapter.getCount()-1:结束的边界
                */
                Log.e("llf", "positionOffset=" + positionOffset + "\nmPreviousOffset=" + mPreviousOffset + "\nposition=" + position + "\ninnerPositon=" + innerPositon);
                if (positionOffset == 0 && mPreviousOffset == 0 && (position == 0 || position == looperAdapter.getCount() - 1)) {
                    Log.e("llf", "onPageScrolled: 映射");
                    setCurrentItem(innerPositon, false);
                }
                mPreviousOffset = positionOffset;

                if (mOnPageChangeListeners != null) {
                    for (int i = 0; i < mOnPageChangeListeners.size(); i++) {
                        OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                        if (listener != null) {
                            //如果内层的位置没有达到最后一个，内层滚动监听器正常设置
                            if (innerPositon != looperAdapter.getInnerCount() - 1) {
                                Log.e("llf", "onPageScrolled: 不是最后一个");
                                listener.onPageScrolled(innerPositon, positionOffset, positionOffsetPixels);
                            } else {
                                //如果到达最后一个位置，当偏移量达到0.5以上，这告诉监听器，这个页面已经到达内层的第一个位置
                                //否则还是最后一个位置
                                if (positionOffset > 0.5) {
                                    Log.e("llf", "onPageScrolled: 最后一个切换");
                                    listener.onPageScrolled(0, 0, 0);
                                } else {
                                    Log.e("llf", "onPageScrolled: 最后一个不切换");
                                    listener.onPageScrolled(innerPositon, 0, 0);
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void onPageSelected(int position) {
            int realPositon = looperAdapter.getInnerAdapterPostion(position);
            if (mPreviousPosition != realPositon) {
                mPreviousPosition = realPositon;
                if (mOnPageChangeListeners != null) {
                    for (int i = 0; i < mOnPageChangeListeners.size(); i++) {
                        OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                        if (listener != null) {
                            listener.onPageSelected(realPositon);
                        }
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (looperAdapter != null) {
                int positon = LooperViewPager.super.getCurrentItem();
                int realPosition = looperAdapter.getInnerAdapterPostion(positon);
                if (state == ViewPager.SCROLL_STATE_IDLE && (positon == 0 || positon == looperAdapter.getCount() - 1)) {
                    setCurrentItem(realPosition, false);
                }
            }
            if (mOnPageChangeListeners != null) {
                for (int i = 0; i < mOnPageChangeListeners.size(); i++) {
                    OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                    if (listener != null) {
                        listener.onPageScrollStateChanged(state);
                    }
                }
            }
        }
    };
}
