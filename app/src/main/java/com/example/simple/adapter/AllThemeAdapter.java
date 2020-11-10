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

import java.util.List;

public class AllThemeAdapter extends ArrayAdapter<String> {
    private List<String> themeList;
    public AllThemeAdapter(@NonNull Context context,List<String> themeList) {
        super(context, 0);
        this.themeList=themeList;
    }
    /**
     * 主要任务还应该是推进功能的实现，不应在哪个点上过度的耽误太多的时间
     * */
    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.theme_item_layout,parent,false);
        TextView title_theme=convertView.findViewById(R.id.title_theme);
        TextView content_theme=convertView.findViewById(R.id.content_theme);
        String item=getItem(position);
        assert item != null;
        title_theme.setText(item.substring(0,1));
        content_theme.setText(item);
        return convertView;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return themeList.get(position);
    }

    @Override
    public int getCount() {
        return themeList.size();
    }
}
