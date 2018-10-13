package com.zhongyujiaoyu.swiprefreshlayout.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2018/10/12.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLiteOpenHelper";


    private MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySQLiteOpenHelper(Context context, int version) {
        this(context, "info.db", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String classesSql = "CREATE TABLE classes(class_id varchar(10) primary key," +
                " class_name varchar(20))";
        String studentsSql = "CREATE TABLE students(s_id varchar(20) primary key," +
                " s_name varchar(20), s_score varchar(4), class_id varchar(10)," +
                " foreign key (class_id) references classes(class_id)" +
                " on delete cascade on update cascade )";
        db.execSQL(classesSql);
        Log.d(TAG, "onCreate: create tabel class:" + classesSql);
        db.execSQL(studentsSql);
        Log.d(TAG, "onCreate: create tabel student:" + studentsSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: oldVersion" + oldVersion + " newVersion=" + newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        Log.d(TAG, "onOpen: ");
    }
}
