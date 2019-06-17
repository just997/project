package com.lemapsdk.a20190506;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.BaseBundle;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RegisteActivity extends Activity {
    EditText name;
    EditText pass;
    EditText repass;
    EditText age1;
    Spinner sp1;
    RadioGroup rg1;
    String sex = "男";
    int age;
    String banji = "17242";
    String fav = "";
    ArrayList<CheckBox> list = new ArrayList<CheckBox>();
    public static final String data[] = {"17242", "17241", "17243"};

    SharedPreferences sh;
    SharedPreferences.Editor ed;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("RegisteActivity","onCreate");
        setContentView(R.layout.second);
        rg1 = findViewById(R.id.rt);
        name = findViewById(R.id.eet1);
        pass =  findViewById(R.id.ee2);
        repass =  findViewById(R.id.ee3);

        CheckBox ck1 = findViewById(R.id.checkBox6);
        CheckBox ck2 = findViewById(R.id.checkBox7);
        CheckBox ck3 = findViewById(R.id.checkBox8);
        CheckBox ck4 = findViewById(R.id.checkBox5);
        list.add(ck1);
        list.add(ck2);
        list.add(ck3);
        list.add(ck3);
        list.add(ck4);

        sh=getSharedPreferences("user",MODE_PRIVATE);
        ed=sh.edit();


//        Button bb=new Button(RegisteActivity.this);
//        bb.setText("返回");
//        setContentView(bb);

        age1 = findViewById(R.id.qq1);
        sp1 = findViewById(R.id.spinner3);
        rg1 = findViewById(R.id.rt);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        sp1.setAdapter(adapter);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                banji = data[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
    }


    //   Spinner spinner = (Spinner)findViewById(R.id.spinner3);
    // final ArrayList<String> arrayList = new ArrayList<String>();
    //  arrayList.add("17241");
    //  arrayList.add("17242");
    //  arrayList.add("17243");
    //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayList);//创建适配器  this--上下文  android.R.layout.simple_spinner_item--显示的模板   arrayList--显示的内容
    //  arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//设置下拉之后的布局的样式 这里采用的是系统的一个布局
    // spinner.setAdapter(arrayAdapter);//将适配器给下拉框
    // spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    //     @Override
    //     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    //    }

    //    @Override
    //    public void onNothingSelected(AdapterView<?> parent) {

    //     }
    //  });


    @Override
    protected void onStart() {
        super.onStart();
        Log.v("RegisteActivity","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("RegisteActivity","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("RegisteActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("RegisteActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("RegisteActivity","onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("RegisteActivity","onRestart");
    }

    public void tiao(View v) {
        if (name.getText().toString().trim().equals(""))
            Toast.makeText(RegisteActivity.this, "请输入账户名", Toast.LENGTH_SHORT).show();
        else if (pass.getText().toString().trim().equals(""))
            Toast.makeText(RegisteActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
        else if (repass.getText().toString().trim().equals(""))
            Toast.makeText(RegisteActivity.this, "再次输入密码", Toast.LENGTH_SHORT).show();
        else if (!pass.getText().toString().trim().equals(repass.getText().toString().trim()))
            Toast.makeText(RegisteActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
        else {

            Toast.makeText(RegisteActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

            //获取账号和密码
            final String n1 = name.getText().toString().trim();
            String p1 = pass.getText().toString().trim();
            Intent nn = new Intent();
            nn.putExtra("name", n1);
            nn.putExtra("mima", p1);
            setResult(1, nn);
            //获取用户信息
            sex = ((RadioButton) findViewById(rg1.getCheckedRadioButtonId())).getText().toString().trim();
            String nianling = age1.getText().toString().trim();
            if (!nianling.equals(""))
                age = Integer.parseInt(age1.getText().toString().trim());
            else
                age = 0;
            fav = "";

            for (CheckBox ck : list) {

                if (ck.isChecked())
                    fav = fav + ck.getText().toString().trim();

                // Toast.makeText(RegisteActivity.this, sex + " " + age + "" + banji + fav, Toast.LENGTH_LONG).show();
                AlertDialog .Builder bb=new AlertDialog.Builder(RegisteActivity.this);
                bb.setTitle("请确定用户信息是否");
                bb.setMessage( "性别："+sex+ "  年龄：" + age + "  班级："+ banji+"  爱好："+ fav);
                bb.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ed.putString("name",name.getText().toString().toString().trim());
                        ed.putString("mima",pass.getText().toString().toString().trim());
                        ed.commit();
                        RegisteActivity.this.finish();


                    }
                });
                bb.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                bb.show();

            }


        }

        // finish();
    }
}
//  this.finish();
