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
import com.example.simple.bean.BeanTreat;

import java.util.List;

public class TreatCardAdapter extends ArrayAdapter<BeanTreat> {
    private LayoutInflater inflater;
    private List<BeanTreat>treatList;
    private OnArrowListener onArrowListener;

    public void setOnArrowListener(OnArrowListener onArrowListener) {
        this.onArrowListener = onArrowListener;
    }

    public TreatCardAdapter(@NonNull Context context, List<BeanTreat>treatList) {
        super(context, 0);
        this.treatList=treatList;
        inflater=LayoutInflater.from(context);
    }
    public interface OnArrowListener{
        void arrow();
    }
    @Override
    public int getCount() {
        return treatList.size();
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=inflater.inflate(R.layout.treat_card_item_layout,parent,false);
        TextView treat_name=convertView.findViewById(R.id.treat_name);
        TextView treat_sex=convertView.findViewById(R.id.treat_sex);
        TextView treat_id=convertView.findViewById(R.id.treat_id);
        TextView treat_time=convertView.findViewById(R.id.treat_time);
        TextView treat_tel=convertView.findViewById(R.id.treat_tel);
        TextView treat_address=convertView.findViewById(R.id.treat_address);
        ImageView department_iv=convertView.findViewById(R.id.department_iv);
        BeanTreat item = getItem(position);
        assert item != null;
        treat_name.setText(item.getName());
        treat_sex.setText(item.getSex());
        treat_id.setText(item.getID());
        treat_tel.setText(item.getTel());
        treat_time.setText(item.getBirthday());
        treat_address.setText(item.getAddress());
        department_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onArrowListener!=null){
                    onArrowListener.arrow();
                }
            }
        });
        return convertView;
    }

    @Nullable
    @Override
    public BeanTreat getItem(int position) {
        return treatList.get(position);
    }
}
