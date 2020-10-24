package com.example.simple.activity;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.simple.R;
import com.example.simple.fragment.ApplyServiceFragment;
import com.example.simple.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * 关于BottomNavigationView的问题：造船不如买船,买船不如租船，既然可以new->activity->Bottom Navigation Activity生成例子，
 * 那就可以根据其微改，有机会一定！
 * 还是先用BottomNavigationBar吧
 * */

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    private BottomNavigationBar navigationView;
    private HomeFragment homeFragment;
    private List<Fragment> fragmentList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        inView();
        addItem();
        homeFragment = new HomeFragment();
        homeFragment.setChangeFragment(new HomeFragment.ChangeFragment() {
            @Override
            public void change() {
                //通信方式
                replaceFragment(new ApplyServiceFragment());
                navigationView.selectTab(1);
            }
        });
        replaceFragment(homeFragment);
    }
    private void inView(){
        navigationView=findViewById(R.id.nav_bar);
        navigationView.setMode(BottomNavigationBar.MODE_SHIFTING);
        navigationView.setTabSelectedListener(this);
        navigationView.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        navigationView.addItem(addNavigationItem(R.drawable.main_icon,R.string.main))
                .addItem(addNavigationItem(R.drawable.service_icon,R.string.all_service))
                .addItem(addNavigationItem(R.drawable.party_icon,R.string.party))
                .addItem(addNavigationItem(R.drawable.news_icon,R.string.news))
                .addItem(addNavigationItem(R.drawable.user_icon,R.string.personal))
                .setFirstSelectedPosition(0)
                .initialise();
        navigationView.setBackgroundColor(Color.BLUE);
//        navigationView.selectTab();
        fragmentList=new ArrayList<>();
    }
    private void addItem(){
        fragmentList.add(new HomeFragment());
        fragmentList.add(new ApplyServiceFragment());
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commitNow();
        //似乎防止fragment重复创建都是 从onCreate(@Nullable Bundle savedInstanceState)下手？？？
    }

    @Override
    public void onTabSelected(int position) {
        replaceFragment(fragmentList.get(position));
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
    private BottomNavigationItem addNavigationItem(int iconResource,int strValueId){
        Resources resources = getResources();
        String string = resources.getString(strValueId);
        BottomNavigationItem item=new BottomNavigationItem(iconResource,string);
        item.setActiveColor(Color.BLUE);
        return item;
    }
}
