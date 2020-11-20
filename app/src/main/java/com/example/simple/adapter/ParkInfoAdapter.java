package com.example.simple.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.simple.R;
import com.example.simple.bean.BeanParkInfo;

import java.util.List;

public class ParkInfoAdapter extends ArrayAdapter<BeanParkInfo> {
    private List<BeanParkInfo>infoList;
    private LayoutInflater inflater;
    private OnItemBeanListener onItemBeanListener;

    public void setOnItemBeanListener(OnItemBeanListener onItemBeanListener) {
        this.onItemBeanListener = onItemBeanListener;
    }

    public ParkInfoAdapter(@NonNull Context context, List<BeanParkInfo>infoList) {
        super(context, 0);
        this.infoList=infoList;
        inflater=LayoutInflater.from(context);
    }
    public interface OnItemBeanListener{
        void beanListener(BeanParkInfo info);
    }
    @Override
    public int getCount() {
        return infoList.size();
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=inflater.inflate(R.layout.parking_item_layout,parent,false);
        TextView park_name=convertView.findViewById(R.id.park_name);
        TextView park_num=convertView.findViewById(R.id.park_num);
        TextView park_address=convertView.findViewById(R.id.park_address);
        TextView park_distance=convertView.findViewById(R.id.park_distance);
        TextView park_rate=convertView.findViewById(R.id.park_rate);
        Button details=convertView.findViewById(R.id.details);
        BeanParkInfo item = getItem(position);
        assert item != null;
        park_name.setText(item.getParkName());
        park_num.setText(item.getSpaceNum());
        park_address.setText(item.getAddress());
        park_distance.setText(String.valueOf(item.getDistance()));
        park_rate.setText(item.getRate());
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemBeanListener!=null){
                    onItemBeanListener.beanListener(item);
                }
            }
        });
        return convertView;
    }

    @Nullable
    @Override
    public BeanParkInfo getItem(int position) {
        return infoList.get(position);
    }
}
