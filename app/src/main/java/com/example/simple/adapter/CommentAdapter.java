package com.example.simple.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.simple.R;
import com.example.simple.bean.BeanComment;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<BeanComment> {
    private List<BeanComment> commentList;
    private LayoutInflater inflater;
    public CommentAdapter(@NonNull Context context, List<BeanComment> commentList) {
        super(context, 0);
        this.commentList=commentList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Nullable
    @Override
    public BeanComment getItem(int position) {
        return commentList.get(position);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=inflater.inflate(R.layout.comment_item_layout,parent,false);
        TextView comment_name=convertView.findViewById(R.id.comment_name);
        TextView comment_time=convertView.findViewById(R.id.comment_time);
        TextView comment_content=convertView.findViewById(R.id.comment_content);
        BeanComment item = getItem(position);
        assert item != null;
        comment_name.setText(item.getReviewer());
        comment_time.setText(item.getCommitTime());
        comment_content.setText(item.getCommit());
        return convertView;
    }
}
