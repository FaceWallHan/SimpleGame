package com.example.simple.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.simple.R;
import com.example.simple.activity.ModifyPassWordActivity;
import com.example.simple.activity.PersonInfoActivity;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class PersonalFragment  extends Fragment implements View.OnClickListener {
    private View view;
    private static final int userId=1;
    private ImageView personal_head;
    private TextView personal_name;
    private String[] userInfo;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inView();
        startPersonRequest();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.personal_fragment_layout,container,false);
        return view;
    }
    private void inView(){
        personal_head=view.findViewById(R.id.personal_head);
        personal_name=view.findViewById(R.id.personal_name);
        userInfo=new String[5];
        Button personal_info = view.findViewById(R.id.personal_info);
        Button personal_order = view.findViewById(R.id.personal_order);
        Button personal_modify = view.findViewById(R.id.personal_modify);
        Button personal_back = view.findViewById(R.id.personal_back);
        TextView exit_login = view.findViewById(R.id.exit_login);
        personal_info.setOnClickListener(this);
        personal_order.setOnClickListener(this);
        personal_modify.setOnClickListener(this);
        personal_back.setOnClickListener(this);
        exit_login.setOnClickListener(this);

    }
    private void startPersonRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getUserInfo")
                .setJsonObject("userid",userId)
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                JSONObject object=array.getJSONObject(0);
                                String id=object.getString("id");
                                String name=object.getString("name");
                                String avatar=object.getString("avatar");
                                String phone=object.getString("phone");
                                String sex=object.getString("sex");
                                personal_name.setText(name);
                                Glide.with(view.getContext()).load(avatar).into(personal_head);
                                userInfo[0]=id;
                                userInfo[1]=name;
                                userInfo[2]=avatar;
                                userInfo[3]=phone;
                                if (sex.equals("male")||sex.equals("男")){
                                    userInfo[4]="男";
                                }else {
                                    userInfo[4]=sex;
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.personal_info:
                if (userInfo.length>0){
                    Intent intent=new Intent(view.getContext(), PersonInfoActivity.class);
                    intent.putExtra("userInfo",userInfo);
                    startActivity(intent);
                }
                break;
            case R.id.personal_order:
                break;
            case R.id.personal_modify:
                startActivity(new Intent(view.getContext(), ModifyPassWordActivity.class));
                break;
            case R.id.personal_back:
                break;
            case R.id.exit_login:
                break;
            default:
                break;
        }
    }
}
