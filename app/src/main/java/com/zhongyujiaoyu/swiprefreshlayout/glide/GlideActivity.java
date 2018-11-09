package com.zhongyujiaoyu.swiprefreshlayout.glide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.zhongyujiaoyu.swiprefreshlayout.R;

public class GlideActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLoadImage;
    private ImageView ivShowImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        initView();
    }

    private void initView() {
        btnLoadImage = (Button) findViewById(R.id.load_picture);
        ivShowImage = (ImageView) findViewById(R.id.show_picture);
        btnLoadImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.load_picture:
                loadImage();
                break;
        }
    }

    private void loadImage() {
        String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
        String gifUrl = "http://p1.pstatp.com/large/166200019850062839d3";
//        Glide.with(this)
//                .load(url)
//                .into(ivShowImage);
        GlideApp.with(this)
                .load(url)
                .placeholder(R.drawable.load)
                .error(R.drawable.image_load_fail)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .override(100, 100)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivShowImage);



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
