package com.example.simple.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simple.R;

public class SearchWebActivity extends AppCompatActivity {
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_web_layout);
        String webUrl=getIntent().getStringExtra("searchWeb");
        if (webUrl==null){
            webUrl="https://www.baidu.com/";
        }
        WebView search_web=findViewById(R.id.search_web);
        search_web.loadUrl(webUrl);
        search_web.getSettings().setJavaScriptEnabled(true);
    }
}
