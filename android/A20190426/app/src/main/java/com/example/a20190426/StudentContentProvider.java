package com.example.a20190426;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class StudentContentProvider extends ContentProvider {
    private final String TAG = "StudentContentProvider";
    private StudentDao studentDao = null;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);// 默认的规则是不匹配的
    private static final int STUDENT = 1; // 操作单行记录
    private static final int STUDENTS = 2; // 操作多行记录
    // 往UriMatcher中添加匹配规则。
    // 添加两个URI筛选
    static {
        URI_MATCHER.addURI("com.example.a20190426.StudentContentProvider", "student", STUDENTS);// 使用通配符#，匹配任意数字
        URI_MATCHER.addURI("com.example.a20190426.StudentContentProvider", "student/#", STUDENT);
    }

    @Override
    public boolean onCreate() {
        studentDao = new StudentDao(getContext());
        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri resultUri = null;
        // 解析Uri，返回Code
        int flag = URI_MATCHER.match(uri);
        switch (flag) {
            case STUDENTS://调用数据库的访问方法//values
                String name=(String) values.get("name");
                int a=(int)values.get("age");
                String sex=(String)values.get("gender");
                Student stu=new Student(name,sex,a);
                long id=studentDao.insert(stu); //执行插入操作的方法，返回插入当前行的行号
                resultUri = ContentUris.withAppendedId(uri, id);
                Log.i(TAG,"--->>插入成功, id=" + id);
                Log.i(TAG,"--->>插入成功, resultUri=" );
                System.out.println("insert success");
                break;
        }
        return resultUri;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        Log.v("urisd",uri.getAuthority()+uri.getPath());
        try {
            int flag = URI_MATCHER.match(uri);
            switch (flag) {
                case STUDENT:
                    long id = ContentUris.parseId(uri);
                    String where_value = " id = ?";
                    String[] args = { String.valueOf(id) };
                    cursor = studentDao.queryPersons(where_value, args);
                    break;
                case STUDENTS:
                    Log.v("asda","ssss");
                    cursor = studentDao.queryPersons(selection, selectionArgs);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Toast.makeText(getContext(),"asdad",Toast.LENGTH_SHORT).show();
        Log.i(TAG, "--->>查询成功，Count=" + cursor.getCount());
        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = -1;
        try {
            int flag = URI_MATCHER.match(uri);
            switch (flag) {
                case STUDENT:
                    long id = ContentUris.parseId(uri);
                    String where_value = " id = ?";
                    String[] args = { String.valueOf(id) };
                    count = studentDao.updateStudent(values, where_value, args);
                    break;
                case STUDENTS:
                    count = studentDao.updateStudent(values, selection, selectionArgs);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "--->>更新成功，count=" + count);
        return count;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = -1; //影响数据库的行数
        try {
            int flag = URI_MATCHER.match(uri);
            switch (flag) {
                case STUDENT:
                    // delete from student where id=?
                    // 单条数据，使用ContentUris工具类解析出结尾的Id
                    long id = ContentUris.parseId(uri);
                    String where_value = "id = ?";
                    String[] args = { String.valueOf(id) };
                    count = studentDao.deleteStudent(where_value, args);
                    break;
                case STUDENTS:
                    count = studentDao.deleteStudent(selection, selectionArgs);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "--->>删除成功,count=" + count);
        return count;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int flag = URI_MATCHER.match(uri);
        Log.v("urisd",uri.getAuthority()+flag);
        switch (flag) {
            case STUDENT:
                return "vnd.android.cursor.item/student"; // 如果是单条记录，则为vnd.android.cursor.item/// + path
            case STUDENTS:
                return "vnd.android.cursor.dir/student"; // 如果是多条记录，则为vnd.android.cursor.dir/// + path
        }
        return null;
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        Log.i(TAG, "--->>" + method);
        Bundle bundle = new Bundle();
        bundle.putString("returnCall", "call被执行了");
        return bundle;
    }
}
