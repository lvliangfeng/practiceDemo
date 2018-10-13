package com.zhongyujiaoyu.swiprefreshlayout.database;

/**
 * Created by Administrator on 2018/10/12.
 */

public class Class {
    private String classId;
    private String className;

    public String getClassId() {
        return classId == null ? "" : classId;

    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className == null ? "" : className;

    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Class--->classId:" + classId + " className:" + className;
    }
}
