package com.example.simple.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.simple.R;
import com.example.simple.dialog.ImageDialog;
import com.example.simple.dialog.ModifyInfoDialog;

import static org.litepal.LitePalApplication.getContext;

public class PersonInfoActivity extends AppCompatActivity implements View.OnClickListener,
                                                    ImageDialog.OnCenterItemClickListener,
                                                    ModifyInfoDialog.OnSubmitListener {
    private ImageView title_back,info_head;
    private TextView nickname,phone,identity;
    private Spinner sex;
    private ImageDialog dialog;
    private ModifyInfoDialog infoDialog;
    private String[] userInfo;
    public static final String NICK_NAME="NICK_NAME";
    public static final String PHONE="PHONE";
    public static final String IDENTITY="IDENTITY";
    private String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info_layout);
        Intent intent=getIntent();
        userInfo=intent.getStringArrayExtra("userInfo");
        inView();
        assert userInfo != null;
        setData(userInfo);

    }
    private void setData(String[] userInfo){
        String id=userInfo[0];
        String convert=id.substring(0,2)+"************"+id.substring(id.length()-4);
        identity.setText(convert);
        nickname.setText(userInfo[1]);
        Glide.with(this).load(userInfo[2]).into(info_head);
        phone.setText(userInfo[3]);
        if (userInfo[4].equals("男")){
            sex.setSelection(0);
        }else {
            sex.setSelection(1);
        }
        //可修改内容为：头像、昵称、性别、联系电话
    }
    private void inView(){
        title_back=findViewById(R.id.title_back);
        info_head=findViewById(R.id.info_head);
        nickname=findViewById(R.id.nickname);
        phone=findViewById(R.id.phone);
        sex=findViewById(R.id.sex);
        identity=findViewById(R.id.identity);
        dialog=new ImageDialog(this,R.layout.image_dialog_layout,new int[]{R.id.photo_choose, R.id.photograph});
        title_back.setOnClickListener(this);
        info_head.setOnClickListener(this);
        nickname.setOnClickListener(this);
        identity.setOnClickListener(this);
        phone.setOnClickListener(this);
        dialog.setOnCenterItemClickListener(this);
        infoDialog=new ModifyInfoDialog();
        infoDialog.setListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;
            case R.id.info_head:
                dialog.show();
                break;
            case R.id.nickname:
                infoDialog.show(getSupportFragmentManager(),NICK_NAME+","+nickname.getText().toString());
                break;
            case R.id.phone:
                infoDialog.show(getSupportFragmentManager(),PHONE+","+phone.getText().toString());
                break;
            case R.id.identity:
                infoDialog.show(getSupportFragmentManager(),IDENTITY+","+userInfo[0]);
                break;
        }
    }

    @Override
    public void OnCenterItemClick(ImageDialog dialog, View view) {
        switch (view.getId()){
            case R.id.photo_choose:
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(getContext(), permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DENIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 如果没有授予该权限，就去提示用户请求
                    //showDialogTipUserRequestPermission();
                }else {
                    //openAlbum();
                }
                break;
            case R.id.photograph:
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onSubmit(String type, String content) {
        switch (type){
            case PHONE:
                phone.setText(content);
                break;
            case IDENTITY:
                identity.setText(content);
                break;
            case NICK_NAME:
                String convert=content.substring(0,2)+"************"+content.substring(content.length()-4);
                identity.setText(convert);
                break;
        }
    }
}
