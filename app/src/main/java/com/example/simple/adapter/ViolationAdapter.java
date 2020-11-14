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
import com.example.simple.bean.BeanViolation;

import java.util.List;

public class ViolationAdapter extends ArrayAdapter<BeanViolation> {
    private List<BeanViolation>violationList;
    private LayoutInflater inflater;
    public ViolationAdapter(@NonNull Context context,List<BeanViolation>violationList) {
        super(context, 0);
        this.violationList=violationList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return violationList.size();
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=inflater.inflate(R.layout.violation_info_item_layout,parent,false);
        TextView violation_time=convertView.findViewById(R.id.violation_time);
        TextView violation_place=convertView.findViewById(R.id.violation_place);
        TextView violation_status=convertView.findViewById(R.id.violation_status);
        TextView violation_plate=convertView.findViewById(R.id.violation_plate);
        TextView deductPoints=convertView.findViewById(R.id.deductPoints);
        TextView violation_cost=convertView.findViewById(R.id.violation_cost);
        BeanViolation item = getItem(position);
        assert item != null;
        violation_time.setText(item.getTime());
        violation_place.setText(item.getPlace());
        violation_plate.setText(item.getCarid());
        if (item.getNotification().equals("1")){
            violation_status.setText("已处理");
        }else {
            violation_status.setText("未处理");
        }
        deductPoints.setText(item.getDeductPoints());
        violation_cost.setText(item.getCost());
        return convertView;
    }

    @Nullable
    @Override
    public BeanViolation getItem(int position) {
        return violationList.get(position);
    }
}
