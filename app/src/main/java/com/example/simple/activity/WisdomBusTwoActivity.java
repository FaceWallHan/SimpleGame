package com.example.simple.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.bean.BeanBus;
import com.example.simple.utils.MyTools;

import java.util.Calendar;

public class WisdomBusTwoActivity extends BaseActivity {
    private TextView select_day;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wisdom_bus_two_layout);
        setTitleText("智慧巴士");
        inView();
        setBusOnClick("下一步",new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WisdomBusTwoActivity.this, WisdomBusThreeActivity.class);
                intent.putExtra("WisdomBusThreeActivity",select_day.getText().toString().trim());
                startActivity(intent);
                finish();
            }
        });
    }
    private void inView(){
        DatePicker date_picker=findViewById(R.id.date_picker);
        select_day=findViewById(R.id.select_day);
        Calendar calendar=Calendar.getInstance();
        select_day.setText(MyTools.getInstance().getNowTime("yyyy-MM-dd"));
        date_picker.init(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker,  int year, int monthOfYear, int dayOfMonth) {
                String time=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                select_day.setText(time);
            }
        });
    }
}
