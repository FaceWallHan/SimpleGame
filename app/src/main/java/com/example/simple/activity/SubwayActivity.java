package com.example.simple.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simple.R;
import com.example.simple.utils.MyTools;

public class SubwayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_layout);
        MyTools.getInstance().showToast(this.getClass().getName(),this);
    }
}
