package com.example.simple.activity;

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

import com.example.simple.R;
import com.example.simple.fragment.ApplyServiceFragment;
import com.example.simple.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private HomeFragment homeFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        navigationView=findViewById(R.id.navigationView);
        homeFragment = new HomeFragment();
        homeFragment.setChangeFragment(new HomeFragment.ChangeFragment() {
            @Override
            public void change() {
                replaceFragment(new ApplyServiceFragment());
            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        replaceFragment(homeFragment);
                        break;
                    case R.id.action_service:
                        replaceFragment(new ApplyServiceFragment());
                        break;
                }
                return false;
            }
        });
        //多次切换fragment问题
        //点击底部item 的风格问题

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }
}
