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
import com.example.simple.bean.BeanOrder;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<BeanOrder> {
    private List<BeanOrder>list;
    private LayoutInflater inflater;
    public OrderAdapter(@NonNull Context context, List<BeanOrder>list) {
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
    public BeanOrder getItem(int position) {
        return list.get(position);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= inflater.inflate(R.layout.order_item_layout,parent,false);
        TextView id=convertView.findViewById(R.id.id);
        TextView type=convertView.findViewById(R.id.type);
        TextView date=convertView.findViewById(R.id.date);
        TextView cost=convertView.findViewById(R.id.cost);
        BeanOrder order=getItem(position);
        assert order != null;
        id.setText(order.getId());
        type.setText(order.getType());
        date.setText(order.getDate());
        cost.setText(order.getCost());
        return convertView;
    }
}
