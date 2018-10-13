package com.zhongyujiaoyu.swiprefreshlayout.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.zhongyujiaoyu.swiprefreshlayout.R;
import com.zhongyujiaoyu.swiprefreshlayout.adapter.ViewPagerPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ViewPagerActivity extends AppCompatActivity {

    private static final String TAG = "ViewPagerActivity";
    private ViewPager viewPager;
    private List<View> list = new ArrayList<>();
    private ViewPagerPagerAdapter pagerAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        initView();
        initData();

        pagerAdapter = new ViewPagerPagerAdapter(this, list);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(2);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled: " + position + "::" + positionOffset + ":" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged: " + state);
            }
        });

    }


    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    private void initData() {
        View itemView1 = LayoutInflater.from(this).inflate(R.layout.viewpager_item1, null);
        View itemView2 = LayoutInflater.from(this).inflate(R.layout.viewpager_item2, null);
        View itemView3 = LayoutInflater.from(this).inflate(R.layout.viewpager_item3, null);
        View itemView4 = LayoutInflater.from(this).inflate(R.layout.viewpager_item4, null);
        View itemView5 = LayoutInflater.from(this).inflate(R.layout.viewpager_item5, null);
        list.add(itemView1);
        list.add(itemView2);
        list.add(itemView3);
        list.add(itemView4);
        list.add(itemView5);
    }
}
