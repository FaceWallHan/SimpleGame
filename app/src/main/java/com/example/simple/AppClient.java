package com.example.simple;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

public class AppClient extends LitePalApplication {
    @SuppressLint("StaticFieldLeak")
    private  static AppClient instance;
    private static RequestQueue requestQueue;

    public static void add(JsonObjectRequest jsonObjectRequest) {
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        requestQueue= Volley.newRequestQueue(this);
    }
    public static AppClient getInstance(){
        return instance;
    }
}
