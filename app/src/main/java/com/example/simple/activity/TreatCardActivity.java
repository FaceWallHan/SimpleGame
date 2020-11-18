package com.example.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.adapter.TreatCardAdapter;
import com.example.simple.bean.BeanTreat;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;
import com.example.simple.utils.DataKeys;
import com.example.simple.utils.MyTools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TreatCardActivity extends BaseActivity implements View.OnClickListener , AdapterView.OnItemClickListener {
    private List<BeanTreat>treatList;
    private TreatCardAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.treat_card_layout);
        setTitleText("在线挂号");
        inView();
        MyTools.getInstance().addActivity(this);
        startTreatRequest();
    }
    private void inView(){
        Button add_treat=findViewById(R.id.add_treat);
        add_treat.setOnClickListener(this);
        ListView treat_lv=findViewById(R.id.treat_lv);
        treat_lv.setOnItemClickListener(this);
        treatList=new ArrayList<>();
        adapter=new TreatCardAdapter(this,treatList);
        adapter.setOnArrowListener(new TreatCardAdapter.OnArrowListener() {
            @Override
            public void arrow() {
                startActivity(new Intent(TreatCardActivity.this,DepartmentActivity.class));
            }
        });
        treat_lv.setAdapter(adapter);
    }
    private void startTreatRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("showCaseById")
                .setJsonObject("ID", MyTools.getInstance().getData(DataKeys.id_Number,"371402199902041133"))
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                TypeToken<List<BeanTreat>>token=new  TypeToken<List<BeanTreat>>(){};
                                String json=jsonObject.getString("ROWS_DETAIL");
                                treatList.addAll(new Gson().fromJson(json,token.getType()));
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
    public void onClick(View view) {
        if (view.getId()==R.id.add_treat){
            createInfo();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        createInfo();
    }
    private void createInfo(){
        Intent intent=new Intent(TreatCardActivity.this,CreateTreatActivity.class);
        startActivity(intent);
        finish();
    }
}
