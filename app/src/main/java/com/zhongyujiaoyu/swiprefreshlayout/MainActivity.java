package com.zhongyujiaoyu.swiprefreshlayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.zhongyujiaoyu.swiprefreshlayout.adapter.ListViewAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private List<String> list = new ArrayList<>();
    //    private ArrayAdapter adapter;
    private ListViewAdapter adapter;
    private NoleakHandler noleakHandler;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorRed),
                getResources().getColor(R.color.colorGreen), getResources().getColor(R.color.colorBlue));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                noleakHandler.sendEmptyMessageDelayed(1, 3000);
            }
        });

//        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getData());
        adapter = new ListViewAdapter(this, getData());
        listView.setAdapter(adapter);
    }

    private void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        listView = (ListView) findViewById(R.id.list_view);
        noleakHandler = new NoleakHandler(this);
    }

    private List<String> getData() {
        for (int i = 0; i < 5; i++) {
            list.add("我是数据" + i);
        }
        return list;
    }

    private static class NoleakHandler extends Handler {
        private WeakReference<MainActivity> mActivity;

        public NoleakHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        activity.list.add("我是新加数据" + new Random().nextInt());
                        activity.adapter.notifyDataSetChanged();
                        activity.swipeRefreshLayout.setRefreshing(false);
                        break;
                }
            }
        }
    }
}
