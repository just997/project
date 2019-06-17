package com.lemapsdk.a20190506;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MyActivity extends BaseAdapter {
    ArrayList<HashMap<String,String>> datas;
    LayoutInflater inflater;
    Context c1;
    String curremtPath;
    public MyActivity(Context cc, ArrayList<HashMap<String,String>>data,String path){
        curremtPath=path;
        datas=new ArrayList<HashMap<String, String>>();
        inflater=LayoutInflater.from(cc);
        c1=cc;
        for (HashMap<String,String>ss:data){
            datas.add(ss);
        }
    }
    @Override
    public int getCount() {
        return datas.size();


    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView tt=null;
        TextView tt1=null;

        if (convertView==null) {

            convertView = inflater.inflate(R.layout.list_item, null);
        }
        tt = convertView.findViewById(R.id.tv);
        tt1=convertView.findViewById(R.id.bb3);
        tt.setText(datas.get(position).get("filename"));
        // Toast.makeText(c1,datas.get(position).get("filename"),Toast.LENGTH_SHORT).show();
        tt1.setText(datas.get(position).get("time"));



       /* bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String path= Environment.getExternalStorageDirectory().getPath();
                String filePath=curremtPath+File.separator+datas.get(position);
                File file=new File(filePath);

                Toast.makeText(c1,filePath,Toast.LENGTH_LONG).show();
                SharedPreferences sh=c1.getSharedPreferences("path",Context.MODE_PRIVATE);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("cpath",filePath);
                ed.commit();
                if (file.isFile()) {     //判断是否是文件夹
                    Intent ii = new Intent(c1, ListC.class);
                    ii.putExtra("filename", datas.get(position));
                    c1.startActivity(ii);
                } else

                {
                    Bundle bb=new Bundle();
                    bb.putString("request","main");
                    Intent ii=new Intent(c1,MainActivity.class);
                    ii.putExtra("dirname", datas.get(position));
                    ii.putExtra("nn", bb);
                    c1.startActivity(ii);
                }
            }

        });*/


        return convertView;
    }
}
