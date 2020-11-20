package com.example.simple.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.simple.R;
import com.example.simple.bean.BeanParkInfo;

public class ParkingInfoActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_info_layout);
        setTitleText("停车场详情");
        BeanParkInfo parkInfo= (BeanParkInfo) getIntent().getSerializableExtra("BeanParkInfo");
        TextView park_info_tv=findViewById(R.id.park_info_tv);
        assert parkInfo != null;
        String open="";
        if (parkInfo.getIsOpen().equals("Y")){
            open="对外开放";
        }else if (parkInfo.getIsOpen().equals("N")){
            open="不对外开放";
        }
        String info="停车场名："+parkInfo.getParkName()
                    +"\n空位数量："+parkInfo.getSpaceNum()
                    +"\n地址："+parkInfo.getAddress()
                    +"\n收费费率："+parkInfo.getRate()
                    +"\n距离："+parkInfo.getDistance()
                    +"\n是否对外开放："+open
                    +"\n收费参考："+parkInfo.getParkName()
                    +"\n剩余车位："+parkInfo.getSurCarPort();
        park_info_tv.setText(info);
    }
}
