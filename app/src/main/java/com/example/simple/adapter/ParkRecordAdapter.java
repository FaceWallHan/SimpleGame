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
import com.example.simple.bean.BeanParkRecord;

import java.util.List;

public class ParkRecordAdapter extends ArrayAdapter<BeanParkRecord> {
    private List<BeanParkRecord>recordList;
    private LayoutInflater inflater;
    public ParkRecordAdapter(@NonNull Context context, List<BeanParkRecord>recordList) {
        super(context, 0);
        this.recordList=recordList;
        inflater=LayoutInflater.from(context);
    }

    @Nullable
    @Override
    public BeanParkRecord getItem(int position) {
        return recordList.get(position);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=inflater.inflate(R.layout.parking_record_item_layout,parent,false);
        TextView record_plate=convertView.findViewById(R.id.record_plate);
        TextView record_charge=convertView.findViewById(R.id.record_charge);
        TextView approach=convertView.findViewById(R.id.approach);
        TextView appearance=convertView.findViewById(R.id.appearance);
        BeanParkRecord item = getItem(position);
        assert item != null;
        record_plate.setText(item.getCarNum());
        record_charge.setText(item.getCharge());
        approach.setText(item.getInTime());
        appearance.setText(item.getOutTime());
        return convertView;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }
}
