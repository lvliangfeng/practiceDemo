package com.zhongyujiaoyu.swiprefreshlayout.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2018/10/9.
 */

public class ViewPagerPagerAdapter extends PagerAdapter {

    private List<View> mData;
    private Context mContext;
    private static final String TAG = "ViewPagerPagerAdapter";

    public ViewPagerPagerAdapter(Context context, List<View> data) {
        this.mContext = context;
        this.mData = data;
    }

    //1. 返回可以滑动的View的个数
    @Override
    public int getCount() {
        return mData.size();
    }


    //3. 将当前View添加到父容器中，并且返回当前View
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.e("llf", "instantiateItem: " + position);
//        if (position > mData.size() - 1) {
//            position = position % mData.size();
//        }
        View view = mData.get(position);
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
//        container.addView(mData.get(position));
//        return mData.get(position);
        container.addView(view);
        return view;
    }


    //4. 确认"第三步"instantiateItem返回的Object与页面View是否是同一个
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        Log.e("llf", "isViewFromObject: 判断");
        return view == object;
    }


    //2. 滑动切换时销毁当前View
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.e("llf", "destroyItem: 销毁View" + position);
//        container.removeView(mData.get(position));
//        container.removeView((View) object);

//        if (position > mData.size() - 1) {
//            position = position % mData.size();
//        }
        View view = mData.get(position);
        ViewGroup vp = (ViewGroup) view.getParent();
        if (vp == null) {
            container.removeView(mData.get(position));
        }

    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
