package com.example.simple.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.bean.BeanNews;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NewsFragment  extends Fragment {
    public static final String TYPE = "Type";
    private View view;
    private List<BeanNews> newsList;
    private LinearLayout horizontal_layout;
    private List<String> typeList;
    private int index=0;
    private String type;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            index++;
            if (index==newsList.size()){
                for (int i = 0; i < typeList.size(); i++) {
                    String type=typeList.get(i);
                    horizontal_layout.addView(typeTextView(type,NewsChildFragment.newInstance(type)));
                }
                replaceFragment(NewsChildFragment.newInstance(typeList.get(0)));
//                Bundle bundle = getArguments();
//                if (bundle != null){
//                    type = bundle.getString(TYPE);
//                    assert type != null;
//                    //此处断言有无实际意义？？？
//                    if (type.equals("")){
//                        replaceFragment(NewsChildFragment.newInstance(typeList.get(0)));
//                    }else {
//                        replaceFragment(NewsChildFragment.newInstance(type));
//                    }
//                }

            }
            return false;
        }
    });
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inView();
        startAllNewsRequest();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.news_fragment_layout,container,false);
        return view;
    }
    private void inView(){
        horizontal_layout=view.findViewById(R.id.horizontal_layout);
        newsList= AppClient.getInstance().getNewsList();
        typeList=new ArrayList<>();

    }
    private TextView  typeTextView(String type,Fragment fragment){
        TextView text=new TextView(view.getContext());
        text.setText(type);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin=20;
        params.rightMargin=20;
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(fragment);
            }
        });
        text.setLayoutParams(params);
        return text;
    }
    private void startOtherRequest(BeanNews beanNews){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getNewsInfoById")
                .setJsonObject("newsid",beanNews.getNewsId())
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                JSONObject object=array.getJSONObject(0);
                                beanNews.setPraiseCount(object.getString("praiseCount"));
                                beanNews.setPublicTime(object.getString("publicTime"));
                                typeList.add(beanNews.getNewsType());
                                //java8新特性stream进行List去重
                                typeList=typeList.stream().distinct().collect(Collectors.toList());
                                //知道解析完毕以便addView???
                                handler.sendEmptyMessage(0);
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
    private void startAllNewsRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getNEWsList")
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object=array.getJSONObject(i);
                                    BeanNews beanNews=new BeanNews();
                                    beanNews.setUrl(object.getString("url"));
                                    beanNews.setNewsId(object.getInt("newsid"));
                                    beanNews.setNewsType(object.getString("newsType"));
                                    beanNews.setPicture(object.getString("picture"));
                                    beanNews.setContent(object.getString("abstract"));
                                    beanNews.setTitle(object.getString("title"));
                                    newsList.add(beanNews);
                                }
                                for (int i = 0; i < newsList.size(); i++) {
                                    BeanNews news = newsList.get(i);
                                    startOtherRequest(news);
                                }
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
    private void replaceFragment(Fragment fragment){
        FragmentManager manager= getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.news_frame,fragment);
        transaction.commit();
    }
}
