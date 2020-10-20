package com.example.simple;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.simple.bean.BeanServiceType;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppClient extends LitePalApplication {
    @SuppressLint("StaticFieldLeak")
    private  static AppClient instance;
    private static RequestQueue requestQueue;
    private Map<String ,List<BeanServiceType>>child;
    private List<String>group;

    public static void add(JsonObjectRequest jsonObjectRequest) {
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        requestQueue= Volley.newRequestQueue(this);
        child=new HashMap<>();
        group=new ArrayList<>();
    }

    public Map<String, List<BeanServiceType>> getListMap() {
        return child;
    }

    public List<String> getTypeList() {
        return group;
    }

    public static AppClient getInstance(){
        return instance;
    }
}
