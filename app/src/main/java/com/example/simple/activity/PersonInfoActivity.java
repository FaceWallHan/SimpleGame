package com.example.simple.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.simple.R;
import com.example.simple.dialog.ImageDialog;
import com.example.simple.dialog.ModifyInfoDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.litepal.LitePalApplication.getContext;

public class PersonInfoActivity extends AppCompatActivity implements View.OnClickListener,
                                                    ImageDialog.OnCenterItemClickListener{
    private ImageView info_head;
    private TextView nickname,phone,identity;
    private Spinner sex;
    private ImageDialog dialog;
    private String[] userInfo;
    private static final int CHOOSE_PHOTO=101;
    private static final int TAKE_PHOTO=100;
    private Uri imageUri;
    private String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};;

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
        ImageView title_back = findViewById(R.id.title_back);
        info_head=findViewById(R.id.info_head);
        nickname=findViewById(R.id.nickname);
        phone=findViewById(R.id.phone);
        sex=findViewById(R.id.sex);
        identity=findViewById(R.id.identity);
        TextView modify_info = findViewById(R.id.modify_info);
        dialog=new ImageDialog(this,R.layout.image_dialog_layout,new int[]{R.id.photo_choose, R.id.photograph});
        title_back.setOnClickListener(this);
        info_head.setOnClickListener(this);
        nickname.setOnClickListener(this);
        identity.setOnClickListener(this);
        phone.setOnClickListener(this);
        modify_info.setOnClickListener(this);
        dialog.setOnCenterItemClickListener(this);


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
                ModifyInfoDialog info=new ModifyInfoDialog();
                info.setListener(new ModifyInfoDialog.OnSubmitListener() {
                    @Override
                    public void onSubmit( String content) {
                        nickname.setText(content);
                    }
                });
                info.show(getSupportFragmentManager(),nickname.getText().toString());
                break;
            case R.id.phone:
                ModifyInfoDialog info1=new ModifyInfoDialog();
                info1.setListener(new ModifyInfoDialog.OnSubmitListener() {
                    @Override
                    public void onSubmit( String content) {
                        phone.setText(content);
                    }
                });
                info1.show(getSupportFragmentManager(),phone.getText().toString());
                break;
            case R.id.identity:
                //为什么把ModifyInfoDialog封装为一个方法不可以？！？！？！
                ModifyInfoDialog info2=new ModifyInfoDialog();
                info2.setListener(new ModifyInfoDialog.OnSubmitListener() {
                    @Override
                    public void onSubmit( String content) {
                        identity.setText(content);
                    }
                });
                info2.show(getSupportFragmentManager(),userInfo[0]);
                break;
            case R.id.modify_info:
                Toast.makeText(this, "此时应提交修改", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void showModifyInfoDialog(TextView text){
        ModifyInfoDialog infoDialog=new ModifyInfoDialog();
        infoDialog.setListener(new ModifyInfoDialog.OnSubmitListener() {
            @Override
            public void onSubmit( String content) {
                text.setText(content);
            }
        });
        infoDialog.show(getSupportFragmentManager(),text.getText().toString());
    }
    @Override
    public void OnCenterItemClick(ImageDialog dialog, View view) {
        switch (view.getId()){
            case R.id.photo_choose:
                if (verifyPermissions(PersonInfoActivity.this,permissions[2])==0){
                    ActivityCompat.requestPermissions(PersonInfoActivity.this,permissions,1);
                }else {
                    toPicture();
                }
                break;
            case R.id.photograph:
                toCamera();
                break;
        }
    }

    //跳转相机
    private void toCamera() {
        File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
        //getExternalCacheDir--->手机SD卡的应用关联缓存目录下--->SD卡中专门用于存放当前应用缓存数据的位置
        try {
            if (outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
            //Result of 'File.delete()' is ignored???
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT>=24){
            imageUri= FileProvider.getUriForFile(PersonInfoActivity.this,"com.start.head.activity",outputImage);
        }else {
            //系统版本低于7.0
            imageUri=Uri.fromFile(outputImage);
        }
        //启动相机程序
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }
    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);  //跳转到 ACTION_IMAGE_CAPTURE
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   toPicture();
                }else {
                    Toast.makeText(this, "拒绝权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    info_head.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case CHOOSE_PHOTO:
                if (Build.VERSION.SDK_INT>=19){
                    handleImageOnKitKat(data);
                }else {
                    handleImageBeforeKitKat(data);
                }
                break;
            default:
                break;
        }
    }

    private void handleImageBeforeKitKat(Intent data){
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);
    }
//    @Override
//    public void onSubmit(String type, String content) {
//        switch (type){
//            case PHONE:
//                phone.setText(content);
//                break;
//            case IDENTITY:
//                identity.setText(content);
//                break;
//            case NICK_NAME:
//                String convert=content.substring(0,2)+"************"+content.substring(content.length()-4);
//                identity.setText(convert);
//                break;
//        }
//    }
    /**
     * 检查是否有对应权限
     *
     * @param activity 上下文
     * @param permission 要检查的权限
     * @return  结果标识
     */
    public int verifyPermissions(Activity activity, String permission) {
        int Permission = ActivityCompat.checkSelfPermission(activity,permission);
        if (Permission == PackageManager.PERMISSION_GRANTED) {
//            L.e("已经同意权限");
            return 1;
        }else{
//            L.e("没有同意权限");
            return 0;
        }
    }
    private void handleImageOnKitKat(Intent data){
        String imagePath=null;
        Uri uri=data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的uri，则使用document id处理
            String docId=DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];//解析出数字格式的id
                String selection=MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse(""),Long.parseLong(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的uri，则使用普通方式处理
            imagePath=getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的uri，直接获取图片路径即可
            imagePath=uri.getPath();
        }
        displayImage(imagePath);
    }
    private String getImagePath(Uri uri,String selection){
        String path=null;
        //通过uri和selection来获取真实的图片路径
        Cursor cursor=getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage(String imagePath){
        if (imagePath!=null){
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            info_head.setImageBitmap(bitmap);
        }else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}
