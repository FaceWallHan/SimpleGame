package com.example.simple.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.adapter.AllServiceAdapter;
import com.example.simple.bean.BeanServiceType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ApplyServiceFragment extends Fragment {
    private View view;
    private ExpandableListView all_service;
    private AppClient client;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.apply_service_layout,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inView();
        addData();
    }
    private void addData(){
        Map<String, List<BeanServiceType>> map = client.getListMap();
        List<String> typeList = client.getTypeList();
        AllServiceAdapter serviceAdapter=new AllServiceAdapter(getContext(),map,typeList);
        all_service.setAdapter(serviceAdapter);
    }
    private void inView(){
        all_service=view.findViewById(R.id.all_service);
        client= (AppClient) getActivity().getApplication();
    }
}
