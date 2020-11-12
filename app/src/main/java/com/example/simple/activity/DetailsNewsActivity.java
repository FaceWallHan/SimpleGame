package com.example.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.simple.R;
import com.example.simple.adapter.CommentAdapter;
import com.example.simple.adapter.SearchNewsAdapter;
import com.example.simple.bean.BeanComment;
import com.example.simple.bean.BeanNews;
import com.example.simple.bean.BeanServiceType;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;
import com.example.simple.utils.AllItemListView;
import com.example.simple.utils.MyTools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailsNewsActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private BeanNews currentBean;
    private ImageView news_picture;
    private TextView news_content,comment_count;
    private List<BeanComment> commentList;
    private AllItemListView comment;
    private EditText comment_et;
    private List<BeanNews> beanNews;
    private SearchNewsAdapter newsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_news_layout);
        inView();
        setViewData();
        startCommentRequest();
        startRecommendIdRequest();
        Log.d("00000000000000000", getClass().getName());
    }
    private void startRecommendInfoRequest(int id){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getNEWSContent")
                .setJsonObject("newsid",id)
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                JSONObject object=array.getJSONObject(0);
                                if (beanNews.size()<3){
                                    BeanNews news=new BeanNews();
                                    news.setUrl(object.getString("url"));
                                    news.setNewsId(object.getInt("newsid"));
                                    news.setNewsType(object.getString("newsType"));
                                    news.setPicture(object.getString("picture"));
                                    news.setContent(object.getString("abstract"));
                                    news.setTitle(object.getString("title"));
                                    beanNews.add(news);
                                    newsAdapter.notifyDataSetChanged();
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
    private void startRecommendIdRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getRecommend")
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                for (int i = 0; i < array.length(); i++) {
                                    int newsId=array.getJSONObject(i).getInt("newsid");
                                    startRecommendInfoRequest(newsId);
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
    private void startCommentRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getCommitById")
                .setJsonObject("id",currentBean.getNewsId())
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                TypeToken<List<BeanComment>> token=new TypeToken<List<BeanComment>>(){};
                                String json=jsonObject.getString("ROWS_DETAIL");
                                commentList=new Gson().fromJson(json,token.getType());
                                String count=commentList.size()+"条评论";
                                comment_count.setText(count);
                                CommentAdapter adapter=new CommentAdapter(DetailsNewsActivity.this,commentList);
                                comment.setAdapter(adapter);
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
    private void setViewData(){
        assert currentBean != null;
        String title=currentBean.getContent();
        if (title.length()>8){
            title=title.substring(0,8);
        }
        setTitle(title);
        Glide.with(this).load(currentBean.getPicture()).into(news_picture);
        news_content.setText(currentBean.getContent());
    }
    private void inView(){
        currentBean= (BeanNews) getIntent().getSerializableExtra("BeanNews");
        assert currentBean != null;
        String title=currentBean.getTitle();
        if (title.length()>5){
            title=title.substring(0,4);
        }
        setTitleText(title);
        news_content=findViewById(R.id.news_content);
        news_picture=findViewById(R.id.news_picture);
        comment = findViewById(R.id.comment);
        comment_count=findViewById(R.id.comment_count);
        Button submit_comment = findViewById(R.id.submit_comment);
        submit_comment.setOnClickListener(this);
        comment_et=findViewById(R.id.comment_et);
        AllItemListView recommend = findViewById(R.id.recommend);
        beanNews=new ArrayList<>();
        newsAdapter=new SearchNewsAdapter(DetailsNewsActivity.this,beanNews);
        recommend.setAdapter(newsAdapter);
        recommend.setOnItemClickListener(this);
    }
    private void startSubmitRequest(String content){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("publicComit")
                .setJsonObject("username","user1")
                .setJsonObject("newsid",currentBean.getNewsId())
                .setJsonObject("commit",content)
                .setJsonObject("commitTime", MyTools.getInstance().getNowTime("yyyy.MM.dd HH:mm:ss"))
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                startCommentRequest();
                                comment_et.setText("");
                                MyTools.getInstance().showToast("评论成功",DetailsNewsActivity.this);
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
    public void onClick(View view) {
        if (view.getId()==R.id.submit_comment){
            String content=comment_et.getText().toString().trim();
            if (!content.equals("")){
                startSubmitRequest(content);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        BeanNews bean = beanNews.get(i);
        Intent intent=new Intent(view.getContext(), DetailsNewsActivity.class);
        intent.putExtra("BeanNews",bean);
        startActivity(intent);
        finish();
    }
}
