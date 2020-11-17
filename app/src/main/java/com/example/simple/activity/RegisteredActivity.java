package com.example.simple.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.simple.R;
import com.example.simple.bean.BeanDepartment;
import com.example.simple.fragment.ExpertFragment;
import com.example.simple.fragment.OrdinaryFragment;

public class RegisteredActivity extends BaseActivity implements View.OnClickListener {
    private TextView expert,ordinary;
    private BeanDepartment department;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered_layout);
        setTitleText("专家挂号");
        replace(new ExpertFragment());
        inView();
    }
    private void inView(){
        expert=findViewById(R.id.expert);
        ordinary=findViewById(R.id.ordinary);
        expert.setOnClickListener(this);
        ordinary.setOnClickListener(this);
        department= (BeanDepartment) getIntent().getSerializableExtra("BeanDepartment");
    }
    private void replace(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.registered_frame,fragment);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.expert:
                setTitleText("专家挂号");
                replace(new ExpertFragment());
                expert.setBackgroundResource(R.drawable.bg_stroke);
                ordinary.setBackgroundResource(R.drawable.bg_stroke_white);
                break;
            case R.id.ordinary:
                setTitleText("普通挂号");
                replace(OrdinaryFragment.newInstance(department));
                ordinary.setBackgroundResource(R.drawable.bg_stroke);
                expert.setBackgroundResource(R.drawable.bg_stroke_white);
                break;
        }
    }
}
