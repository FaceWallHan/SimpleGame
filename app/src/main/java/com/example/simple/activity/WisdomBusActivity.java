package com.example.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.adapter.WisdomBusAdapter;
import com.example.simple.bean.BeanBus;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WisdomBusActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private List<BeanBus>busList;
    private WisdomBusAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wisdom_bus_layout);
        setTitleText("智慧巴士");
        inView();
        startBueRequest();
    }
    private void inView(){
        busList=new ArrayList<>();
        ListView bus_lv=findViewById(R.id.bus_lv);
        adapter=new WisdomBusAdapter(this,busList);
        bus_lv.setAdapter(adapter);
        bus_lv.setOnItemClickListener(this);
    }
    private void startBueRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("busList")
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                TypeToken<List<BeanBus>>token=new  TypeToken<List<BeanBus>>(){};
                                String json=jsonObject.getString("ROWS_DETAIL");
                                busList.addAll(new Gson().fromJson(json,token.getType()));
                                adapter.notifyDataSetChanged();
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AppClient.beanBus=busList.get(i);
        startActivity(new Intent(WisdomBusActivity.this,WisdomBusOneActivity.class));
    }
}
