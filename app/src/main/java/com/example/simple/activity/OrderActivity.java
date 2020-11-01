package com.example.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.simple.R;
import com.example.simple.adapter.OrderAdapter;
import com.example.simple.bean.BeanOrder;
import com.example.simple.bean.BeanServiceType;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private List<BeanOrder>orderList;
    private OrderAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);
        setTitle(getResources().getString(R.string.personal_order));
        inView();
        startTypeRequest();
    }
    private void inView(){
        ListView order_list = findViewById(R.id.order_list);
        orderList=new ArrayList<>();
        adapter=new OrderAdapter(OrderActivity.this,orderList);
        order_list.setAdapter(adapter);
        order_list.setOnItemClickListener(this);
    }
    private void startOrderRequest(String type){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setJsonObject("type",type)
                .setUrl("getOrderByType")
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                TypeToken<List<BeanOrder>> token=new TypeToken<List<BeanOrder>>(){};
                                String json=jsonObject.getString("ROWS_DETAIL");
                                orderList.addAll(new Gson().fromJson(json,token.getType()));
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
    private void startTypeRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getAllOrderType")
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                for (int i = 0; i < array.length(); i++) {
                                    startOrderRequest(array.getString(i));
                                }
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
        String id = orderList.get(i).getId();
        Intent intent=new Intent(OrderActivity.this,OrderInfoActivity.class);
        intent.putExtra("orderId",id);
        startActivity(intent);
    }
}
