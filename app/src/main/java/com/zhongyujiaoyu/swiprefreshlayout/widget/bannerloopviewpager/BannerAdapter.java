package com.zhongyujiaoyu.swiprefreshlayout.widget.bannerloopviewpager;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * @Author lf_lv
 * @date 2018/10/30
 * Description:
 */

public abstract class BannerAdapter<T> {
    private List<T> mDatas;

    public BannerAdapter(List<T> datas) {
        mDatas = datas;
    }

    public List<T> getmDatas() {
        return mDatas;
    }

    public void setImageViewSource(ImageView imageView, int position) {
        bindImage(imageView, mDatas.get(position));
    }

    public void selectTips(TextView textView, int position) {
        if (mDatas != null && mDatas.size() > 0) {
            bindTips(textView, mDatas.get(position));
        }
    }

    public abstract void bindImage(ImageView imageView, T t);

    public abstract void bindTips(TextView textView, T t);
}
