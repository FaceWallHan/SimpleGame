package com.example.simple.net;

import retrofit2.Retrofit;

public class RetrofitClient {
    //https://api.uomg.com/api/
    //http://192.168.43.110:8080/mobileA/
    private static final String BASE_URL = "https://api.uomg.com/api/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
              //  .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance() {

        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }


        return mInstance;

    }

    public NetClient getApi(){
        return retrofit.create(NetClient.class);
    }
}
