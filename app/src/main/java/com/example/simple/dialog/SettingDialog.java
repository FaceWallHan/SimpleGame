package com.example.simple.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.simple.R;
import com.example.simple.utils.DataKeys;
import com.example.simple.utils.MyTools;

public class SettingDialog extends DialogFragment {
    private View view;
    private EditText address_et, port_et;
    private Button save_bt, cancel_bt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        view = inflater.inflate(R.layout.setting_dialog, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inView();
        save_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = address_et.getText().toString().trim();
                String port = port_et.getText().toString().trim();
                if (!decideAddress(address)) {
                    Toast.makeText(getContext(), "请输入正确的IP地址" , Toast.LENGTH_SHORT).show();
                } else if (!decidePort(port)){
                    Toast.makeText(getContext(), "请输入正确的端口号", Toast.LENGTH_SHORT).show();
                }else {
                    MyTools.getInstance().setData(DataKeys.IP_ADDRESS,address);
                    MyTools.getInstance().setData(DataKeys.PORT,port);
                    Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingDialog.this.dismiss();
            }
        });
    }
    private boolean decidePort(String port){
        boolean is=false;
        if (port!=null&&!port.equals("")){
            int index=Integer.parseInt(port);
            if (index>0&&index<65535){
                is=true;
            }
        }
        return is;
    }
    private boolean decideAddress(String str) {
        boolean is=true;
        String[] ip=str.split("\\.");
        if (ip.length==4){
            if (Integer.parseInt(ip[0])>255||ip[0].equals("0")||Integer.parseInt(ip[0])<0){
                is=false;
            }else {
                for (int i=1;i<ip.length;i++){
                    if (Integer.parseInt(ip[i])>255||Integer.parseInt(ip[i])<0){
                        is=false;
                        break;
                    }else {
                        is=true;
                    }
                }
            }

        }else {
            is=false;
        }
        return is;

    }

    private void inView() {
        address_et = view.findViewById(R.id.address_et);
        port_et = view.findViewById(R.id.port_et);
        save_bt = view.findViewById(R.id.save_bt);
        cancel_bt = view.findViewById(R.id.cancel_bt);
        String ip= (String) MyTools.getInstance().getData(DataKeys.IP_ADDRESS,"118.190.26.201");
        String port= (String) MyTools.getInstance().getData(DataKeys.PORT,"8080");
        address_et.setText(ip);
        port_et.setText(port);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        Dialog dialog = getDialog();
//        if (dialog != null) {
//            DisplayMetrics dm = new DisplayMetrics();
//            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.7), (int) (dm.widthPixels * 0.7));
//        }
//    }
    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(700, 600);
    }
}
