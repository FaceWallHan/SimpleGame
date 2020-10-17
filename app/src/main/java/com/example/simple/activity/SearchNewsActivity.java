package com.example.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simple.R;
import com.example.simple.adapter.SearchNewsAdapter;
import com.example.simple.bean.BeanNews;
import com.example.simple.net.NetCall;
import com.example.simple.net.NetRequest;
import com.example.simple.net.VolleyTo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsActivity extends AppCompatActivity {
    private ListView search_list;
    private String value;
    private List<BeanNews>list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_news_layout);
        inView();
        decideIntent();
        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(SearchNewsActivity.this,SearchWebActivity.class);
                BeanNews beanNews=list.get(i);
                intent.putExtra("searchWeb",beanNews.getUrl());
                startActivity(intent);
            }
        });

    }
    private void decideIntent(){
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("bundle");
        if (bundle != null) {
            String imageNum = bundle.getString("imageNum");
            startNewsSimpleRequest(imageNum);
        }else{
            if (intent.getStringExtra("search")==null){
                value="";
            }else {
                value=intent.getStringExtra("search");
            }
            startNewsListRequest();
        }
    }
    private void inView(){
        search_list=findViewById(R.id.search_list);
        list=new ArrayList<>();
        search_list.setEmptyView(findViewById(R.id.search_tv));
    }
    private BeanNews parseJson(JSONObject object){
        BeanNews beanNews=new BeanNews();
        try {
            beanNews.setNewsId(object.getInt("newsid"));
            beanNews.setContent(object.getString("abstract"));
            beanNews.setNewsType(object.getString("newsType"));
            beanNews.setPicture(object.getString("picture"));
            beanNews.setTitle(object.getString("title"));
            beanNews.setUrl(object.getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return beanNews;
    }
    private void startNewsSimpleRequest(String numImage){
        VolleyTo request=new VolleyTo();
        request.setUrl("getNewsByImages")
                .setJsonObject("num",numImage)
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                list.add(parseJson(jsonObject));
                                SearchNewsAdapter adapter=new SearchNewsAdapter(SearchNewsActivity.this,list);
                                search_list.setAdapter(adapter);
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
    private void startNewsListRequest(){
        VolleyTo request=new VolleyTo();
        request.setUrl("getNewsByKeys")
                .setJsonObject("keys",value)
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object=array.getJSONObject(i);
                                    BeanNews beanNews=new BeanNews();
                                    beanNews.setNewsId(object.getInt("newsid"));
                                    beanNews.setContent(object.getString("abstract"));
                                    beanNews.setNewsType(object.getString("newsType"));
                                    beanNews.setPicture(object.getString("picture"));
                                    beanNews.setTitle(object.getString("title"));
                                    beanNews.setUrl(object.getString("url"));
                                    list.add(beanNews);
                                }
                                SearchNewsAdapter adapter=new SearchNewsAdapter(SearchNewsActivity.this,list);
                                search_list.setAdapter(adapter);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
