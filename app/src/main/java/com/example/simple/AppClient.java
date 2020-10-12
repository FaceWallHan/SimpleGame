package com.example.simple;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

public class AppClient extends LitePalApplication {
    @SuppressLint("StaticFieldLeak")
    private  static AppClient instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
    public static AppClient getInstance(){
        return instance;
    }
}
