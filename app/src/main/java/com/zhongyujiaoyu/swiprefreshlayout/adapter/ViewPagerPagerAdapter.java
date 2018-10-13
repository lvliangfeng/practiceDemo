package com.zhongyujiaoyu.swiprefreshlayout.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
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
        Log.d(TAG, "instantiateItem: 添加父容器View" + position);
        container.addView(mData.get(position));
        return mData.get(position);
    }

    
    //4. 确认"第三步"instantiateItem返回的Object与页面View是否是同一个
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        Log.d(TAG, "isViewFromObject: 判断");
        return view == object;
    }


    //2. 滑动切换时销毁当前View
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.d(TAG, "destroyItem: 销毁View" + position);
        container.removeView(mData.get(position));
    }
}
