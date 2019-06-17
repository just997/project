package com.example.a20190426;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private SQLiteOpenHelper helper;
    private Context context;
    public StudentDao(Context context){
        this.context = context;
    }

    public long insert(Student stu){
        helper = new MySqliteHelper(context,"students.db", null, 1);
        Log.i("MYSQLITEHELPER","before get db");
        SQLiteDatabase db = helper.getWritableDatabase();
        Log.i("MYSQLITEHELPER","after get db");
        long id=0;
        ContentValues cv=new ContentValues();
        cv.put("name",stu.getName());
        cv.put("age",stu.getAge());
        cv.put("gender",stu.getGender());
        id=db.insert("t_student",null,cv);
        //db.execSQL("insert into t_student(name, gender, age) values(?,?,?)" , new Object[]{stu.getName(),stu.getGender(),stu.getAge()});
        db.close();
        return  id;
    }

    public  void updateById(int id,Student ss){
        helper = new MySqliteHelper(context,"students.db", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",ss.getName());
        cv.put("age",ss.getAge());
        cv.put("gender",ss.getGender());
        db.update("t_student",cv,"id=?",new String[]{id+""});
    }
    public int updateStudent(ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase database = null;
        int count = -1;
        try {
            database = helper.getWritableDatabase();
            count = database.update("t_student", values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
                database.close();
            }
        }
        return count;
    }

    public void deleteById(int id){
        helper = new MySqliteHelper(context,"students.db", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("t_student","id=?",new String[]{""+id});
    }

    public int deleteStudent(String whereClause, String[] whereArgs) {
        int count = -1;
        SQLiteDatabase database = null;
        try {
            database = helper.getWritableDatabase();
            count = database.delete("t_student", whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return count;
    }

    public Cursor queryPersons(String selection, String[] selectionArgs) {
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            helper = new MySqliteHelper(context,"students.db", null, 1);
            database = helper.getReadableDatabase();
            cursor = database.query(true, "t_student", null, selection,
                    selectionArgs, null, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
                // database.close();
            }
        }
        if(cursor!=null) {
            Log.v("asda", "asddcursor1");
        }
        else {
            Log.v("asda", "asddcursor2");
        }
        return cursor;
    }
    public List<Student> getAllStudents(){
        List<Student> list = new ArrayList<Student>();
        helper = new MySqliteHelper(context,"students.db", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select id,name,gender,age from t_student", null);
        if(cursor == null){
            return null;
        }
        while(cursor.moveToNext()){
            Student stu = new Student(cursor.getString(1),cursor.getString(2),cursor.getInt(3));
            stu.setId(cursor.getInt(0));
            Log.i("MYSQLITEHELPER",stu.toString());
            list.add(stu);
        }
        return list;
    }
}
