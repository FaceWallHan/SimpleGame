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
    private MoreListener listener;

    public void setListener(MoreListener listener) {
        this.listener = listener;
    }

    public ServicesTypeAdapter(@NonNull Context context, List<BeanServiceType>typeList) {
        super(context, 0);
        inflater=LayoutInflater.from(context);
        this.typeList=typeList;
    }
    public interface MoreListener{
        void listen();
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
            Glide.with(parent.getContext()).load(R.drawable.more_service).into(holder.entrance_image);
            holder.entrance_text.setText("更多");
            holder.entrance_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.listen();
                }
            });
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
