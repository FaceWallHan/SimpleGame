package com.example.simple.activity;

import android.os.Bundle;
import android.util.Log;
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

public class OpinionActivity extends BaseActivity implements View.OnClickListener {
    private EditText opinion_et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opinion_layout);
        setTitle(getResources().getString(R.string.personal_back));
        inView();
    }
    private void inView(){
        Button submit_opinion = findViewById(R.id.submit_opinion);
        submit_opinion.setOnClickListener(this);
        opinion_et=findViewById(R.id.opinion_et);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.submit_opinion){
            String opinion=opinion_et.getText().toString().trim();
            if (opinion.equals(""))return;
            if (opinion.length()>150){
                MyTools.getInstance().showToast("字数限制在150字以内",OpinionActivity.this);
                return;
            }
            VolleyTo volleyTo=new VolleyTo();
            volleyTo.setUrl("publicOpinion")
                    .setJsonObject("content",opinion)
                    .setJsonObject("userid",1)
                    .setJsonObject("time", MyTools.getInstance().getNowTime("yyyy.MM.dd HH:mm:ss"))
                    .setVolleyLo(new NetCall() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            try {
                                if (jsonObject.getString("RESULT").equals("S")){
                                    MyTools.getInstance().showDialog("提交成功",OpinionActivity.this,null);
                                }else {
                                    MyTools.getInstance().showDialog("提交失败",OpinionActivity.this,null);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                        }
                    }).start();
        }
    }
}
