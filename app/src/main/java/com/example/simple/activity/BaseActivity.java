package com.example.simple.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.simple.R;

public class BaseActivity  extends AppCompatActivity {
    public void setTitle(String title){
        ImageView title_back = findViewById(R.id.title_back);
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView title_tv=findViewById(R.id.title_tv);
        title_tv.setText(title);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
