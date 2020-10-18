package com.example.simple.net;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.simple.AppClient;

import org.json.JSONException;
import org.json.JSONObject;

public class VolleyTo extends Thread{
    //https://api.uomg.com/api/
    //http://192.168.43.110:8080/mobileA/
    private String Url="http://118.190.26.201:8080/mobileA/";
    private JSONObject jsonObject=new JSONObject();
    private int Time;
    private boolean isLoop;
    private ProgressDialog mDialog;
    private NetCall volleyLo;
    public VolleyTo setUrl(String url) {
        Url += url;
        return this;
    }

    public VolleyTo setJsonObject(String k, Object v) {
        try {
            jsonObject.put(k,v);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public VolleyTo setTime(int time) {
        Time = time;
        return this;
    }

    public VolleyTo setLoop(boolean loop) {
        isLoop = loop;
        return this;
    }

    public VolleyTo setDialog(Context context) {
        mDialog=new ProgressDialog(context);
        mDialog.setTitle("提示");
        mDialog.setMessage("网络请求中。。。");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.show();
        return this;
    }

    public VolleyTo setVolleyLo(NetCall volleyLo) {
        this.volleyLo = volleyLo;
        return this;
    }

    @Override
    public void run() {
        super.run();
        do {
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    volleyLo.onSuccess(jsonObject);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyLo.onFailure(volleyError);
                }
            });
            AppClient.add(jsonObjectRequest);
            try {
                Thread.sleep(Time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (isLoop);
    }
}
