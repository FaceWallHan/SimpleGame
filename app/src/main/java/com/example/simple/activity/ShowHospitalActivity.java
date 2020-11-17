package com.example.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;

import androidx.annotation.Nullable;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.adapter.HospitalAdapter;
import com.example.simple.bean.BeanHospital;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowHospitalActivity  extends BaseActivity implements AdapterView.OnItemClickListener {
    private List<BeanHospital>hospitalList;
    private HospitalAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_hospital_layout);
        setTitleText("门诊预约");
        startHospitalRequest();
        inView();
    }
    private void inView(){
        ListView hospital_lv=findViewById(R.id.hospital_lv);
        hospitalList=new ArrayList<>();
        adapter=new HospitalAdapter(this,hospitalList);
        hospital_lv.setAdapter(adapter);
        hospital_lv.setOnItemClickListener(this);
    }
    private void startRankRequest(String hospitalId){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getRankByHospitalId")
                .setJsonObject("hospitalId",hospitalId)
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                JSONObject object=array.getJSONObject(0);
                                float rank= (float) object.getDouble("rank");
                                for (int i = 0; i < hospitalList.size(); i++) {
                                    BeanHospital hospital = hospitalList.get(i);
                                    if (hospital.getHospitalId().equals(hospitalId)){
                                        hospital.setRank(rank);
                                        hospitalList.set(i,hospital);
                                    }
                                }
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
    private void startHospitalRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("hospitalList")
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object=array.getJSONObject(i);
                                    BeanHospital hospital=new BeanHospital();
                                    String hospitalId=object.getString("hospitalId");
                                    hospital.setHospitalId(hospitalId);
                                    hospital.setHospitalName(object.getString("hospitalName"));
                                    hospital.setPicture(object.getString("picture"));
                                    hospital.setDesc(object.getString("desc"));
                                    hospitalList.add(hospital);
                                    startRankRequest(hospitalId);
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
        BeanHospital hospital = hospitalList.get(i);
        Intent intent=new Intent(ShowHospitalActivity.this,HospitalInfoActivity.class);
        intent.putExtra("BeanHospital",hospital);
        AppClient.hospitalId=hospital.getHospitalId();
        startActivity(intent);
    }
}
