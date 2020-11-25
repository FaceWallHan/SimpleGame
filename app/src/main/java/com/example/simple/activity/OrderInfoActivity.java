package com.example.simple.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.simple.R;
import com.example.simple.adapter.OrderInfoAdapter;
import com.example.simple.bean.BeanOrderInfo;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderInfoActivity extends BaseActivity {
    private List<BeanOrderInfo>infoList;
    private String orderId;
    private OrderInfoAdapter adapter;
    private TextView order_info;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_info_layout);
        setTitleText("订单详情");
        inView();
        startInfoRequest(orderId);
    }
    private void inView(){
        ListView order_info_list=findViewById(R.id.order_info_list);
        order_info=findViewById(R.id.order_info);
        order_info_list.setEmptyView(findViewById(R.id.empty_tv));
        orderId=getIntent().getStringExtra("orderId");
        infoList=new ArrayList<>();
        adapter=new OrderInfoAdapter(OrderInfoActivity.this,infoList);
        order_info_list.setAdapter(adapter);
    }
    private void setData(JSONObject jsonObject){
        String id= "";
        try {
            id = getResources().getString(R.string.order_id)+":"+jsonObject.getString("id");
            String type=getResources().getString(R.string.order_type)+":"+jsonObject.getString("type");
            String date=getResources().getString(R.string.order_date)+":"+jsonObject.getString("date");
            String cost=getResources().getString(R.string.order_cost)+":"+jsonObject.getString("cost");
            TypeToken<List<BeanOrderInfo>> token=new TypeToken<List<BeanOrderInfo>>(){};
            String json=jsonObject.getString("ROWS_DETAIL");
            infoList.addAll(new Gson().fromJson(json,token.getType()));
            adapter.notifyDataSetChanged();
            String info=id+"\n"+type+"\n"+date+"\n"+cost;
            order_info.setText(info);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void startInfoRequest(String orderId){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getOrderById")
                .setJsonObject("id",orderId)
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                setData(jsonObject);
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
}
