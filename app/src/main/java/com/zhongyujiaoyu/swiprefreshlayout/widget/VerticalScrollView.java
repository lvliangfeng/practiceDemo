package com.zhongyujiaoyu.swiprefreshlayout.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @Author lf_lv
 * @date 2018/10/29
 * Description: 不拦截水平滑动的ScrollView
 * 用来解决ScrollView嵌套ViewPager使用的滑动事件冲突问题
 */

public class VerticalScrollView extends ScrollView {

    private float mDownX, mDownY;

    public VerticalScrollView(Context context) {
        super(context);
    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    public VerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = getX();
                mDownY = getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float deltaX = Math.abs(getX() - mDownX);
                float deltaY = Math.abs(getY() - mDownY);
                if (deltaX > deltaY) {
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
