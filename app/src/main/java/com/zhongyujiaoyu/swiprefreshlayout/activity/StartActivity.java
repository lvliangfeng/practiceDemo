package com.zhongyujiaoyu.swiprefreshlayout.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.zhongyujiaoyu.swiprefreshlayout.R;
import com.zhongyujiaoyu.swiprefreshlayout.fragment.LeftFragment;
import com.zhongyujiaoyu.swiprefreshlayout.fragment.RightFragment;

public class StartActivity extends AppCompatActivity {

    private FrameLayout leftFrame;
    private FrameLayout rightFrame;
    private LeftFragment leftFragment;
    private RightFragment rightFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        initView();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        leftFragment = new LeftFragment();
        fragmentTransaction.add(R.id.frame_left, leftFragment, "LeftFragment");
        fragmentTransaction.commit();
        rightFragment = new RightFragment();
        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .add(R.id.frame_right, rightFragment, "RightFragment").commit();

        leftFragment.setDataTransationListener(new LeftFragment.DataTransationListener() {
            @Override
            public void dataTransation(String data) {
                rightFragment.setTvContent(data);
            }
        });
    }

    private void initView() {
        leftFrame = (FrameLayout) findViewById(R.id.frame_left);
        rightFrame = (FrameLayout) findViewById(R.id.frame_right);
    }
}
