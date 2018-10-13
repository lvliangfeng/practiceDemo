package com.zhongyujiaoyu.swiprefreshlayout.database;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhongyujiaoyu.swiprefreshlayout.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseActivity extends AppCompatActivity implements View.OnClickListener {


    private List<Class> classData = new ArrayList<Class>();
    private List<Student> studentsData = new ArrayList<Student>();
    private static final String className = "A/B/C/D/E";
    private static final String studentName = "赵大/钱二/张三/李四/王五/郑六/田七/周八/叶九/孔十/萧十一/赵云/吕布/关羽/狄仁杰/白起/嬴政/小乔";
    private DBOperationHelper dbOperationHelper;
    private SharedPreferences sharedPreferences;
    private String info = "";
    private Handler hander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0) {
                etStudentId.setText("");
                etStudentName.setText("");
                etStudentScore.setText("");
                etClassName.setText("");
                etClassId.setText("");
            } else if (msg.what == 1) {
                dbOperationHelper.deleteClass((String) msg.obj);
                info += "删除一个班级及班级里面的学生：班级Id:" + (String) msg.obj;
                tvAllInfo.setText(info);
            }

        }

    };
    private Button btnAddStudent;
    private Button btnDeleteStudent;
    private Button btnDeleteClass;
    private Button btnUpdateStudent;
    private Button btnFindClassStudents;
    private Button btnFindMaxScoreStudent;
    private Button btnAllInfo;
    private EditText etClassId;
    private EditText etClassName;
    private EditText etStudentId;
    private EditText etStudentName;
    private EditText etStudentScore;
    private TextView tvAllInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        initView();
        sharedPreferences = getSharedPreferences("DatabaseDemo", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        dbOperationHelper = new DBOperationHelper(this);
        if (sharedPreferences.getInt("times", 0) == 0) {
            initDatabase();
            editor.putInt("times", 1);
            editor.commit();
        }
    }


    private void initView() {
        btnAddStudent = (Button) findViewById(R.id.add_student);
        btnDeleteStudent = (Button) findViewById(R.id.delete_student);
        btnDeleteClass = (Button) findViewById(R.id.delete_class);
        btnUpdateStudent = (Button) findViewById(R.id.update_student);
        btnFindClassStudents = (Button) findViewById(R.id.find_class_students);
        btnFindMaxScoreStudent = (Button) findViewById(R.id.find_max_score_student);
        btnAllInfo = (Button) findViewById(R.id.all_info);
        etClassId = (EditText) findViewById(R.id.class_id);
        etClassName = (EditText) findViewById(R.id.class_name);
        etStudentId = (EditText) findViewById(R.id.student_id);
        etStudentName = (EditText) findViewById(R.id.student_name);
        etStudentScore = (EditText) findViewById(R.id.score);
        tvAllInfo = (TextView) findViewById(R.id.info);
        tvAllInfo.setMovementMethod(ScrollingMovementMethod.getInstance());

        btnAddStudent.setOnClickListener(this);
        btnDeleteStudent.setOnClickListener(this);
        btnDeleteClass.setOnClickListener(this);
        btnUpdateStudent.setOnClickListener(this);
        btnFindClassStudents.setOnClickListener(this);
        btnFindMaxScoreStudent.setOnClickListener(this);
        btnAllInfo.setOnClickListener(this);

    }

    private void initDatabase() {
        info = "";
        tvAllInfo.setText("");
        String[] classTemp = className.split("/");
        Class c;
        for (int i = 0; i < classTemp.length; i++) {
            c = new Class();
            c.setClassName(classTemp[i]);
            c.setClassId("00" + i);
            dbOperationHelper.addClass(c);
            info += '\n' + "add to database classes:" + c.toString();
        }
        String[] studentTemp = studentName.split("/");
        Student s;
        for (int j = 0; j < studentTemp.length; j++) {
            s = new Student();
            s.setStudentName(studentTemp[j]);
            s.setStudentId("2018100" + j);
            s.setClassId("00" + new Random().nextInt(classTemp.length));
            s.setScore(String.valueOf(new Random().nextInt(100) + 1));
            dbOperationHelper.addStudent(s);
            info += '\n' + "add to database students:" + '\n' + s.toString();
        }
        tvAllInfo.setText(info);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            case R.id.all_info:
                printAllInfo();
                break;
            case R.id.add_student:
                addAStudent();
                break;
            case R.id.delete_student:
                deleteAStudent();
                break;
            case R.id.delete_class:
                deleteAClass();
                break;
            case R.id.update_student:
                updateStudent();
                break;
            case R.id.find_class_students:
                printStudentsOfClass();
                break;
            case R.id.find_max_score_student:
                printMaxScoreStudent();
                break;
        }
        hander.sendEmptyMessageDelayed(0, 5000);
    }

    private void addAStudent() {
        info = "";
        tvAllInfo.setText("");
        String studentId = etStudentId.getText().toString();
        String studentName = etStudentName.getText().toString();
        String studentScore = etStudentScore.getText().toString();
        String studentClassId = etClassId.getText().toString();
        if (checkInfo(studentId) && checkInfo(studentName) && checkInfo(studentScore) && checkInfo(studentClassId)) {
            Student s = new Student();
            s.setStudentId(studentId);
            s.setStudentName(studentName);
            s.setScore(studentScore);
            s.setClassId(studentClassId);
            dbOperationHelper.addStudent(s);
            info += "add to database students:" + '\n' + s.toString();
        } else {
            info += "添加一个学生失败：缺少必要信息，请确认studentId,studentName,score,classId的信息是否完整！";
        }
        tvAllInfo.setText(info);
    }

    private void deleteAClass() {
        info = "";
        tvAllInfo.setText("");
        final String tempCID = etClassId.getText().toString();
        if (checkInfo(tempCID)) {
            if (dbOperationHelper.isClassExists(tempCID)) {
                new AlertDialog.Builder(this)
                        .setTitle("提示：")
                        .setMessage("删除一个班级将会删除该班的所有学生信息，确定？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = tempCID;
                                hander.sendMessage(msg);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            } else
                info += "删除一个班级失败：查无此对应班级，请确认classId的信息是否正确！";
        } else {
            info += "删除一个班级失败：缺少必要信息，请确认classId的信息是否完整！";
        }
        tvAllInfo.setText(info);
    }

    private void deleteAStudent() {
        info = "";
        tvAllInfo.setText("");
        String tempSID = etStudentId.getText().toString();
        if (checkInfo(tempSID)) {
            if (dbOperationHelper.isStudentsExists(tempSID)) {
                dbOperationHelper.deleteStudent(tempSID);
                info += "删除一个学生：学生Id:" + tempSID;
            } else
                info += "删除一个学生失败：查无此对应学生，请确认studentId的信息是否正确！";
        } else {
            info += "删除一个学生失败：缺少必要信息，请确认studentId的信息是否完整！";
        }
        tvAllInfo.setText(info);
    }


    private void updateStudent() {
        info = "";
        tvAllInfo.setText("");
        String tempSID = etStudentId.getText().toString();
        String tempSName = etStudentName.getText().toString();
        String tempScore = etStudentScore.getText().toString();
        String tempCID = etClassId.getText().toString();
        if (checkInfo(tempSID) && checkInfo(tempSName) && checkInfo(tempScore) && checkInfo(tempCID)) {
            if (dbOperationHelper.isStudentsExists(tempSID)) {
                Student temp = new Student();
                temp.setStudentId(tempSID);
                temp.setStudentName(tempSName);
                temp.setScore(tempScore);
                temp.setClassId(tempCID);
                dbOperationHelper.updateStudent(temp);
                info += "update database students:" + '\n' + temp.toString();
            } else {
                Student temp = new Student();
                temp.setStudentId(tempSID);
                temp.setStudentName(tempSName);
                temp.setScore(tempScore);
                temp.setClassId(tempCID);
                dbOperationHelper.addStudent(temp);
                info += "没有找到对应ID的学生，将此学生添加到数据库！" + '\n' +
                        "add to database students:" + '\n' + temp.toString();
            }
        } else {
            info += "更新学生失败：缺少必要信息，请确认studentId,studentName,score,classId的信息是否完整！";
        }
        tvAllInfo.setText(info);
    }

    /**
     * 打印某班的学生
     */
    private void printStudentsOfClass() {
        info = "";
        tvAllInfo.setText("");
        String tempCID = etClassId.getText().toString();
        String tempCName = etClassName.getText().toString();
        if (checkInfo(tempCID)) {
            if (dbOperationHelper.isClassExists(tempCID)) {
                info += "使用ID查询";
                studentsData.clear();
                studentsData = dbOperationHelper.findStudentsByClassId(tempCID);
            } else {
                info += "该ID对应的班级不存在";
            }
        } else if (checkInfo(tempCName)) {
            if (dbOperationHelper.isClassExists(tempCName)) {
                info += "使用Name查询";
                studentsData.clear();
                studentsData = dbOperationHelper.findStudentsByClassName(tempCName);
            } else {
                info += "该Name对应的班级不存在";
            }
        } else {
            studentsData.clear();
            info += "查找学生失败：缺少必要信息，请确认classId或className的信息是否完整！";
        }
        for (int i = 0; i < studentsData.size(); i++) {
            info += '\n' + studentsData.get(i).toString();
        }
        tvAllInfo.setText(info);
        ;
    }

    private void printMaxScoreStudent() {
        info = "";
        tvAllInfo.setText("");
        Student temp = dbOperationHelper.findMaxScoreStudent();
        info += '\n' + temp.toString();
        tvAllInfo.setText(info);
    }


    private void getAllStudent() {
        studentsData.clear();
        studentsData = dbOperationHelper.findAllStudents();
        for (int i = 0; i < studentsData.size(); i++) {
            info += '\n' + studentsData.get(i).toString();
        }
    }

    private void getAllClass() {
        classData.clear();
        classData = dbOperationHelper.findAllClasses();
        for (int i = 0; i < classData.size(); i++) {
            info += '\n' + classData.get(i).toString();
        }
    }

    private void printAllInfo() {
        info = "";
        tvAllInfo.setText("");
        getAllStudent();
        getAllClass();
        tvAllInfo.setText(info);
    }

    private boolean checkInfo(String s) {
        if (s.equals("") || s == null)
            return false;
        else
            return true;
    }
}


