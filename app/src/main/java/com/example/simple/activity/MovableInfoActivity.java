package com.example.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.simple.R;
import com.example.simple.adapter.CommentAdapter;
import com.example.simple.adapter.MovableAdapter;
import com.example.simple.bean.BeanAction;
import com.example.simple.bean.BeanComment;
import com.example.simple.bean.BeanNews;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;
import com.example.simple.utils.AllItemListView;
import com.example.simple.utils.MyTools;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovableInfoActivity extends BaseActivity implements View.OnClickListener , AdapterView.OnItemClickListener {
    private BeanAction beanAction;
    private ImageView action_picture;
    private AllItemListView action_comment;
    private EditText action_et;
    private TextView action_content,action_count;
    private List<BeanComment>commentList;
    private List<BeanAction>actionList;
    private MovableAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movable_info_layout);
        int id=getIntent().getIntExtra("BeanAction",1);
        setTitleText("活动详情");
        startActionByIdRequest(id);
        inView();
        startRecommendInfoRequest(id);
    }
    private void inView(){
        action_picture=findViewById(R.id.action_picture);
        AllItemListView action_recommend = findViewById(R.id.action_recommend);
        action_comment=findViewById(R.id.action_comment);
        Button submit_action = findViewById(R.id.submit_action);
        action_et=findViewById(R.id.action_et);
        action_content=findViewById(R.id.action_content);
        action_count=findViewById(R.id.action_count);
        actionList=new ArrayList<>();
        adapter=new MovableAdapter(MovableInfoActivity.this,actionList);
        action_recommend.setAdapter(adapter);
        commentList=new ArrayList<>();
        submit_action.setOnClickListener(this);
        action_recommend.setOnItemClickListener(this);
    }
    private void parseData(){
        setTitleText(beanAction.getName());
        Glide.with(this).load(beanAction.getImage()).into(action_picture);
        String content="时间："+beanAction.getTime()
                        +"\n参与人数："+beanAction.getCount()
                        +"\n点赞数："+beanAction.getPraiseCount()
                        +"\n描述："+beanAction.getDescription()
                        +"\n推荐："+beanAction.getRecommend();
        action_content.setText(content);
    }
    private void startActionByIdRequest(int id){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getActionById")
                .setJsonObject("id",id)
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                JSONObject object=array.getJSONObject(0);
                                beanAction=new Gson().fromJson(object.toString(),BeanAction.class);
                                parseData();
                                startCommentRequest();
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
        volleyTo.setUrl("getActionCommitById")
                .setJsonObject("id",beanAction.getId())
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    BeanComment comment = new BeanComment();
                                    comment.setCommit(object.getString("commitContent"));
                                    comment.setCommitTime(object.getString("commitTime"));
                                    comment.setReviewer(object.getString("username"));
                                    commentList.add(comment);
                                }
                                String count=commentList.size()+"条评论";
                                action_count.setText(count);
                                CommentAdapter adapter=new CommentAdapter(MovableInfoActivity.this,commentList);
                                action_comment.setAdapter(adapter);

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
    private void startRecommendInfoRequest(int id){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getRecommandAction")
                .setJsonObject("newsid",id)
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                for (int i = 0; i < array.length(); i++) {
                                    if (actionList.size()>3){
                                        break;
                                    }
                                    JSONObject object=array.getJSONObject(i);
                                    BeanAction action=new BeanAction();
                                    action.setCount(object.getInt("count"));
                                    action.setId(object.getInt("id"));
                                    action.setDescription(object.getString("description"));
                                    action.setImage(object.getString("image"));
                                    action.setName(object.getString("name"));
                                    action.setPraiseCount(object.getInt("praiseCount"));
                                    action.setRecommend(object.getInt("recommand"));
                                    action.setShowImage(object.getInt("showImage"));
                                    action.setTime(object.getString("time"));
                                    action.setTypeId(object.getString("typeid"));
                                    actionList.add(action);
                                }
                                adapter.notifyDataSetChanged();
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
    private void startSubmitRequest(String content){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("publicActionCommit")
                .setJsonObject("userid","1")
                .setJsonObject("id",beanAction.getId())
                .setJsonObject("commitContent",content)
                .setJsonObject("commitTime", MyTools.getInstance().getNowTime("yyyy.MM.dd HH:mm:ss"))
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                startCommentRequest();
                                action_et.setText("");
                                MyTools.getInstance().showToast("评论成功",MovableInfoActivity.this);
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
        if (view.getId()==R.id.submit_action){
            String content=action_et.getText().toString().trim();
            if (!content.equals("")){
                //RxJava防止多次按？
                startSubmitRequest(content);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        BeanAction action=actionList.get(i);
        Intent intent=new Intent(view.getContext(), MovableInfoActivity.class);
        intent.putExtra("BeanAction",action.getId());
        startActivity(intent);
        finish();
    }
}
