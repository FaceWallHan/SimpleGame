package com.example.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.adapter.ViolationAdapter;
import com.example.simple.bean.BeanViolation;
import com.example.simple.utils.AllItemListView;

import java.util.ArrayList;
import java.util.List;

public class ViolationInfoActivity  extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private AllItemListView violation_lv;
    private Button more;
    private List<BeanViolation>violationList;
    private ViolationAdapter adapter;
    public static int number=5;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.violation_info_layout);
        inView();
        setTitleText(getResources().getString(R.string.violation_info));
        addData();
    }
    private void addData(){
        if (violationList.size()==0){
            violation_lv.setEmptyView(findViewById(R.id.empty_tv));

        }else if (violationList.size()>number){
            violationList=violationList.subList(0,number);
            more.setVisibility(View.VISIBLE);
        }
        adapter=new ViolationAdapter(ViolationInfoActivity.this,violationList);
        violation_lv.setAdapter(adapter);
    }
    private void inView(){
        violation_lv=findViewById(R.id.violation_lv);
        violation_lv.setOnItemClickListener(this);
        more=findViewById(R.id.more);
        more.setOnClickListener(this);
        violationList= new ArrayList<>();
        violationList.addAll(AppClient.getInstance().getViolationList());
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.more){
            if (violationList.size()==number){
                violationList.clear();
                violationList.addAll(AppClient.getInstance().getViolationList());
                more.setText("收起");
                adapter.notifyDataSetChanged();
            }else {
                violationList=violationList.subList(0,number);
                more.setText(getResources().getString(R.string.show_more));
                adapter=new ViolationAdapter(ViolationInfoActivity.this,violationList);
                violation_lv.setAdapter(adapter);
            }
            //List的大小改变与内容改变在notifyDataSetChanged()上有区别
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        BeanViolation violation = violationList.get(i);
        Intent intent=new Intent(ViolationInfoActivity.this,ViolationDetailsActivity.class);
        intent.putExtra("BeanViolation",violation);
        startActivity(intent);
    }
}
