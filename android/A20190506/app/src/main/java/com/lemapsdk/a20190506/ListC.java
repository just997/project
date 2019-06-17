package com.lemapsdk.a20190506;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ListC extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fistfile);
        Intent ii=getIntent();
        String fname=ii.getStringExtra("filename");
        TextView tt=findViewById(R.id.tt);
        tt.setText(fname);

        TextView tt1=findViewById(R.id.tt1);
        SharedPreferences sh=getSharedPreferences("path",MODE_PRIVATE);
        String path=sh.getString("cpath","/root/");
        // Toast.makeText(this,path,Toast.LENGTH_SHORT).show();
        //读取指定路径文件内容
        String data="";
        File ff=new File(path);
        try {
            InputStream inputStream = new FileInputStream(ff);

            if (inputStream != null) {
                InputStreamReader in = new InputStreamReader(inputStream);
                BufferedReader bufread = new BufferedReader(in);
                String line;
                while ((line = bufread.readLine()) != null) {
                    data = data + line + "\n"; }inputStream.close(); } }
        catch (Exception ee){
            Log.v("error",ee.getMessage());
        }
        tt1.setText(data);
    }
}