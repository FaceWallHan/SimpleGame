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
        startNewsListRequest();
        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("111111111111111", "onItemClick: "+i);
                Intent intent=new Intent(SearchNewsActivity.this,SearchWebActivity.class);
                BeanNews beanNews=list.get(i);
                intent.putExtra("searchWeb",beanNews.getUrl());
                startActivity(intent);
            }
        });
    }
    private void inView(){
        TextView search_tv=findViewById(R.id.search_tv);
        search_list=findViewById(R.id.search_list);
        if (getIntent().getStringExtra("search")==null){
            value="";
        }
        String news=value+"新闻";
        search_tv.setText(news);
        list=new ArrayList<>();
    }
    private void startNewsListRequest(){
        NetRequest request=new NetRequest();
        request.setUrl("getNEWsList")
                .addValue("keys",value)
                .setNetCall(new NetCall() {
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
                                Log.d("1111111111111111", "onSuccess: "+list.size());
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
}
