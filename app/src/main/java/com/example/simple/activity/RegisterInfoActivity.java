package com.example.simple.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.simple.R;
import com.example.simple.bean.BeanAttend;
import com.example.simple.utils.MyTools;
import com.google.android.material.snackbar.Snackbar;

public class RegisterInfoActivity  extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered_info_layout);
        setTitleText("挂号信息");
        TextView registered_info=findViewById(R.id.registered_info);
        BeanAttend attend= (BeanAttend) getIntent().getSerializableExtra("BeanAttend");
        assert attend != null;
        String info="门诊类型：普通号"
                +"\n预约时间：" +attend.getTime()
                +"\n预约科室：" +attend.getDepartmentName()
                +"\n医生号：" +attend.getDoctorId()
                +"\n点击预约";
        registered_info.setText(info);
        MyTools.getInstance().addActivity(this);
        registered_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"预约成功",Snackbar.LENGTH_SHORT)
                        .setAction("我知道了", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MyTools.getInstance().clearActivity();
                            }
                        }).show();
            }
        });
    }
}
