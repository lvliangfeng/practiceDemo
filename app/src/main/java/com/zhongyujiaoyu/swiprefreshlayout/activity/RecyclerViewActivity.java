package com.zhongyujiaoyu.swiprefreshlayout.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.zhongyujiaoyu.swiprefreshlayout.R;
import com.zhongyujiaoyu.swiprefreshlayout.adapter.RecyclerViewAdapter;
import com.zhongyujiaoyu.swiprefreshlayout.bean.Book;
import com.zhongyujiaoyu.swiprefreshlayout.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Book> data = new ArrayList<>();
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initView();
        initData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

//        StaggeredGridLayoutManager staggeredGridLayoutManager =
//                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(staggeredGridLayoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL_LIST));
        recyclerViewAdapter = new RecyclerViewAdapter(this, data);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(RecyclerViewActivity.this, "点击位置:" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int positon) {
                Toast.makeText(RecyclerViewActivity.this, "长按位置:" + positon, Toast.LENGTH_SHORT).show();
                recyclerViewAdapter.removeData(positon);
            }
        });
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.DKGRAY);
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        List<Book> list = new ArrayList<>();
                        for (int i = 0; i < 6; i++) {
                            Book book = new Book("headbook" + i, R.mipmap.ic_launcher);
                            list.add(book);
                        }
                        recyclerViewAdapter.addHeaderItem(list);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        //上拉加载
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == recyclerViewAdapter.getItemCount()) {
                    recyclerViewAdapter.changeStatus(RecyclerViewAdapter.LOADING);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<Book> list = new ArrayList<>();
                            for (int i = 0; i < 3; i++) {
                                Book book = new Book("footbook" + i, R.mipmap.ic_launcher);
                                list.add(book);
                            }
                            recyclerViewAdapter.changeStatus(RecyclerViewAdapter.PULLUP_LOAD);
                            recyclerViewAdapter.addFooterItem(list);
                        }
                    }, 3000);
                    List<Book> list = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        Book book = new Book("footbook" + i, R.mipmap.ic_launcher);
                        list.add(book);
                    }
                    recyclerViewAdapter.addFooterItem(list);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            Book book1 = new Book("Book" + i, R.mipmap.ic_launcher);
            data.add(book1);
            Book book2 = new Book("Book" + i + i, R.mipmap.ic_launcher);
            data.add(book2);
            Book book3 = new Book("Book" + i + i + i, R.mipmap.ic_launcher);
            data.add(book3);
        }
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
    }


}
