package com.example.simple.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.simple.R;
import com.example.simple.bean.BeanBus;

import java.util.List;

public class WisdomBusAdapter extends ArrayAdapter<BeanBus> {
    private List<BeanBus>busList;
    private LayoutInflater inflater;
    public WisdomBusAdapter(@NonNull Context context,List<BeanBus>busList) {
        super(context, 0);
        this.busList=busList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return busList.size();
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=inflater.inflate(R.layout.wisdom_bus_item_layout,parent,false);
        TextView pathName=convertView.findViewById(R.id.pathName);
        TextView price=convertView.findViewById(R.id.price);
        TextView mileage=convertView.findViewById(R.id.mileage);
        TextView site=convertView.findViewById(R.id.site);
        TextView runTime=convertView.findViewById(R.id.runTime);
        BeanBus item = getItem(position);
        assert item != null;
        pathName.setText(item.getPathName());
        price.setText(item.getPrice());
        mileage.setText(item.getMileage());
        String sumSite=item.getStartSite()+"——"+item.getEndSite();
        site.setText(sumSite);
        String sumTime=item.getRunTime1()+"\n"+item.getRunTime2();
        runTime.setText(sumTime);
        return convertView;
    }

    @Nullable
    @Override
    public BeanBus getItem(int position) {
        return busList.get(position);
    }
}
