package com.example.simple.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;
import com.example.simple.utils.MyTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WisdomBusFourActivity extends BaseActivity {
    private  ArrayList<String>value;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wisdom_bus_four_layout);
        setTitleText("智慧巴士");
        inView();
        setBusOnClick("提交订单",new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSubmitRequest();
            }
        });
    }
    private void startSubmitRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("setOrderBus")
                .setJsonObject("busid",AppClient.beanBus.getBusid())
                .setJsonObject("name",value.get(0))
                .setJsonObject("phone",value.get(1))
                .setJsonObject("upsite",value.get(2))
                .setJsonObject("downsite",value.get(3))
                .setJsonObject("traveltime",value.get(4))
                .setJsonObject("isPay","N")
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            String result="";
                            if (jsonObject.getString("RESULT").equals("S")){
                                result="提交成功";
                            }else {
                                result="提交失败";
                            }
                            MyTools.getInstance().showDialog(result, WisdomBusFourActivity.this,null, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                }).start();
    }
    private void inView(){
        value=getIntent().getStringArrayListExtra("ArrayList");
        TextView travel_date=findViewById(R.id.travel_date);
        TextView passenger_name_four=findViewById(R.id.passenger_name_four);
        TextView phone_four=findViewById(R.id.phone_four);
        TextView up_location_four=findViewById(R.id.up_location_four);
        TextView down_location_four=findViewById(R.id.down_location_four);
        passenger_name_four.setText(value.get(0));
        phone_four.setText(value.get(1));
        up_location_four.setText(value.get(2));
        down_location_four.setText(value.get(3));
        travel_date.setText(value.get(4));
    }
}
