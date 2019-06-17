package com.example.a20190426;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *
 * */
public class MyAdapter extends BaseAdapter {
    ArrayList<Student> data;
    Context context;
    public MyAdapter(Context cc, ArrayList<Student> dd){
        context=cc;
        data=dd;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.arraylistlayout,null);
        }
        /**用LayoutInflater方法，并用convertView.findViewById绑定TextView，装载数据到ListView
         * */
        TextView name=convertView.findViewById(R.id.tt1);
        TextView age=convertView.findViewById(R.id.tt2);
        TextView sex=convertView.findViewById(R.id.tt3);
        name.setText(data.get(position).getName());
        age.setText(data.get(position).getAge()+"");
        sex.setText(data.get(position).getGender());
        return convertView;
    }
}
