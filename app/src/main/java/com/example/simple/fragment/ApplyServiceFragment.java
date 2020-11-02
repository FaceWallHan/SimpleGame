package com.example.simple.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.activity.SubwayActivity;
import com.example.simple.adapter.AllServiceAdapter;
import com.example.simple.bean.BeanServiceType;


import java.util.List;
import java.util.Map;

public class ApplyServiceFragment extends Fragment {
    private View view;
    private ExpandableListView all_service;
    private Map<String, List<BeanServiceType>> map;
    private List<String> typeList;
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
                if (type.equals("地铁查询")){
                    startActivity(new Intent(view.getContext(), SubwayActivity.class));
                }
            }
        });

    }
    private void inView(){
        all_service=view.findViewById(R.id.all_service);
        map = AppClient.getInstance().getListMap();
        typeList = AppClient.getInstance().getTypeList();
    }
}
