package com.example.simple.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.simple.R;
import com.example.simple.adapter.ViewPagerAdapter;
import com.example.simple.fragment.GuideFragment;
import com.example.simple.net.NetCall;
import com.example.simple.net.NetRequest;
import com.example.simple.net.VolleyTo;
import com.example.simple.utils.DataKeys;
import com.example.simple.utils.MyTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {
    private ViewPager image_pager;
    private LinearLayout point_layout;
    private List<Fragment> list;
    private int time=2000;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            int currentItem = image_pager.getCurrentItem();
            if (currentItem < list.size() - 1) {
                currentItem++;
            } else {
                currentItem = 0;
            }
            image_pager.setCurrentItem(currentItem);// 切换到下一个页面
            // 继续延时3秒发消息,形成循环，可以handleMessage方法里发送消息的
            handler.sendEmptyMessageDelayed(0, time);
            //疯狂地自动
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((boolean)MyTools.getInstance().getData(DataKeys.isFirst,true)){
            setContentView(R.layout.guide_layout);
            inView();
            startRequest();

        }else {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }
    private void addView(){
        point_layout.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            ImageView view=new ImageView(this);
            if (i==0){
                view.setBackgroundResource(R.drawable.point_black);
            }else {
                view.setBackgroundResource(R.drawable.point_white);
            }
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(20,20);
            params.leftMargin=5;
            params.rightMargin=5;
            point_layout.addView(view,params);
        }
    }
    private void setPager(){
        image_pager.setCurrentItem(0);
        image_pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),list));
        image_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < point_layout.getChildCount(); i++) {
                    ImageView view= (ImageView) point_layout.getChildAt(i);
                    if (i==position){
                        view.setBackgroundResource(R.drawable.point_black);
                    }else {
                        view.setBackgroundResource(R.drawable.point_white);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void startRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getActionImages")
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object=array.getJSONObject(i);
                                    String imageUrl=object.getString("image");
                                    if (i==array.length()-1){
                                        list.add(new GuideFragment(imageUrl,true));
                                    }else {
                                        list.add(new GuideFragment(imageUrl,false));
                                    }
                                }
                                setPager();
                                addView();
                                Log.d("11111111111111", "onCreate: "+point_layout.getChildCount());
                                //handler.sendEmptyMessageDelayed(0, time);
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
    private void inView(){
        list=new ArrayList<>();
        image_pager=findViewById(R.id.image_pager);
        point_layout=findViewById(R.id.point_layout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }
}