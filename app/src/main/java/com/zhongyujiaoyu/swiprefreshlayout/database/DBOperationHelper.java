package com.zhongyujiaoyu.swiprefreshlayout.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/12.
 */

public class DBOperationHelper {

    private MySQLiteOpenHelper dbhelper;

    public DBOperationHelper(Context context) {
        dbhelper = new MySQLiteOpenHelper(context, 1);
    }

    public DBOperationHelper(Context context, int version) {
        dbhelper = new MySQLiteOpenHelper(context, version);
    }

    public void addClass(Class entity) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("class_id", entity.getClassId());
        contentValues.put("class_name", entity.getClassName());
        sqLiteDatabase.insert("classes", null, contentValues);
//        sqLiteDatabase.execSQL("insert into classes(class_id,class_name) values(?,?)",new Object[]{entity.getClassId(),entity.getClassName()});
        sqLiteDatabase.close();
    }

    public void addStudent(Student entity) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("s_id", entity.getStudentId());
        contentValues.put("s_name", entity.getStudentName());
        contentValues.put("s_score", entity.getScore());
        contentValues.put("class_id", entity.getClassId());
        sqLiteDatabase.insert("students", null, contentValues);
//        sqLiteDatabase.execSQL("insert into students(s_id,s_name,s_score,class_id) values(?,?,?,?)",
//                new Object[]{entity.getStudentId(),entity.getStudentName(),entity.getScore(),entity.getClassId()});
        sqLiteDatabase.close();
    }

    @SuppressLint("NewApi")
    public void deleteClass(String class_id) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        sqLiteDatabase.setForeignKeyConstraintsEnabled(true);
        sqLiteDatabase.delete("classes", "class_id=?", new String[]{class_id});
//        sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON");
//        sqLiteDatabase.execSQL("delete from classes where class_id=?", new Object[]{class_id});
        sqLiteDatabase.close();
    }

    public void deleteStudent(String s_id) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        sqLiteDatabase.delete("students", "s_id=?", new String[]{s_id});
//        sqLiteDatabase.execSQL("delete from students where s_id=?", new Object[]{s_id});
        sqLiteDatabase.close();
    }

    public void updateStudent(Student entity) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("s_name", entity.getStudentName());
        contentValues.put("s_score", entity.getScore());
        contentValues.put("class_id", entity.getClassId());
        sqLiteDatabase.update("students", contentValues, "s_id=?", new String[]{entity.getStudentId()});
//        sqLiteDatabase.execSQL("update students set s_name=?,score=?,class_id=? where student_id=?",
//                new Object[]{entity.getStudentName(),entity.getScore(),entity.getClassId(),entity.getStudentId()});
        sqLiteDatabase.close();
    }

    public List<Student> findStudentsByClassId(String classId) {
        List<Student> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("students", new String[]{"s_id", "s_name", "s_score"},
                "calss_id=?", new String[]{classId}, null, null, "s_score desc");
//        Cursor cursor = sqLiteDatabase.rawQuery("select s_id,s_name,s_score from students where class_id=? orderby s_score desc", new String[]{classId});
        while (cursor.moveToNext()) {
            Student student = new Student();
            student.setStudentId(cursor.getString(cursor.getColumnIndex("s_id")));
            student.setStudentName(cursor.getString(cursor.getColumnIndex("s_name")));
            student.setScore(cursor.getString(cursor.getColumnIndex("s_score")));
            student.setClassId(classId);
            list.add(student);
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;
    }

    public List<Student> findStudentsByClassName(String className) {

        List<Student> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select s_id, s_name, s_score, classes.class_id " +
                "from students,classes where students.class_id=classes.class_id " +
                "and classes.class_name=? order by score asc", new String[]{className});
        while (cursor.moveToNext()) {
            Student student = new Student();
            student.setStudentId(cursor.getString(cursor.getColumnIndex("s_id")));
            student.setStudentName(cursor.getString(cursor.getColumnIndex("s_name")));
            student.setScore(cursor.getString(cursor.getColumnIndex("s_score")));
            student.setClassId(cursor.getString(3));
            list.add(student);
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;
    }

    public List<Student> findAllStudents() {
        List<Student> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("students", new String[]{"s_id", "s_name", "s_score", "class_id"},
                null, null, null, null, "score desc");
//        sqLiteDatabase.rawQuery("select * from students where 1=1 order by score desc");
        while (cursor.moveToNext()) {
            Student student = new Student();
            student.setStudentId(cursor.getString(cursor.getColumnIndex("s_id")));
            student.setStudentName(cursor.getString(cursor.getColumnIndex("s_name")));
            student.setScore(cursor.getString(cursor.getColumnIndex("s_score")));
            student.setClassId(cursor.getString(cursor.getColumnIndex("class_id")));
            list.add(student);
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;
    }

    public List<Class> findAllClasses() {
        List<Class> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("classes", new String[]{"class_id", "class_name"},
                null, null, null, null, "class_id asc");
//        sqLiteDatabase.rawQuery("select * from classes where 1=1", null);
        while (cursor.moveToNext()) {
            Class classObject = new Class();
            classObject.setClassId(cursor.getString(cursor.getColumnIndex("class_id")));
            classObject.setClassName(cursor.getString(cursor.getColumnIndex("class_name")));
            list.add(classObject);
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;
    }

    public Student findMaxScoreStudent() {
        Student student = new Student();
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select s_id,s_name,class_id,max(s_score)  from students  " +
                "where 1=1", null);
//        select * from table a where id=(select MAX(id) from table where key =a.key ) and key ='n'
//        SELECT * FROM 表名 ORDER BY id DESC LIMIT 0,1
        cursor.moveToFirst();
        student.setStudentId(cursor.getString(0));
        student.setStudentName(cursor.getString(1));
        student.setClassId(cursor.getString(2));
        student.setScore(cursor.getString(3));
        cursor.close();
        sqLiteDatabase.close();
        return student;
    }

    public boolean isStudentsExists(String studentId) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select count(*)  from students  " +
                "where student_id=?", new String[]{studentId});
        cursor.moveToFirst();
        if (cursor.getInt(0) > 0)
            return true;
        else
            return false;
    }

    public boolean isClassExists(String s) {
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor cursor = localSQLiteDatabase.rawQuery("select count(*)  from classes  " +
                "where class_id=? or class_name=?", new String[]{s, s});
        cursor.moveToFirst();
        if (cursor.getInt(0) > 0)
            return true;
        else
            return false;
    }

}
