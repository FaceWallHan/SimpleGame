package com.example.simple;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.simple.bean.BeanBus;
import com.example.simple.bean.BeanDepartment;
import com.example.simple.bean.BeanNews;
import com.example.simple.bean.BeanServiceType;
import com.example.simple.bean.BeanViolation;


import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppClient extends LitePalApplication {
    @SuppressLint("StaticFieldLeak")
    private  static AppClient instance;
    public   SharedPreferences preferences;
    private static RequestQueue requestQueue;
    private Map<String ,List<BeanServiceType>>child;
    private List<String>group;
    private List<BeanNews> newsList;
    private List<BeanViolation> violationList;

    public static void add(JsonObjectRequest jsonObjectRequest) {
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        requestQueue= Volley.newRequestQueue(this);
        child=new HashMap<>();
        group=new ArrayList<>();
        newsList=new ArrayList<>();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        violationList=new ArrayList<>();
    }

    public List<BeanNews> getNewsList() {
        return newsList;
    }

    public Map<String, List<BeanServiceType>> getListMap() {
        return child;
    }

    public List<String> getTypeList() {
        return group;
    }

    public List<BeanViolation> getViolationList() {
        return violationList;
    }

    public static String hospitalId="123";
    public static String parkingid="1";
    public static BeanBus beanBus;
    public static AppClient getInstance(){
        return instance;
    }
}
