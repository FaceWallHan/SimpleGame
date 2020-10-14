package com.example.simple.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.simple.R;
import com.example.simple.activity.SearchNewsActivity;
import com.example.simple.adapter.ViewPagerAdapter;
import com.example.simple.net.NetCall;
import com.example.simple.net.NetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private View view;
    private EditText search;
    private ViewFlipper flipper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.home_fragment_layout,container,false);
        return view;
    }
    private void inView(){
        search=view.findViewById(R.id.search);
        flipper=view.findViewById(R.id.flipper);
    }
    @SuppressLint("ClickableViewAccessibility")
    private void setClick(){
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i== EditorInfo.IME_ACTION_SEARCH){
                    String str=search.getText().toString().trim();
                    Intent intent=new Intent();
                    Log.d("11111111111111111", "onEditorAction: "+str);
                    intent.putExtra("asdfg",str);
                    startActivity(new Intent(getContext(), SearchNewsActivity.class));
                    return true;
                }
                return true;
            }
        });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inView();
        setClick();
        startImageRequest();
    }
    private void startImageRequest(){
        NetRequest request=new NetRequest();
        request .setUrl("getImages")
                .setNetCall(new NetCall() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("RESULT").equals("S")){
                        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                        JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object=array.getJSONObject(i);
                            flipper.addView(loadImage(object,view.getContext()),params);
                        }
                        flipper.startFlipping();
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
    private ImageView loadImage(JSONObject jsonObject, Context context){
        ImageView imageView=new ImageView(context);
        try {
            Glide.with(view.getContext()).load(jsonObject.getString("path")).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent=new Intent(view.getContext(),SearchNewsActivity.class);
                    intent.putExtra("imageNum",jsonObject.getString("num"));
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        return imageView;
    }
}
