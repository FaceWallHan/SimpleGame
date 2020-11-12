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

import com.bumptech.glide.Glide;
import com.example.simple.R;
import com.example.simple.bean.BeanAction;

import java.util.List;

public class MovableAdapter extends ArrayAdapter<BeanAction> {
    private List<BeanAction> actionList;
    private LayoutInflater inflater;
    public MovableAdapter(@NonNull Context context, List<BeanAction> actionList) {
        super(context, 0);
        this.actionList=actionList;
        inflater=LayoutInflater.from(context);
    }

    @Nullable
    @Override
    public BeanAction getItem(int position) {
        return actionList.get(position);
    }

    @Override
    public int getCount() {
        return actionList.size();
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=inflater.inflate(R.layout.movable_item_layout,parent,false);
        ImageView movable_image=convertView.findViewById(R.id.movable_image);
        TextView movable_name=convertView.findViewById(R.id.movable_name);
        TextView movable_count=convertView.findViewById(R.id.movable_count);
        TextView movable_praise=convertView.findViewById(R.id.movable_praise);
        BeanAction item = getItem(position);
        assert item != null;
        Glide.with(parent.getContext()).load(item.getImage()).into(movable_image);
        String name=item.getName();
        if (name.length()>15){
            name=name.substring(0,14);
        }
        movable_name.setText(name);
        movable_count.setText("活动人数:"+item.getCount());
        movable_praise.setText("点赞数:"+item.getPraiseCount());
        return convertView;
    }
}
