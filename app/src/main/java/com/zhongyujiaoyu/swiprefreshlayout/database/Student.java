package com.zhongyujiaoyu.swiprefreshlayout.database;

/**
 * Created by Administrator on 2018/10/12.
 */

public class Student {
    private String studentId;
    private String studentName;
    private String score;
    private String classId;

    public String getStudentId() {
        return studentId == null ? "" : studentId;

    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName == null ? "" : studentName;

    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getScore() {
        return score == null ? "" : score;

    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getClassId() {
        return classId == null ? "" : classId;

    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String toString() {
        return "Student--->studentId:" + studentId +
                " studentName:" + studentName + " score:" + score +
                " classId:" + classId;
    }

}
