package com.example.simple.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.simple.R;
import com.example.simple.bean.BeanHospital;

import java.util.List;

public class HospitalAdapter extends ArrayAdapter<BeanHospital> {
    private List<BeanHospital>hospitalList;
    private LayoutInflater inflater;
    public HospitalAdapter(@NonNull Context context,  List<BeanHospital>hospitalList) {
        super(context, 0);
        this.hospitalList=hospitalList;
        inflater=LayoutInflater.from(context);
    }

    @Nullable
    @Override
    public BeanHospital getItem(int position) {
        return hospitalList.get(position);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=inflater.inflate(R.layout.show_hospital_item_layout,parent,false);
        TextView hospital_tv=convertView.findViewById(R.id.hospital_tv);
        ImageView hospital_iv=convertView.findViewById(R.id.hospital_iv);
        RatingBar horizontal_rat=convertView.findViewById(R.id.horizontal_rat);
        BeanHospital hospital=getItem(position);
        assert hospital != null;
        Glide.with(parent.getContext()).load(hospital.getPicture()).into(hospital_iv);
        hospital_tv.setText(hospital.getHospitalName());
        horizontal_rat.setRating(hospital.getRank());
        return convertView;
    }

    @Override
    public int getCount() {
        return hospitalList.size();
    }
}
