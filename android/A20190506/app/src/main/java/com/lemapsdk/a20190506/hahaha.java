package com.lemapsdk.a20190506;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class hahaha extends AppCompatActivity {
    Button zhuce;
    EditText et1,et2;
    SharedPreferences sh;
    SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hahaha);

        Log.v("hahaha","onCreate");
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        zhuce=(Button) findViewById(R.id.bb2);
        zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(hahaha.this,RegisteActivity.class);

                //startActivity(ii);
                startActivityForResult(ii,1);
            }
        });
        sh=getSharedPreferences("user",MODE_PRIVATE);
        ed=sh.edit();

    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.v("hahaha","onStart");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.v("hahaha","onResume");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v("hahaha","onPause");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.v("hahaha","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("hahaha","onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("hahaha","onRQWestart");
    }


    public void login(View vv){
        Intent ii=new Intent(hahaha.this,MainPage.class);

        String name=et1.getText().toString().trim();
        String pass=et2.getText().toString().trim();
        if (name.equals(""))
            Toast.makeText(hahaha.this,"1",Toast.LENGTH_SHORT).show();
        if (pass.equals(""))
            Toast.makeText(hahaha.this,"1",Toast.LENGTH_SHORT).show();
        else {
            String n1=sh.getString("name","qwe");
            String p1=sh.getString("mima","123");
            if (name.equals(n1) && pass.equals(p1)){
                Bundle bb=new Bundle();
                bb.putString("ming",name);
                bb.putString("mima",pass);
                bb.putString("request","login");
                ii.putExtra("nn",bb);
                startActivity(ii);

            }
            else
                Toast.makeText(hahaha.this,"密码错误！",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCo,int resultCode,Intent data){
        super.onActivityResult(resultCode, resultCode, data);
        if (resultCode==1){
            String n1=data.getExtras().getString("name");
            String p1=data.getExtras().getString("mima");
            et1.setText(n1);
            et2.setText(p1);

        }

    }

}
