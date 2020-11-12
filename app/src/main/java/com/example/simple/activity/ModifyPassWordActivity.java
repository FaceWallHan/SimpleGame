package com.example.simple.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simple.R;
import com.example.simple.utils.DataKeys;
import com.example.simple.utils.MyTools;

public class ModifyPassWordActivity extends BaseActivity implements View.OnClickListener {
    private EditText old_pass,new_pass,confirm_pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_password_layout);
        inView();
        setTitleText(getResources().getString(R.string.modify_password));
    }
    private void inView(){
        TextView modify_info = findViewById(R.id.modify_info);
        modify_info.setOnClickListener(this);
        old_pass=findViewById(R.id.old_pass);
        new_pass=findViewById(R.id.new_pass);
        confirm_pass=findViewById(R.id.confirm_pass);
        old_pass.setText(MyTools.getInstance().getData(DataKeys.passWord,"123456").toString());
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.modify_info) {
            String oldStr = old_pass.getText().toString().trim();
            String newTStr = new_pass.getText().toString().trim();
            String confirmTStr = confirm_pass.getText().toString().trim();
            if (newTStr.equals("")) return;
            if (confirmTStr.equals("")) return;
            if (oldStr.equals(newTStr)) {
                MyTools.getInstance().showToast("新密码不能与原密码相同", this);
                return;
            }
            if (!newTStr.equals(confirmTStr)) {
                MyTools.getInstance().showToast("请确认密码一致", this);
                return;
            }
            MyTools.getInstance().showToast("success", this);
            //此处应有网络请求
        }
    }
}
