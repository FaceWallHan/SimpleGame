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
        TextView search_title=convertView.findViewById(R.id.search_title);
        TextView search_content=convertView.findViewById(R.id.search_content);
        TextView news_comment=convertView.findViewById(R.id.news_comment);
        TextView news_time=convertView.findViewById(R.id.news_time);
        ImageView search_picture=convertView.findViewById(R.id.search_picture);
        String title=beanNews.getTitle();
        if (title.length()>20){
            title=title.substring(0,20)+"...";
        }
        search_title.setText(title);
        search_content.setText(beanNews.getContent().substring(0,40)+"...");
        Glide.with(parent.getContext()).load(beanNews.getPicture()).into(search_picture);
        if (beanNews.getPraiseCount()!=null){
            news_comment.setText(beanNews.getPraiseCount()+"评论");
        }
        news_time.setText(beanNews.getPublicTime());
        return convertView;
    }
}
