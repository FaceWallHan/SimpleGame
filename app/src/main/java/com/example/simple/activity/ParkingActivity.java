package com.example.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.adapter.ParkInfoAdapter;
import com.example.simple.bean.BeanParkInfo;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;
import com.example.simple.utils.AllItemListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParkingActivity extends BaseActivity implements View.OnClickListener , ParkInfoAdapter.OnItemBeanListener {
    private List<BeanParkInfo>infoList,second;
    private ParkInfoAdapter adapter;
    private Button park_more;
    private AllItemListView park_info;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_layout);
        setTitleText("停车场");
        inView();
        startParkInfoRequest();
    }
    private void inView(){
        ImageView history_park=findViewById(R.id.history_park);
        history_park.setOnClickListener(this);
        infoList=new ArrayList<>();
        park_info=findViewById(R.id.park_info);

        park_more=findViewById(R.id.park_more);
        park_more.setOnClickListener(this);
    }
    private void startParkInfoRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getParkInfor")
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                TypeToken<List<BeanParkInfo>>token=new  TypeToken<List<BeanParkInfo>>(){};
                                String json=jsonObject.getString("ROWS_DETAIL");
                                second=new Gson().fromJson(json,token.getType());
                                Collections.sort(second,new BeanParkInfo.DescDistance());
                                infoList.addAll(second);
                                if (infoList.size()>ViolationInfoActivity.number){
                                    infoList=infoList.subList(0,ViolationInfoActivity.number);
                                }
                                adapter=new ParkInfoAdapter(ParkingActivity.this,infoList);
                                adapter.setOnItemBeanListener(ParkingActivity.this);
                                park_info.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.history_park:
                startActivity(new Intent(ParkingActivity.this,ParkingRecordActivity.class));
                break;
            case R.id.park_more:
                adapter=new ParkInfoAdapter(ParkingActivity.this,second);
                adapter.setOnItemBeanListener(ParkingActivity.this);
                park_info.setAdapter(adapter);
                park_more.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void beanListener(BeanParkInfo info) {
        AppClient.parkingid=info.getParkingid();
        Intent intent=new Intent(ParkingActivity.this,ParkingInfoActivity.class);
        intent.putExtra("BeanParkInfo",info);
        startActivity(intent);
    }
}
