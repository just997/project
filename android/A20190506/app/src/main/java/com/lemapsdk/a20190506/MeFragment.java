package com.lemapsdk.a20190506;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MeFragment extends Fragment {
    ArrayList<Object> datas=new ArrayList<Object>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wode, container, false);

        ListView ll=view.findViewById(R.id.ll3);

        Person pp=new Person();
        pp.setName("wangjingpeng");
        pp.setPath("http://hxei.com.cn/677.img");
        datas.add(pp);
        Other ooo1=new Other();
        ooo1.nn="收藏";
        Other ooo2=new Other();
        ooo2.nn="相册";
        Other1 oo1=new Other1();
        datas.add(ooo1);
        datas.add(ooo2);
        datas.add(oo1);

        woAdapter adapter=new woAdapter(getActivity(),datas);
        ll.setAdapter(adapter);
        ll.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity()," "+position,Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
