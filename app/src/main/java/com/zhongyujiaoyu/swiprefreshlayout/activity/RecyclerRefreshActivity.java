package com.zhongyujiaoyu.swiprefreshlayout.activity;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.zhongyujiaoyu.swiprefreshlayout.R;
import com.zhongyujiaoyu.swiprefreshlayout.adapter.CommonAdapter;
import com.zhongyujiaoyu.swiprefreshlayout.adapter.LoadWrapperAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RecyclerRefreshActivity extends AppCompatActivity implements CommonAdapter.OnItemClickListener {

    private static final String TAG = "RecyclerRefreshActivity";
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    //    private CommonAdapter commonAdapter;
    private LoadWrapperAdapter wrapperAdapter;
    private List<String> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_refresh);
        initView();
        initToolbar();
        initRecyclerView();
        initSiwpRefresh();
    }


    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    }

    private void initRecyclerView() {
        getData();
        CommonAdapter commonAdapter = new CommonAdapter(mDataList);
        wrapperAdapter = new LoadWrapperAdapter(commonAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wrapperAdapter);
        wrapperAdapter.setOnItemClickListener(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == wrapperAdapter.getItemCount()) {
                    wrapperAdapter.setLoadStatus(wrapperAdapter.LOADING);
                    if (mDataList.size() < 52) {
                        Log.d(TAG, "onScrollStateChanged: loading ing");
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        getData();
                                        wrapperAdapter.setLoadStatus(wrapperAdapter.LOADING_COMPLETE);
                                    }
                                });
                            }
                        }, 1000);
                    } else {
                        wrapperAdapter.setLoadStatus(wrapperAdapter.LOADING_END);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
            }
        });
    }

    private void initSiwpRefresh() {
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });
    }

    private void initToolbar() {

        // Logo
//        toolbar.setLogo(R.mipmap.ic_launcher);
        // 主标题
//        toolbar.setTitle("Toolbar");
        // 副标题
//        toolbar.setSubtitle("Sub Title");
        //设置toolbar
        setSupportActionBar(toolbar);
        //左边的小箭头（注意需要在setSupportActionBar(toolbar)之后才有效果）
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //toolbar 菜单栏的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.linear_layout:
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.grid_layout:
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                break;
        }
        mDataList.clear();
        getData();
//        commonAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(wrapperAdapter);
        return super.onOptionsItemSelected(item);
    }

    private void getData() {
        char letter = 'A';
        for (int i = 0; i < 26; i++) {
            mDataList.add(String.valueOf(letter));
            letter++;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (view instanceof CardView) {
            View v = ((CardView) view).getChildAt(0);
            String content = ((TextView) v).getText().toString();
            Toast.makeText(this, "点击位置:" + position + ",内容:" + content, Toast.LENGTH_SHORT).show();
        } else if (view instanceof TextView) {
            String content = ((TextView) view).getText().toString();
            Toast.makeText(this, "点击位置:" + position + ",内容:" + content, Toast.LENGTH_SHORT).show();
        }
    }
}
