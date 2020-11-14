package com.example.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.bean.BeanViolation;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViolationActivity extends BaseActivity implements View.OnClickListener {
    private List<String>numberList,plateList;
    private Spinner number_sp,plate_sp;
    private EditText engine_et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.violation_layout);
        setTitleText("违章查询");
        inView();
        startNumberRequest();
        startPlateRequest();
    }
    private void startPlateRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getCarPlace")
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object=array.getJSONObject(i);
                                    String name=object.getString("name");
                                    plateList.add(name);
                                }
                                ArrayAdapter<String>adapter=new ArrayAdapter<String>(ViolationActivity.this,android.R.layout.simple_spinner_item,plateList);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                plate_sp.setAdapter(adapter);
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
    private void startNumberRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getAllCarType")
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object=array.getJSONObject(i);
                                    String name=object.getString("name");
                                    numberList.add(name);
                                }
                                ArrayAdapter<String>adapter=new ArrayAdapter<String>(ViolationActivity.this,android.R.layout.simple_spinner_item,numberList);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                number_sp.setAdapter(adapter);
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
    private void inView(){
        numberList=new ArrayList<>();
        plateList=new ArrayList<>();
        number_sp=findViewById(R.id.number_sp);
        plate_sp=findViewById(R.id.plate_sp);
        engine_et=findViewById(R.id.engine_et);
        Button inquire=findViewById(R.id.inquire);
        inquire.setOnClickListener(this);
    }
    private void startViolationInfoRequest(String id){
        String carId=plate_sp.getSelectedItem().toString()+id;
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getViolationsByCarId")
                .setDialog(ViolationActivity.this)
                .setJsonObject("carid",carId)
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                TypeToken<List<BeanViolation>> typeToken=new  TypeToken<List<BeanViolation>>(){};
                                String json=jsonObject.getString("ROWS_DETAIL");
                                List<BeanViolation> violationList = new Gson().fromJson(json,typeToken.getType());
                                AppClient.getInstance().getViolationList().clear();
                                AppClient.getInstance().getViolationList().addAll(violationList);
                                startActivity(new Intent(ViolationActivity.this,ViolationInfoActivity.class));
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
        if (view.getId()==R.id.inquire){
            String engine=engine_et.getText().toString().trim();
            if (!engine.equals("")){
                startViolationInfoRequest(engine);
            }
        }
    }
}
