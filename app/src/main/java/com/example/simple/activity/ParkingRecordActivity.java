package com.example.simple.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.adapter.ParkInfoAdapter;
import com.example.simple.adapter.ParkRecordAdapter;
import com.example.simple.bean.BeanParkRecord;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;
import com.example.simple.utils.AllItemListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ParkingRecordActivity extends BaseActivity implements View.OnClickListener {
    private TextView record_stop_time,record_stop_date,record_start_date,record_start_time;
    private AllItemListView record_lv;
    private Button record_more;
    private List<BeanParkRecord>recordList,second;
    private ParkRecordAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_record_layout);
        setTitleText("停车记录");
        inView();
        startHistoryByIdRequest();
    }
    private DatePickerDialog getCalendarDateDialog(DatePickerDialog.OnDateSetListener onDateSetListener){
        Calendar calendar = Calendar.getInstance();
        return new DatePickerDialog(this, onDateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }
    private TimePickerDialog getCalendarTimeDialog(TimePickerDialog.OnTimeSetListener onTimeSetListener){
        Calendar calendar = Calendar.getInstance();
        return new TimePickerDialog(this,onTimeSetListener,calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),true);
    }
    private void inView(){
        record_lv=findViewById(R.id.record_lv);
        record_stop_time=findViewById(R.id.record_stop_time);
        record_stop_time.setOnClickListener(this);
        record_stop_date=findViewById(R.id.record_stop_date);
        record_stop_date.setOnClickListener(this);
        record_start_date=findViewById(R.id.record_start_date);
        record_start_date.setOnClickListener(this);
        record_start_time=findViewById(R.id.record_start_time);
        record_start_time.setOnClickListener(this);
        Button inquire_record = findViewById(R.id.inquire_record);
        inquire_record.setOnClickListener(this);
        record_more=findViewById(R.id.record_more);
        record_more.setOnClickListener(this);
        recordList=new ArrayList<>();
    }
    private void startHistoryByIdRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getParkingHistoryById")
                .setJsonObject("parkingid", AppClient.parkingid)
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                TypeToken<List<BeanParkRecord>>token=new TypeToken<List<BeanParkRecord>>(){};
                                String json=jsonObject.getString("ROWS_DETAIL");
                                second=new Gson().fromJson(json,token.getType());
                                recordList.addAll(second);
                                if (recordList.size()>ViolationInfoActivity.number){
                                    recordList=recordList.subList(0,ViolationInfoActivity.number+1);
                                }else {
                                    record_more.setVisibility(View.GONE);
                                }
                                Collections.sort(recordList,new BeanParkRecord.inTimeAsc());
                                String []inTime=recordList.get(0).getInTime().split(" ");
                                record_start_date.setText(inTime[0]+" ");
                                record_start_time.setText(inTime[1]);
                                //对进入时间升序
                                Collections.sort(recordList,new BeanParkRecord.outTimeDesc());
                                String []outTime=recordList.get(0).getInTime().split(" ");
                                record_stop_date.setText(outTime[0]+" ");
                                record_stop_time.setText(outTime[1]);
                                //对出去时间降序
                                adapter=new ParkRecordAdapter(ParkingRecordActivity.this,recordList);
                                record_lv.setAdapter(adapter);
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
        switch (view.getId()){
            case R.id.inquire_record:
                String inTime=record_start_date.getText().toString()+record_start_time.getText().toString();
                String outTime=record_stop_date.getText().toString()+record_stop_time.getText().toString();
                //接口有点瑕疵
                break;
            case R.id.record_start_date:
                getCalendarDateDialog(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        String date=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                        record_start_date.setText(date);
                    }
                }).show();
                break;
            case R.id.record_stop_date:
                getCalendarDateDialog(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        String date=year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
                        record_stop_date.setText(date);
                    }
                }).show();
                break;
            case R.id.record_start_time:
                getCalendarTimeDialog(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int month) {
                        String time=" "+hour+":"+month+":00";
                        record_start_time.setText(time);
                    }
                }).show();
                break;
            case R.id.record_stop_time:
                getCalendarTimeDialog(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int month) {
                        String time=" "+hour+":"+month+":00";
                        record_stop_time.setText(time);
                    }
                }).show();
                break;
            case R.id.record_more:
                adapter=new ParkRecordAdapter(ParkingRecordActivity.this,second);
                record_lv.setAdapter(adapter);
                record_more.setVisibility(View.GONE);
                break;
        }
    }
}
