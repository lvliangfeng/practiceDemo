package com.zhongyujiaoyu.swiprefreshlayout.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhongyujiaoyu.swiprefreshlayout.R;


/**
 * @Author lf_lv
 * @date 2018/10/29
 * Description: 扩展SwipeRefreshLayout实现上拉加载
 */

public class ExpandLoadSwipeRefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener {

    private static final String TAG = "ExpandLoadSwipeRefresh";
    private int scaleTouchSlop;//系统认为最小滑动距离
    private OnLoadListener mOnLoadListener;
    private ListView mListView;
    private View mListViewFooter;
    private ProgressBar pb_load_progress;
    private TextView tv_load_more;
    private boolean isLoading = false;//是否正在加载
    private boolean isShow = false; //是否显示底部
    private int mYDown, mYUp;//按下 抬起时的y坐标
    private float mXDown, mXMove;//判断不拦截左右滑动

    public ExpandLoadSwipeRefreshLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ExpandLoadSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        scaleTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        mListViewFooter = LayoutInflater.from(context).inflate(R.layout.footer_loadmore, null, false);
        pb_load_progress = mListViewFooter.findViewById(R.id.pb_load_progress);
        pb_load_progress.setVisibility(GONE);
        tv_load_more = mListViewFooter.findViewById(R.id.tv_load_more);
        tv_load_more.setText("加载更多");
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setLoad() {
        isShow = true;
        requestLayout();
    }

    public boolean getLoad() {
        return isShow;
    }

    public void hideBottom() {
        tv_load_more.setText("暂无更多");
        tv_load_more.setVisibility(GONE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mListView == null && isShow) {
            int childs = getChildCount();
            Log.d(TAG, "onLayout: childs:" + childs);
            if (childs > 0) {
//                View childView = getChildAt(childs - 2);//为什么是减2  不明白？
                View childView = getChildAt(childs - 1);
                if (childView instanceof ListView) {
                    mListView = (ListView) childView;
                    mListView.addFooterView(mListViewFooter);
                    mListView.setOnScrollListener(this);
                }
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (!isShow) {
            return;
        }

        if (tv_load_more.getText().toString().equals("加载更多")) {
            if (canLoad()) {
                mListViewFooter.setVisibility(VISIBLE);
                if (mOnLoadListener != null) {
                    mOnLoadListener.onLoad(mListViewFooter);
                }
            } else {
                mListViewFooter.setVisibility(GONE);
            }
        } else {
            if (!isPullUp()) {
                mListViewFooter.setVisibility(View.GONE);
            } else {
                mListViewFooter.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    //判断是否可以加载更多
    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }

    //判断是否到达底部
    private boolean isBottom() {
        if (mListView != null && mListView.getAdapter() != null) {
            return mListView.getLastVisiblePosition() == mListView.getAdapter().getCount() - 1;
        }
        return false;
    }

    //判断是否是上拉操作
    private boolean isPullUp() {
        return (mYDown - mYUp) > scaleTouchSlop;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mYDown = (int) ev.getRawY();
                mXDown = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                mYUp = (int) ev.getRawY();
                mXMove = ev.getX();
                float absX = Math.abs(mXMove - mXDown);
                if (absX > scaleTouchSlop) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    public void setOnLoadListener(OnLoadListener loadListener) {
        setLoad();
        mOnLoadListener = loadListener;
    }

    public interface OnLoadListener {
        void onLoad(View mListViewFooter);
    }
}
