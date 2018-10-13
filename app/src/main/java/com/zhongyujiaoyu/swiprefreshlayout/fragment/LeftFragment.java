package com.zhongyujiaoyu.swiprefreshlayout.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zhongyujiaoyu.swiprefreshlayout.R;
import com.zhongyujiaoyu.swiprefreshlayout.activity.ViewPagerActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2018/10/9.
 */

public class LeftFragment extends Fragment implements View.OnClickListener {

    private DataTransationListener listener;
    private Button btn;
    private Button btnJumpViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        EventBus.getDefault().register(this);

        View view = inflater.inflate(R.layout.fragment_left, container, false);
        btnJumpViewPager = view.findViewById(R.id.btn_jump_viewpager);
        btnJumpViewPager.setOnClickListener(this);
        btn = view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接传递
//                RightFragment rightFragment = (RightFragment) getActivity()
//                        .getSupportFragmentManager().findFragmentByTag("RightFragment");
//                rightFragment.setTvContent("我让你变你就得变");


                //接口传递
//                if (listener != null)
//                    listener.dataTransation("我用接口让你变你还得变");

                //eventbus传递
                EventBus.getDefault().post("我现在使用EventBus!");
            }
        });

        return view;
    }

    @Subscribe
    public void on(String data) {
        btn.setText("EventBus");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jump_viewpager:
                Intent intent = new Intent(this.getActivity(), ViewPagerActivity.class);
                startActivity(intent);

                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public interface DataTransationListener {
        public void dataTransation(String data);
    }

    public void setDataTransationListener(DataTransationListener dataTransationListener) {
        this.listener = dataTransationListener;
    }
}
