package com.example.a20190426;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText name,age;
    RadioGroup rg;
    StudentDao dao;
    List<Student> data;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);
        name=findViewById(R.id.et1);
        age=findViewById(R.id.et2);
        rg=findViewById(R.id.rg);
        dao=new StudentDao(MainActivity.this);
        lv=findViewById(R.id.lv1);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                /**将布局文件dialog加载到list里面
                 * 通过方法LayoutInflater进行装载
                 * */
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("是否修改数据？");
                View vvd= LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog,null);
                /***
                 * 自定义对话框，用于修改数据
                 */
                final EditText et1=vvd.findViewById(R.id.et1);
                final EditText et2=vvd.findViewById(R.id.et2);
                RadioGroup rg1=vvd.findViewById(R.id.rg);
                et1.setText(data.get(position).getName());
                et2.setText(data.get(position).getAge()+"");
                final RadioButton rb1=vvd.findViewById(R.id.rb1);
                final RadioButton rb2=vvd.findViewById(R.id.rb2);
                if(data.get(position).getGender().equals("男")){
                    rb1.setChecked(true);
                }else{
                    rb2.setChecked(true);
                }
                builder.setView(vvd);
                /**
                 * 为ListView添加两个监听按钮，并定为修改和取消两种操作
                 * 方法为setPositiveButton
                 * */
                builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = data.get(position).getId();
                        String gender = "";
                        if (rb1.isChecked()) {
                            gender = "男";
                        } else {
                            gender = "女";
                        }
                        Student ss = new Student(et1.getText().toString().trim(), gender, Integer.parseInt(et2.getText().toString().trim()));
                        dao.updateById(id, ss);
                        chakan(null);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                //Toast.makeText(MainActivity.this,"123456789",Toast.LENGTH_LONG).show();
                return true;
            }
        });
        /**绑定监听，调用数据删除，自定义对话框，定义两个按钮，确定和取消
         * */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("是否删除数据？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id=data.get(position).getId();
                        dao.deleteById(id);
                        data=dao.getAllStudents();
                        MyAdapter adapter=new MyAdapter(MainActivity.this,(ArrayList<Student>)data);
                        lv.setAdapter(adapter);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });
    }

    public void tianjia(View vv){
        String ming=name.getText().toString().trim();
        String sex=((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        int a=Integer.parseInt(age.getText().toString().trim());
        Student st=new Student(ming,sex,a);
        dao.insert(st);
    }

    public void chakan(View vv){
       /* List<Person> personList=dao.getAllStudents();
        Person personAdapter=new Person(this,R.layout.item,person);
        lv.setAdapter(person);*/
        data=dao.getAllStudents();
        MyAdapter adapter=new MyAdapter(MainActivity.this,(ArrayList<Student>)data);
        lv.setAdapter(adapter);
        /**将数据加载到ListView
         * */
        /*Toast.makeText(MainActivity.this,data.get(0).getName(),Toast.LENGTH_LONG).show();*/
    }
}
