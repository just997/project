package com.lemapsdk.a20190506;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ZhidaoFragment extends Fragment {
    ListView listView;
    ArrayList<HashMap<String,String>> data=new ArrayList<HashMap<String, String>>();
    public static String cPath="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main, container, false);

        //获取SDCard路径
        File sdDir= Environment.getExternalStorageDirectory();
        //判断SD卡是否存在,并且是否有读写权限
        int i=0;
        String ss="";
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            data.clear();
            ss="";
            File[] files=sdDir.listFiles();//读取sd卡目录下的所有文件
            for(File ff:files)
            {
                HashMap<String,String> map=new HashMap<String,String>();
                map.put("filename",ff.getName());
                long tt=ff.lastModified();
                String ctime= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(tt));
                map.put("time",ctime);
                data.add(map);
                ss+=","+ff;
                i++;
            }
        }
        final TextView tt=view.findViewById(R.id.path);
        tt.setText(sdDir.getPath());
        //SharedPreferences sh=getSharedPreferences("path",MODE_PRIVATE);
        //SharedPreferences.Editor ed=sh.edit();
        //ed.putString("cpath",sdDir.getPath());
        Intent ii=getActivity().getIntent();
        Bundle bb=ii.getBundleExtra("nn");
        String isFrom=bb.getString("request");
        if(isFrom.equals("main"))
        {
            //获取传过来的文件夹的名，拼成文件夹的路径并显示，再获取该文件夹下面所有文件名
            //Toast.makeText(MainVActivityiew.this,"asdadada",Toast.LENGTH_LONG).show();
            SharedPreferences sh1=getActivity().getSharedPreferences("path", Context.MODE_PRIVATE);
            String cpath=sh1.getString("cpath","asd");
            tt.setText(cpath);
            File ff=new File(cpath);
            File[] files=ff.listFiles();
            Toast.makeText(getActivity(),cpath,Toast.LENGTH_LONG).show();
            data.clear();
            if(files!=null)
                for (File f:files) {
                    HashMap<String,String> map=new HashMap<String,String>();
                    map.put("filename",f.getName());
                    long tt1=f.lastModified();
                    String ctime= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(tt1));
                    map.put("time",ctime);
                    data.add(map);
                    //data.add(f.getName());
                }
        }

        listView=view.findViewById(R.id.list);
        MyActivity adapter=new MyActivity(getActivity(),data,tt.getText().toString().trim());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity(),"asdsadad",Toast.LENGTH_LONG).show();
                String filePath=tt.getText().toString().trim()+File.separator+data.get(position).get("filename");
                File file=new File(filePath);
                Toast.makeText(getActivity(),filePath,Toast.LENGTH_LONG).show();
                // 保存当前路径到参数文件中
                SharedPreferences sh=getActivity().getSharedPreferences("path",Context.MODE_PRIVATE);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("cpath",filePath);
                ed.commit();
                if(file.isFile()) //判断是否是文件夹
                //如果是文件则跳转新页面显示文件内容，否则获取该文件夹的文件重新装载
                {
                    Intent ii=new Intent(getActivity(),ListC.class);
                    ii.putExtra("filename",data.get(position).get("filename"));
                    startActivity(ii);
                }
                else if(file.isDirectory())
                {
                    Bundle bb = new Bundle();
                    bb.putString("request","main");//用于判断从哪个页面跳转来的
                    Intent ii=new Intent(getActivity(),MainPage.class);
                    ii.putExtra("dirname",data.get(position).get("filename"));
                    ii.putExtra("nn", bb);
                    startActivity(ii);
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder bb=new AlertDialog.Builder(getActivity());
                bb.setTitle("请确定文件详情");
                bb.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                LayoutInflater ll=LayoutInflater.from(getActivity());
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




        return view;
    }
}

