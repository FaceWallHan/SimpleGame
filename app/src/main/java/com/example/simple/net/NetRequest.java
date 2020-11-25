package com.example.simple.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.example.simple.utils.DataKeys;
import com.example.simple.utils.MyTools;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NetRequest extends Thread {
    private ProgressDialog dialog;
    private boolean isLoop=false;
    private int time=0;
    private Map<String,Object> objectMap;
    private NetCall netCall;
    private String url;

    public NetRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public NetRequest() {
        objectMap=new HashMap<>();
    }
    public NetRequest showDialog(Context context) {
        dialog=new ProgressDialog(context);
        dialog.setMessage("loading...");
        dialog.setTitle("提示");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return this;
    }

    public NetRequest setLoop(boolean loop,int time) {
        isLoop = loop;
        this.time = time;
        return this;
    }
    public NetRequest addValue(String key,Object value){
        objectMap.put(key,value);
        return this;
    }

    public NetRequest setNetCall(NetCall netCall) {
        this.netCall = netCall;
        return this;
    }
    @Override
    public void run() {
        do {
//            String ip= (String) MyTools.getInstance().getData(DataKeys.IP_ADDRESS,"192.168.43.110");
//            String port= (String) MyTools.getInstance().getData(DataKeys.PORT,"8080");
//            Retrofit retrofit=new Retrofit.Builder()
//                    .baseUrl("http://"+ip +":"+port+"/mobileA/")//只有这里地址加上“/”
//                    //.addConverterFactory(GsonConverterFactory.create())
//                    .build();
//            NetClient client=retrofit.create(NetClient.class);
            Call<ResponseBody> call=RetrofitClient.getInstance().getApi().getResource(url);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    //为什么要关闭防火墙？？？
                     //result = null;
                    try {
                        String result = response.body().string();
                        JSONObject jsonObject=new JSONObject(result);
                        netCall.onSuccess(jsonObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (dialog!=null&&dialog.isShowing()){
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    netCall.onFailure(t);
                    if (dialog!=null&&dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            });
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (isLoop);
    }
}
