package com.example.simple.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.simple.R;
import com.example.simple.bean.BeanServiceType;

import java.util.List;

public class ServicesTypeAdapter extends ArrayAdapter<BeanServiceType> {
    private List<BeanServiceType>typeList;
    private LayoutInflater inflater;
    public ServicesTypeAdapter(@NonNull Context context, List<BeanServiceType>typeList) {
        super(context, 0);
        this.typeList=typeList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return typeList.size();
    }

    protected static class ViewHolder{
        ImageView entrance_image;
        TextView entrance_text;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        BeanServiceType serviceType=typeList.get(position);
        if (convertView==null){
            holder=new ViewHolder();
            convertView=inflater.inflate(R.layout.entrance_item_layout,parent,false);
            holder.entrance_image=convertView.findViewById(R.id.entrance_image);
            holder.entrance_text=convertView.findViewById(R.id.entrance_text);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        if (position==9){
            Glide.with(parent.getContext()).load(serviceType.getIcon()).into(holder.entrance_image);
            holder.entrance_text.setText("更多");
        }else {
            Glide.with(parent.getContext()).load(serviceType.getIcon()).into(holder.entrance_image);
            holder.entrance_text.setText(serviceType.getServiceName());
        }

        return convertView;
    }

    @Nullable
    @Override
    public BeanServiceType getItem(int position) {
        return typeList.get(position);
    }
}
