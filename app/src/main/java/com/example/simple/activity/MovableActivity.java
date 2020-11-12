package com.example.simple.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.simple.R;
import com.example.simple.adapter.AllThemeAdapter;
import com.example.simple.adapter.MovableAdapter;
import com.example.simple.bean.BeanAction;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovableActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private ViewFlipper movable_flipper;
    private GridView movable_theme;
    private ListView movable_list;
    private List<BeanAction> actionList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movable_layout);
        setTitleText("活动");
        inView();
        startImageRequest();
        startTypeRequest();
        startAllActionRequest();
    }
    private void inView(){
        movable_list=findViewById(R.id.movable_list);
        movable_theme=findViewById(R.id.movable_theme);
        movable_flipper=findViewById(R.id.movable_flipper);
        movable_list.setOnItemClickListener(this);
    }
    private void startAllActionRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getAllActions")
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            TypeToken<List<BeanAction>>token=new TypeToken<List<BeanAction>>(){};
                            String json=jsonObject.getString("ROWS_DETAIL");
                            actionList=new Gson().fromJson(json,token.getType());
                            MovableAdapter adapter=new MovableAdapter(MovableActivity.this,actionList);
                            movable_list.setAdapter(adapter);
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
        volleyTo.setUrl("getAllActionType")
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                List<String> type=new ArrayList<>();
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                for (int i = 0; i < array.length(); i++) {
                                    type.add(array.getJSONObject(i).getString("typename"));
                                }
                                AllThemeAdapter adapter=new AllThemeAdapter(MovableActivity.this,type);
                                movable_theme.setAdapter(adapter);
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
    private void startImageRequest(){
        VolleyTo request=new VolleyTo();
        request .setUrl("getActionImages")
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object=array.getJSONObject(i);
                                    movable_flipper.addView(loadImage(object),params);
                                }
                                movable_flipper.startFlipping();
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
    private void pointActivity(int id){
        Intent intent=new Intent(MovableActivity.this,MovableInfoActivity.class);
        intent.putExtra("BeanAction",id);
        startActivity(intent);
    }
    private ImageView loadImage(JSONObject jsonObject){
        ImageView imageView=new ImageView(this);
        try {
            Glide.with(this).load(jsonObject.getString("image")).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    pointActivity(jsonObject.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        return imageView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        BeanAction action = actionList.get(i);
        pointActivity(action.getId());
    }
}
