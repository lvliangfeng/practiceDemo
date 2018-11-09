package com.zhongyujiaoyu.swiprefreshlayout.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.zhongyujiaoyu.swiprefreshlayout.R;
import com.zhongyujiaoyu.swiprefreshlayout.adapter.ViewPagerPagerAdapter;
import com.zhongyujiaoyu.swiprefreshlayout.widget.bannerloopviewpager.LooperViewPager;

import java.util.ArrayList;
import java.util.List;

public class LooperViewPagerActivity extends AppCompatActivity {

    private LooperViewPager looperViewPager;
    private List<View> list = new ArrayList<>();
    private ViewPagerPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper_view_pager);
        initView();
        initData();

        pagerAdapter = new ViewPagerPagerAdapter(this, list);
        looperViewPager.setAdapter(pagerAdapter);
        looperViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
        });
    }

    private void initView() {
        looperViewPager = findViewById(R.id.looper_viewpager);
    }

    private void initData() {
        View itemView1 = LayoutInflater.from(this).inflate(R.layout.viewpager_item1, null);
        View itemView2 = LayoutInflater.from(this).inflate(R.layout.viewpager_item2, null);
        View itemView3 = LayoutInflater.from(this).inflate(R.layout.viewpager_item3, null);
//        View itemView4 = LayoutInflater.from(this).inflate(R.layout.viewpager_item4, null);
//        View itemView5 = LayoutInflater.from(this).inflate(R.layout.viewpager_item5, null);
        list.add(itemView1);
        list.add(itemView2);
        list.add(itemView3);
//        list.add(itemView4);
//        list.add(itemView5);
    }
}
