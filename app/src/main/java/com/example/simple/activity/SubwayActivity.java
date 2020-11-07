package com.example.simple.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.simple.R;
import com.example.simple.utils.MyTools;

public class SubwayActivity extends AppCompatActivity {
    private MapView map_view;
    private BaiduMap baiduMap;
    private LocationClient client;
    // 是否是第一次定位
    private boolean isFirstLocate = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_layout);
        inView();
        if (!MyTools.getInstance().verifyPermissions(SubwayActivity.this,MyTools.getInstance().permissions[3]) ){
            ActivityCompat.requestPermissions(SubwayActivity.this,MyTools.getInstance().permissions,10);
        }else {
            startLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocation();
            } else {
                Toast.makeText(this, "拒绝权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void inView(){
        map_view=findViewById(R.id.map_view);
        baiduMap = map_view.getMap();
        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        baiduMap.setMyLocationEnabled(true);

    }
    private void startLocation(){
        client = new LocationClient(SubwayActivity.this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("BD09ll");//定位SDK默认输出GCJ02坐标，地图SDK默认输出BD09ll坐标
        option.setScanSpan(1000);//设置扫描范围?
        client.setLocOption(option);
        //注册LocationListener监听器
        client.registerLocationListener(new MyLocationListener());
        //开启地图定位图层
        client.start();
    }
    @Override
    protected void onResume() {
        //表示activity已经可见，onStart的时候activity还在后台，onResume才显示到前台
        map_view.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //表示activity正在停止
        map_view.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        //表示activity即将被销毁
        client.stop();
        baiduMap.setMyLocationEnabled(false);
        map_view.onDestroy();
        map_view=null;
        super.onDestroy();
    }
    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || map_view == null){
                return;
            }
            LatLng lng=new LatLng(location.getLatitude(),location.getLongitude());
            if (isFirstLocate){
                isFirstLocate=false;
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(lng));
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
        }
    }
}
