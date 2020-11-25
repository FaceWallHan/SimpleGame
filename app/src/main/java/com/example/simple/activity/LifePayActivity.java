package com.example.simple.activity;

import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.simple.R;
import com.example.simple.fragment.AutoPayFragment;
import com.example.simple.fragment.ExpertFragment;
import com.example.simple.fragment.PersonalManageFragment;
import com.example.simple.utils.MyTools;

public class LifePayActivity extends BaseActivity implements View.OnClickListener {
    private TextView auto_pay,personal_id_manage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 需要动态获取权限
         * */
        setContentView(R.layout.life_pay_layout);
        inView();
        setTitleText("自动缴费");
        replaceFragment(new AutoPayFragment());
    }
    private void inView(){
        auto_pay=findViewById(R.id.auto_pay);
        personal_id_manage=findViewById(R.id.personal_id_manage);
        auto_pay.setOnClickListener(this);
        personal_id_manage.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.auto_pay:
                setTitleText("自动缴费");
                replaceFragment(new AutoPayFragment());
                auto_pay.setBackgroundResource(R.drawable.bg_stroke);
                personal_id_manage.setBackgroundResource(R.drawable.bg_stroke_white);
                break;
            case R.id.personal_id_manage:
                setTitleText("户号管理");
                replaceFragment(new PersonalManageFragment());
                personal_id_manage.setBackgroundResource(R.drawable.bg_stroke);
                auto_pay.setBackgroundResource(R.drawable.bg_stroke_white);
                break;
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.life_frame,fragment);
        transaction.commit();
    }

}
