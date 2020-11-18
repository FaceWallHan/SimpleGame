package com.example.simple.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.simple.bean.BeanAttend;

import java.util.List;

public class OrdinaryAdapter extends ArrayAdapter<BeanAttend> {
    private LayoutInflater inflater;
    private List<BeanAttend> attendList;
    public OrdinaryAdapter(@NonNull Context context,List<BeanAttend> attendList) {
        super(context, 0);
        this.attendList=attendList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return attendList.size();
    }

    @Nullable
    @Override
    public BeanAttend getItem(int position) {
        return attendList.get(position);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
        TextView text1=convertView.findViewById(android.R.id.text1);
        BeanAttend item = getItem(position);
        assert item != null;
        String str=item.getTime()+"\t\t"+item.getDepartmentName();
        text1.setText(str);
        return convertView;
    }
}
