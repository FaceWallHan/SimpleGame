package com.example.simple.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.activity.MovableActivity;
import com.example.simple.activity.OrderActivity;
import com.example.simple.activity.SubwayActivity;
import com.example.simple.adapter.AllServiceAdapter;
import com.example.simple.bean.BeanServiceType;
import com.example.simple.utils.MyTools;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ApplyServiceFragment extends Fragment implements TextView.OnEditorActionListener {
    private View view;
    private ExpandableListView all_service;
    private Map<String, List<BeanServiceType>> map;
    private List<String> typeList,serviceList;
    private EditText search_et;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.apply_service_fragment_layout,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inView();
        addData();
    }
    private void addData(){
        AllServiceAdapter serviceAdapter=new AllServiceAdapter(getContext(),map,typeList);
        all_service.setAdapter(serviceAdapter);

        serviceAdapter.setOnChildGridClickListener(new AllServiceAdapter.OnChildGridClickListener() {
            @Override
            public void OnOnChildGridClick(String type) {
                startActivity(MyTools.getInstance().judgmentIntent(type,view.getContext()));
            }
        });

    }
    private void inView(){
        all_service=view.findViewById(R.id.all_service);
        map = AppClient.getInstance().getListMap();
        typeList = AppClient.getInstance().getTypeList();
        search_et=view.findViewById(R.id.search_et);
        search_et.setOnEditorActionListener(this);
        serviceList=new ArrayList<>();
        for (String s : map.keySet()) {
            List<BeanServiceType> serviceTypes = map.get(s);
            assert serviceTypes != null;
            for (int i = 0; i < serviceTypes.size(); i++) {
                String name=serviceTypes.get(i).getServiceName();
                serviceList.add(name);
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int index, KeyEvent keyEvent) {
        if (index== EditorInfo.IME_ACTION_SEARCH){
            String str=search_et.getText().toString().trim();
            boolean is=false;
            for (int j = 0; j < serviceList.size(); j++) {
                if (serviceList.get(j).equals(str)) {
                    is=true;
                    break;
                }
            }
            if (is){
                startActivity(MyTools.getInstance().judgmentIntent(str,view.getContext()));
            }else {
                MyTools.getInstance().showDialog("请输入正确的服务！",view.getContext(),null);
                search_et.setText("");
            }
            return true;
        }
        return false;
    }
}
