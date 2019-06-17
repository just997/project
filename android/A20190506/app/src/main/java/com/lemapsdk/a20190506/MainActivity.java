package com.lemapsdk.a20190506;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.FrameMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends Activity {
    ListView listView;
    ArrayList<HashMap<String,String>> data=new ArrayList<HashMap<String, String>>();
    public static String cPath="";
    TextView tt;
    @Override
    protected void onCreate(@Nullable Bundle savedinstanceState){
        super.onCreate(savedinstanceState);
        setContentView(R.layout.main);
        //获取sdcand路径
        File sdDir= Environment.getExternalStorageDirectory();
        //判断sd卡是否存在
        int i=0;
        String ss="";

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            data.clear();
            ss="";
            File[] files=sdDir.listFiles();//读取sd卡目录下的所有目录
            for (File ff:files)
            {
                HashMap<String,String> map=new HashMap<String, String>();
                map.put("filename",ff.getName());
                long tt=ff.lastModified();
                String ctime=new SimpleDateFormat("yyy-MM-dd hh:mm:ss").format(new Date(tt));
                map.put("time",ctime);
                data.add(map);
                ss+=","+ff;
                i++;

            }
        }
        tt=findViewById(R.id.path);
        tt.setText(sdDir.getPath());

        Intent ii=getIntent();
        //tt.setText(sdDir.getPath());
        Bundle bb=ii.getBundleExtra("nn");
        String isFrom=bb.getString("request");

        if (isFrom.equals("main"))
        {
            //获取文件夹名拼成文件夹的路径并显示，在获取该文件夹下所有文件
            //  String dirName=ii.getStringExtra("dirname");
            //  String dirpath=tt.getText().toString().trim()+File.separator+dirName;
            SharedPreferences sh1=getSharedPreferences("path",MODE_PRIVATE);
            String cpath=sh1.getString("cpath","asd");
            tt.setText(cpath);
            File ff=new File(cpath);
            File[]files=ff.listFiles();
            Toast.makeText(MainActivity.this,cpath,Toast.LENGTH_LONG).show();
            data.clear();
            if (files!=null)
                for (File f:files) {
                    HashMap<String,String> map=new HashMap<String, String>();
                    map.put("filename",f.getName());
                    long tt1=f.lastModified();
                    String ctime=new SimpleDateFormat("yyy-MM-dd hh:mm:ss").format(new Date(tt1));
                    map.put("time",ctime);
                    data.add(map);


                }
        }

        listView=findViewById(R.id.list);
        MyActivity adapter=new MyActivity(MainActivity.this,data,tt.getText().toString().trim());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String filePath=tt.getText().toString().trim()+File.separator+data.get(position).get("filename");
                File file=new File(filePath);
                Toast.makeText(MainActivity.this,filePath,Toast.LENGTH_LONG).show();
                SharedPreferences sh=MainActivity.this.getSharedPreferences("path",MODE_PRIVATE);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("cpath",filePath);
                ed.commit();
                if (file.isFile()) {     //判断是否是文件夹
                    Intent ii = new Intent(MainActivity.this, ListC.class);
                    ii.putExtra("filename", data.get(position).get("filename"));
                    startActivity(ii);
                } else if (file.isDirectory())

                {
                    Bundle bb=new Bundle();
                    bb.putString("request","main");
                    Intent ii=new Intent(MainActivity.this,MainActivity.class);
                    ii.putExtra("dirname",data.get(position).get("filename"));
                    ii.putExtra("nn", bb);
                    startActivity(ii);
                }
            }


        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder bb=new AlertDialog.Builder(MainActivity.this);
                bb.setTitle("请确定文件详情");
                bb.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                LayoutInflater ll=LayoutInflater.from(MainActivity.this);
                LinearLayout vv=(LinearLayout) ll.inflate(R.layout.myduading,null);
                TextView tv1=vv.findViewById(R.id.tv1);
                tv1.setText("文件名："+data.get(position).get("filename"));
                TextView tv2=vv.findViewById(R.id.tv2);
                tv2.setText("文件创建日期:   "+data.get(position).get("time"));
                bb.setView(vv);
                bb.show();
                return true;
            }
        });

    }
}