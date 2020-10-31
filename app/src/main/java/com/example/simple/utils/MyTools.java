package com.example.simple.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.simple.AppClient;

public class MyTools {
    private  static SharedPreferences preferences;
    private  static MyTools myTools;
    private static  SharedPreferences.Editor editor;
    private static Toast toast;
    private static AlertDialog.Builder builder;
    public synchronized static MyTools getInstance(){
        if (myTools==null){
            myTools=new MyTools();
        }
        return myTools;
    }

    @SuppressLint("CommitPrefEdits")
    private MyTools() {
        preferences= AppClient.getInstance().getSharedPreferences("simple", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }
    public   void setData(String key, Object object) {
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
    public   Object getData(String key, Object defaultObject) {
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
    public void showDialog(String msg, Context context) {
        if (builder==null){
            builder=new AlertDialog.Builder(context);
        }
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setPositiveButton("确定", null);
        builder.show();
    }
}
