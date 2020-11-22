package com.example.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;
import com.example.simple.utils.MyTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WisdomBusThreeActivity extends BaseActivity {
    private String select_day;
    private EditText passenger_name_three,phone_three;
    private TextView site_three;
    private Spinner up_location,down_location;
    private List<String>siteList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wisdom_bus_three_layout);
        setTitleText("智慧巴士");
        inView();
        startDataRequest();
        setBusOnClick("下一步",new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phone_three.getText().toString().trim();
                String name = passenger_name_three.getText().toString().trim();
                String up=up_location.getSelectedItem().toString();
                String down=down_location.getSelectedItem().toString();
                if (up.equals(down)){
                    MyTools.getInstance().showDialog("上车地点和下车地点不能相同",WisdomBusThreeActivity.this,null,null);
                }else if (phone.equals("")||phone.length()!=11){
                    MyTools.getInstance().showDialog("请输入正确的手机号码",WisdomBusThreeActivity.this,null,null);
                }else if (name.equals("")){
                    MyTools.getInstance().showDialog("请输入姓名",WisdomBusThreeActivity.this,null,null);
                }else {
                    Intent intent=new Intent(WisdomBusThreeActivity.this,WisdomBusFourActivity.class);
                    ArrayList<String>value=new ArrayList<>();
                    value.add(name);
                    value.add(phone);
                    value.add(up);
                    value.add(down);
                    value.add(select_day);
                    intent.putStringArrayListExtra("ArrayList",value);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    private void startDataRequest(){
        String site=AppClient.beanBus.getStartSite()+"\n|\n"+AppClient.beanBus.getStartSite();
        site_three.setText(site);
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("busStationById")
                .setJsonObject("busid", AppClient.beanBus.getBusid())
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object=array.getJSONObject(i);
                                    siteList.add(object.getString("siteName"));
                                }
                                ArrayAdapter<String> adapter=new ArrayAdapter<String>(WisdomBusThreeActivity.this,android.R.layout.simple_list_item_1,siteList);
                                up_location.setAdapter(adapter);
                                down_location.setAdapter(adapter);
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
        select_day=getIntent().getStringExtra("WisdomBusThreeActivity");
        down_location=findViewById(R.id.down_location);
        up_location=findViewById(R.id.up_location);
        site_three=findViewById(R.id.site_three);
        passenger_name_three=findViewById(R.id.passenger_name_three);
        phone_three=findViewById(R.id.phone_three);
        siteList=new ArrayList<>();
    }
}
