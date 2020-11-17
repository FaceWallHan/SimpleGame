package com.example.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.simple.R;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;
import com.example.simple.utils.MyTools;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateTreatActivity extends BaseActivity implements View.OnClickListener {
    private EditText name_tv,sex_tv,identity_tv,birthday_tv,phone_tv,address_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_treat_layout);
        setTitleText(getResources().getString(R.string.create_treat));
        inView();
    }
    private void inView(){
        Button submit_treat = findViewById(R.id.submit_treat);
        name_tv=findViewById(R.id.name_tv);
        sex_tv=findViewById(R.id.sex_tv);
        identity_tv=findViewById(R.id.identity_tv);
        birthday_tv=findViewById(R.id.birthday_tv);
        phone_tv=findViewById(R.id.phone_tv);
        address_tv=findViewById(R.id.address_tv);
        submit_treat.setOnClickListener(this);
    }
    private void startSubmitRequest(String name,String sex,String id,String birthday,String tel,String address){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("createCase")
                .setJsonObject("name",name)
                .setJsonObject("sex",sex)
                .setJsonObject("ID",id)
                .setJsonObject("birthday",birthday)
                .setJsonObject("tel",tel)
                .setJsonObject("address",address)
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                Intent intent=new Intent(CreateTreatActivity.this,TreatCardActivity.class);
                                startActivity(intent);
                                MyTools.getInstance().showToast("创建成功",CreateTreatActivity.this);
                                finish();
                            }else {
                                MyTools.getInstance().showDialog("创建失败",CreateTreatActivity.this,null);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            MyTools.getInstance().showDialog("创建失败",CreateTreatActivity.this,null);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        MyTools.getInstance().showDialog("创建失败",CreateTreatActivity.this,null);
                    }
                }).start();
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.submit_treat){
            String name=name_tv.getText().toString().trim();
            String sex=sex_tv.getText().toString().trim();
            String identity=identity_tv.getText().toString().trim();
            String birthday=birthday_tv.getText().toString().trim();
            String phone=phone_tv.getText().toString().trim();
            String address=address_tv.getText().toString().trim();
            if (!identity.equals("")){
                startSubmitRequest(name,sex,identity,birthday,phone,address);
            }else {
                MyTools.getInstance().showDialog("身份证号不能为空",CreateTreatActivity.this,null);
            }
        }
    }
}
