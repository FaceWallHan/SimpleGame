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

import com.example.simple.R;
import com.example.simple.bean.BeanOrder;
import com.example.simple.bean.BeanOrderInfo;

import java.util.List;

public class OrderInfoAdapter extends ArrayAdapter<BeanOrderInfo> {
    private List<BeanOrderInfo>list;
    private LayoutInflater inflater;
    public OrderInfoAdapter(@NonNull Context context, List<BeanOrderInfo>list) {
        super(context, 0);
        this.list=list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public BeanOrderInfo getItem(int position) {
        return list.get(position);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= inflater.inflate(R.layout.order_info_item_layout,parent,false);
        TextView business=convertView.findViewById(R.id.business);
        TextView commodity=convertView.findViewById(R.id.commodity);
        TextView price=convertView.findViewById(R.id.price);
        TextView count=convertView.findViewById(R.id.count);
        BeanOrderInfo order=getItem(position);
        assert order != null;
        business.setText(order.getBusiness());
        commodity.setText(order.getCommodity());
        price.setText(order.getPrice());
        count.setText(order.getCount());
        return convertView;
    }
}
