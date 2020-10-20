package com.example.simple.adapter;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.simple.R;
import com.example.simple.bean.BeanServiceType;

import java.util.List;
import java.util.Map;

public class AllServiceAdapter extends BaseExpandableListAdapter {
    private Context context;
    private Map<String , List<BeanServiceType>> child;
    private List<String>group;

    public AllServiceAdapter(Context context, Map<String, List<BeanServiceType>> child, List<String> group) {
        this.context = context;
        this.child = child;
        this.group = group;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return child.get(group.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return group.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return child.get(group.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
    /**
     *
     * 获取显示指定组的视图对象
     *
     * @param groupPosition 组位置
     * @param isExpanded 该组是展开状态还是伸缩状态，true=展开
     * @param convertView 重用已有的视图对象
     * @param parent 返回的视图对象始终依附于的视图组
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,parent,false);
        TextView text=convertView.findViewById(android.R.id.text1);
        text.setText(group.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(R.layout.service_item_layout,parent,false);
//        ImageView service_image=convertView.findViewById(R.id.service_image);
//        TextView service_text=convertView.findViewById(R.id.service_text);
        BeanServiceType serviceType = child.get(group.get(groupPosition)).get(childPosition);
//        Glide.with(context).load(serviceType.getIcon()).into(service_image);
//        service_text.setText(serviceType.getServiceName());
        LinearLayout child_layout=convertView.findViewById(R.id.child_layout);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(50,75);
        child_layout.addView(addLayoutItem(serviceType),params);
  //          child_layout.getChildCount();
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
    private LinearLayout addLayoutItem(BeanServiceType serviceType){
        LinearLayout layout=new LinearLayout(context);
        ImageView service_image=new ImageView(context);
        TextView service_text=new TextView(context);
        service_text.setGravity(Gravity.CENTER);
        service_text.setTextSize(10);

        Glide.with(context).load(serviceType.getIcon()).into(service_image);
        service_text.setText(serviceType.getServiceName());

        LinearLayout.LayoutParams imageParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,5);
        LinearLayout.LayoutParams textParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,2);
        layout.addView(service_image,imageParams);
        layout.addView(service_text,textParams);
        return layout;
    }
}
