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
import com.example.simple.bean.BeanNews;

import java.util.List;

public class SearchNewsAdapter extends ArrayAdapter<BeanNews> {
    private List<BeanNews >list;
    private LayoutInflater inflater;
    public SearchNewsAdapter(@NonNull Context context,List<BeanNews >list) {
        super(context, 0);
        this.list=list;
        inflater=LayoutInflater.from(context);
    }


    @Nullable
    @Override
    public BeanNews getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= inflater.inflate(R.layout.search_news_item_layout,parent,false);
        BeanNews beanNews=list.get(position);
        TextView search_head=convertView.findViewById(R.id.search_head);
        TextView search_title=convertView.findViewById(R.id.search_title);
        TextView search_content=convertView.findViewById(R.id.search_content);
        ImageView search_picture=convertView.findViewById(R.id.search_picture);
        search_head.setText(beanNews.getNewsId()+beanNews.getNewsType());
        search_title.setText(beanNews.getTitle());
        search_content.setText(beanNews.getContent());
        Glide.with(parent.getContext()).load(beanNews.getPicture()).into(search_picture);
        return convertView;
    }
}
