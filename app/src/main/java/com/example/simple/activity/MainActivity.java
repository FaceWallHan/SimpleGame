package com.example.simple.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.simple.R;
import com.example.simple.fragment.ApplyServiceFragment;
import com.example.simple.fragment.HomeFragment;

import org.litepal.LitePal;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setChangeFragment(new HomeFragment.ChangeFragment() {
            @Override
            public void change() {
                replaceFragment(new ApplyServiceFragment());
            }
        });
        replaceFragment(homeFragment);
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }
}
