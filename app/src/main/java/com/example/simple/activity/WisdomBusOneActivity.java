package com.example.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.bean.BeanBus;

public class WisdomBusOneActivity extends BaseActivity  {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wisdom_bus_one_layout);
        setTitleText("智慧巴士");
        inView();
        setBusOnClick("下一步",new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WisdomBusOneActivity.this,WisdomBusTwoActivity.class));
                finish();
            }
        });
    }
    private void inView(){
        BeanBus bus=AppClient.beanBus;
        TextView site_one=findViewById(R.id.site_one);
        String sumSite=bus.getStartSite()+"\n|\n"+bus.getEndSite();
        site_one.setText(sumSite);
        TextView cost_one=findViewById(R.id.cost_one);
        cost_one.setText(bus.getPrice());
        TextView mileage_one=findViewById(R.id.mileage_one);
        mileage_one.setText(bus.getMileage());
    }
}
