package com.zhongyujiaoyu.swiprefreshlayout.widget.bannerloopviewpager;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Author lf_lv
 * @date 2018/10/30
 * Description: 无限循环ViewPager适配器
 */

public class LooperAdapter extends PagerAdapter {

    private PagerAdapter mAdapter;
    private int mItemCount = 0;

    public LooperAdapter(PagerAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getCount() {
        return mAdapter.getCount() <= 1 ? mAdapter.getCount() : mAdapter.getCount() + 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return mAdapter.isViewFromObject(view, object);
    }

    @Override
    public void startUpdate(@NonNull ViewGroup container) {
        mAdapter.startUpdate(container);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.e("llf", "looper instantiateItem: " + position);
        return mAdapter.instantiateItem(container, getInnerAdapterPostion(position));
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.e("llf", "looper destroyItem: " + position);
        mAdapter.destroyItem(container, getInnerAdapterPostion(position), object);
    }


    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        mAdapter.setPrimaryItem(container, position, object);
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        mAdapter.finishUpdate(container);
    }

    @Override
    public void notifyDataSetChanged() {
        mItemCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    /**
     * 根据外层position获取内层position
     *
     * @param position 外层ViewPager的position
     * @return 外层ViewPager当前数据位置对应的内层ViewPager对应的位置
     */
    public int getInnerAdapterPostion(int position) {
        //ViewPager真正可用的个数
        int realCount = getInnerCount();
        //内层没有可用的Item则返回0
        if (realCount == 0) {
            return 0;
        }
        int realPositon = (position - 1) % realCount;
        if (realPositon < 0) {
            realPositon += realCount;
        }
        return realPositon;
    }

    /**
     * @return 内层ViewPager中可用的item个数
     */
    public int getInnerCount() {
        return mAdapter.getCount();
    }

    /**
     * 根据内层position的位置，返回映射后外层position位置
     *
     * @param positon 内层position的位置
     * @return 无限轮播ViewPager的切换位置
     */
    public int toLooperPosition(int positon) {
        if (getInnerCount() > 1) {
            return positon + 1;
        } else {
            return positon;
        }
    }
}
