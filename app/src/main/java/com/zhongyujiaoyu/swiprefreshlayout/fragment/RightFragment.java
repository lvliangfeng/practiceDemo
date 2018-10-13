package com.zhongyujiaoyu.swiprefreshlayout.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhongyujiaoyu.swiprefreshlayout.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2018/10/9.
 */

public class RightFragment extends Fragment {

    private TextView tv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        View view = inflater.inflate(R.layout.fragment_right, container, false);
        tv = view.findViewById(R.id.tv);
        return view;
    }

    public void setTvContent(String content) {
        tv.setText(content);
    }

    @Subscribe
    public void onEvent(String data) {
        tv.setText(data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
