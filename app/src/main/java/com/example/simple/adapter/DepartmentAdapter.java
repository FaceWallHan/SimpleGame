package com.example.simple.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.simple.R;
import com.example.simple.bean.BeanDepartment;

import java.util.List;

public class DepartmentAdapter extends ArrayAdapter<BeanDepartment> {
    private List<BeanDepartment>departmentList;
    private LayoutInflater inflater;
    public DepartmentAdapter(@NonNull Context context, List<BeanDepartment>departmentList) {
        super(context, 0);
        this.departmentList=departmentList;
        inflater=LayoutInflater.from(context);
    }

    @Nullable
    @Override
    public BeanDepartment getItem(int position) {
        return departmentList.get(position);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=inflater.inflate(R.layout.department_item_layout,parent,false);
        TextView department_name=convertView.findViewById(R.id.department_name);
        TextView department_tag=convertView.findViewById(R.id.department_tag);
        TextView department_desc=convertView.findViewById(R.id.department_desc);
        BeanDepartment item = getItem(position);
        assert item != null;
        department_desc.setText(item.getDesc());
        department_tag.setText(item.getTag());
        department_name.setText(item.getDeptName());
        return convertView;
    }

    @Override
    public int getCount() {
        return departmentList.size();
    }
}
