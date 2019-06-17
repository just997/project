package com.lemapsdk.a20190506;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class woAdapter extends BaseAdapter {
    Context cc1;
    ArrayList<Object> data;
    private static final int TVPE1=0;
    private static final int TVPE2=1;
    private static final int TVPE3=2;
    public woAdapter(Context cc, ArrayList<Object> dd){
        cc1=cc;
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
    public int getItemViewType(int position) {

        if ( data.get(position) instanceof Person)
            return TVPE1;
        else if (data.get(position) instanceof Other)
            return TVPE2;
        else
            return TVPE3;

    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        viewHolder1 vh1 = null;
        viewHolder2 vh2 = null;
        viewHolder3 vh3 = null;

        if (convertView == null) {

            switch (type) {
                case TVPE1:
                    vh1 = new viewHolder1();
                    convertView = LayoutInflater.from(cc1).inflate(R.layout.list_item1, null);
                    vh1.imageView = convertView.findViewById(R.id.vv1);
                    vh1.tt = convertView.findViewById(R.id.cc1);
                    convertView.setTag(R.id.tag_person, vh1);
                    break;
                case TVPE2:
                    vh2 = new viewHolder2();
                    convertView = LayoutInflater.from(cc1).inflate(R.layout.list_item2, null);
                    vh2.imageView = convertView.findViewById(R.id.img1);
                    vh2.textView = convertView.findViewById(R.id.tt2);
                    convertView.setTag(R.id.tag_other, vh2);
                    break;
                case TVPE3:
                    vh3 = new viewHolder3();
                    convertView = LayoutInflater.from(cc1).inflate(R.layout.list_item3, null);
                    vh3.textView = convertView.findViewById(R.id.tt3);
                    convertView.setTag(R.id.tag_other1, vh3);
                    break;
            }

        } else {

            switch (type) {

                case TVPE1:
                    vh1 = (viewHolder1) convertView.getTag(R.id.tag_person);
                    break;
                case TVPE2:
                    vh2 = (viewHolder2) convertView.getTag(R.id.tag_other);
                    break;
                case TVPE3:
                    vh3 = (viewHolder3) convertView.getTag(R.id.tag_other1);
                    break;
            }
        }
        Object mm=data.get(position);
        switch (type){

            case TVPE1:
                Person pp=(Person) mm;
                if (pp!=null){

                    //vh1.imageView.setImageResource(pp.getPath());
                    vh1.tt.setText(pp.getName());
                }
                break;
            case TVPE2:
                Other oo=(Other) mm;
                if (oo!=null)
                {
                    vh2.textView.setText(oo.nn);
                }
                break;
            case TVPE3:
                vh3.textView.setText("退出");
                break;
        }
        return convertView;

    }

}
