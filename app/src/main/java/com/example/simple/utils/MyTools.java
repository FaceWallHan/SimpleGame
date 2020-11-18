package com.example.simple.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.activity.MovableActivity;
import com.example.simple.activity.ShowHospitalActivity;
import com.example.simple.activity.SubwayActivity;
import com.example.simple.activity.ViolationActivity;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyTools {
    private  static SharedPreferences preferences;
    private  static MyTools myTools;
    private static  SharedPreferences.Editor editor;
    private static Toast toast;
    private static AlertDialog.Builder builder;
    public String[] permissions={
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE};
    private static List<Activity>activityList;
    public synchronized static MyTools getInstance(){
        if (myTools==null){
            myTools=new MyTools();
            activityList=new ArrayList<>();
        }
        return myTools;
    }

    @SuppressLint("CommitPrefEdits")
    private MyTools() {
        preferences= AppClient.getInstance().getSharedPreferences("simple", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }
    public void setData(String key, Object object) {
        String type = object.getClass().getSimpleName();
        switch (type) {
            case "String":
                editor.putString(key, object.toString());
                break;
            case "Integer":
                editor.putInt(key, (Integer) object);
                break;
            case "Boolean":
                editor.putBoolean(key, (Boolean) object);
                break;
            case "Float":
                editor.putFloat(key, (Float) object);
                break;
            case "Long":
                editor.putLong(key, (Long) object);
                break;
        }

        editor.apply();
    }
    public Object getData(String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        switch (type) {
            case "String":
                return preferences.getString(key, (String) defaultObject);
            case "Integer":
                return preferences.getInt(key, (Integer) defaultObject);
            case "Boolean":
                return preferences.getBoolean(key, (Boolean) defaultObject);
            case "Float":
                return preferences.getFloat(key, (Float) defaultObject);
            case "Long":
                return preferences.getLong(key, (Long) defaultObject);
        }

        return null;
    }
    @SuppressLint("ShowToast")
    public  void showToast(String msg, Context context) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }
    public void showDialog(String msg, Context context,DialogInterface.OnClickListener listener) {
        if (builder==null){
            builder=new AlertDialog.Builder(context);
        }
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", null);
        if (listener!=null){
            builder.setNegativeButton("取消", listener);
        }
        builder.show();
    }
    public String getNowTime(String type){
        SimpleDateFormat format=new SimpleDateFormat(type, Locale.CHINA);
        Date date=new Date(System.currentTimeMillis());
        return format.format(date);
    }
    /**
     * 检查是否有对应权限
     *
     * @param activity 上下文
     * @param permission 要检查的权限
     * @return  结果标识
     */
    public boolean verifyPermissions(Activity activity, String permission) {
        int index = ActivityCompat.checkSelfPermission(activity,permission);
        //是否已经同意权限
        return index== PackageManager.PERMISSION_GRANTED;
    }
    public int[]imageResource=new int[]{R.drawable.main_icon,
            R.drawable.service_icon,
            R.drawable.news_icon,
            //R.drawable.party_icon,
            R.drawable.user_icon};
    public int[]stringResource=new int[]{R.string.main,
            R.string.all_service,
            R.string.news,
           // R.string.party,
            R.string.personal};
    public Intent judgmentIntent(String type,Context context){
        Intent intent=new Intent();
        switch (type){
            case "地铁查询":
                intent=new Intent(context, SubwayActivity.class);
                break;
            case "活动":
                intent=new Intent(context, MovableActivity.class);
                break;
            case "违章查询":
                intent=new Intent(context, ViolationActivity.class);
                break;
            case "门诊预约":
                intent=new Intent(context, ShowHospitalActivity.class);
                break;
            default:
                break;
        }
        return intent;
    }
    public void addActivity(Activity activity){
        activityList.add(activity);
    }
    public void clearActivity(){
        for (int i = 0; i < activityList.size(); i++) {
            Activity activity = activityList.get(i);
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }

}
