package com.example.simple.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.simple.R;
import com.example.simple.bean.BeanHospital;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HospitalInfoActivity extends BaseActivity implements View.OnClickListener {
    private BeanHospital hospital;
    private ViewFlipper flipper_hi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital_info_layout);
        inView();
        startImageRequest();
    }
    private void inView(){
        hospital= (BeanHospital) getIntent().getSerializableExtra("BeanHospital");
        flipper_hi=findViewById(R.id.flipper_hi);
        setTitleText(hospital.getHospitalName());
        TextView hospital_info = findViewById(R.id.hospital_info);
        String desc="\t\t"+hospital.getDesc();
        hospital_info.setText(desc);
        Button reservation=findViewById(R.id.reservation);
        reservation.setOnClickListener(this);
    }
    private void startImageRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getImagesByHospitalId")
                .setJsonObject("hospitalId",hospital.getHospitalId())
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                JSONObject object=array.getJSONObject(0);
                                for (int i = 1; i < 5; i++) {
                                    flipper_hi.addView(loadImage(object.getString("image"+i)));
                                }
                                flipper_hi.startFlipping();
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
    private ImageView loadImage(String path){
        ImageView imageView=new ImageView(this);
        Glide.with(this).load(path).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.reservation){
            startActivity(new Intent(HospitalInfoActivity.this,TreatCardActivity.class));
        }
    }
}
