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
import com.example.simple.bean.BeanManager;

import java.util.List;

public class ManageAdapter extends ArrayAdapter<BeanManager> {
    private List<BeanManager>managerList;
    private LayoutInflater inflater;
    public ManageAdapter(@NonNull Context context, List<BeanManager>managerList) {
        super(context, 0);
        this.managerList=managerList;
        inflater=LayoutInflater.from(context);
    }

    @Nullable
    @Override
    public BeanManager getItem(int position) {
        return managerList.get(position);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=inflater.inflate(R.layout.personal_manage_item_layout,parent,false);
        TextView type=convertView.findViewById(R.id.type);
        TextView accountAddress=convertView.findViewById(R.id.accountAddress);
        TextView cost=convertView.findViewById(R.id.cost);
        TextView banlance=convertView.findViewById(R.id.banlance);
        TextView accountId=convertView.findViewById(R.id.accountId);
        TextView unit=convertView.findViewById(R.id.unit);
        BeanManager item = getItem(position);
        assert item != null;
        type.setText(item.getType());
        accountAddress.setText(item.getAccountAddress());
        cost.setText(item.getCost());
        banlance.setText(item.getBanlance());
        accountId.setText(item.getAccountId());
        unit.setText(item.getUnit());
        return convertView;
    }

    @Override
    public int getCount() {
        return managerList.size();
    }
}
