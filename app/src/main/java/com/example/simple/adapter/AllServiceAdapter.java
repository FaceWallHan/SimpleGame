package com.example.simple.adapter;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
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
    private OnChildGridClickListener onChildGridClickListener;

    public void setOnChildGridClickListener(OnChildGridClickListener onChildGridClickListener) {
        this.onChildGridClickListener = onChildGridClickListener;
    }

    public AllServiceAdapter(Context context, Map<String, List<BeanServiceType>> child, List<String> group) {
        this.context = context;
        this.child = child;
        this.group = group;
    }
    public interface OnChildGridClickListener{
        void OnOnChildGridClick(String type);
    }
    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
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
        GridView grid_service=convertView.findViewById(R.id.grid_service);
        List<BeanServiceType> serviceTypes = child.get(group.get(groupPosition));
        ServicesTypeAdapter adapter=new ServicesTypeAdapter(context,serviceTypes);
        grid_service.setAdapter(adapter);
        grid_service.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (onChildGridClickListener!=null){
                    onChildGridClickListener.OnOnChildGridClick(serviceTypes.get(i).getServiceName());
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
