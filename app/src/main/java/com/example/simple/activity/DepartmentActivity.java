package com.example.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.adapter.DepartmentAdapter;
import com.example.simple.bean.BeanDepartment;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;
import com.example.simple.utils.MyTools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DepartmentActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private List<BeanDepartment>departmentList;
    private DepartmentAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_layout);
        setTitleText("门诊科室分诊");
        inView();
        MyTools.getInstance().addActivity(this);
        startDepartmentRequest();
    }
    private void inView(){
        ListView department_lv=findViewById(R.id.department_lv);
        department_lv.setOnItemClickListener(this);
        departmentList=new ArrayList<>();
        adapter=new DepartmentAdapter(DepartmentActivity.this,departmentList);
        department_lv.setAdapter(adapter);
    }
    private void startDepartmentRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getHospitalDepartmentByHospitalId")
                .setJsonObject("hospitalId", AppClient.hospitalId)
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                TypeToken<List<BeanDepartment>>token=new  TypeToken<List<BeanDepartment>>(){};
                                String json=jsonObject.getString("ROWS_DETAIL");
                                List<BeanDepartment> list=new Gson().fromJson(json,token.getType());
                                departmentList.addAll(list);
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
        BeanDepartment department = departmentList.get(i);
        Intent intent = new Intent(DepartmentActivity.this, RegisteredActivity.class);
        intent.putExtra("BeanDepartment",department);
        startActivity(intent);
    }
}
