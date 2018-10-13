package com.zhongyujiaoyu.swiprefreshlayout.bean;

import android.media.Image;

/**
 * Created by Administrator on 2018/10/10.
 */

public class Book {

    private String name;
    private int icon;

    public Book(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
