package com.example.simple.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.simple.R;
import com.example.simple.bean.BeanViolation;

public class ViolationDetailsActivity extends BaseActivity {
    private BeanViolation violation;
    private LinearLayout punishment_layout,violation_layout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.violation_info_details_layout);
        inView();
        addLayout();
    }
    private void addLayout(){
        violation_layout.addView(addGroupLayout("违法时间",violation.getTime()));
        violation_layout.addView(addGroupLayout("违法地点",violation.getPlace()));
        violation_layout.addView(addGroupLayout("违法行为",violation.getDescription()));
        punishment_layout.addView(addGroupLayout("通知书号",violation.getNotification()));
        punishment_layout.addView(addGroupLayout("违章记分",violation.getDeductPoints()));
        punishment_layout.addView(addGroupLayout("罚款金额",violation.getCost()));
    }
    private LinearLayout addGroupLayout(String type,String content){
        LinearLayout layout=new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(addTextView(type,1));
        layout.addView(addTextView(content,2));
        layout.setBackgroundResource(R.drawable.bg_stroke);
        return layout;
    }
    private TextView addTextView(String content,int weight){
        TextView value=new TextView(this);
        value.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,weight));
        value.setTextSize(20);
        value.setText(content);
        return value;
    }
    private void inView(){
        violation= (BeanViolation) getIntent().getSerializableExtra("BeanViolation");
        violation_layout=findViewById(R.id.violation_layout);
        punishment_layout=findViewById(R.id.punishment_layout);
    }
}
