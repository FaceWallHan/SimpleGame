package com.example.simple.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.simple.R;

public class SettingDialog extends DialogFragment {
    private View view;
    private EditText address_et,port_et;
    private Button save_bt,enter_bt,cancel_bt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window= getDialog().getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);

        getDialog().setCanceledOnTouchOutside(false);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        //window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
        //window.setLayout(width/2, height/2);//这2行,和上面的一样,注意顺序就行;
        Log.d("1111111111111111", "onCreateView: "+height+width);
        view=inflater.inflate(R.layout.setting_dialog,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inView();
        Log.d("1111111111111111", "onActivityCreated: ");
        address_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address=address_et.getText().toString().trim();

            }
        });
        port_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String port=port_et.getText().toString().trim();
                if (!port.equals("")){
                    int index=Integer.parseInt(port);
                    if (index>0&&index<65535){

                    }
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
    private boolean decideAddress(String ip){
        boolean is = false;
        if (ip==null||ip.equals("")){
            is=false;
        }else {
            String[] address=ip.split("\\.");
            if (address.length==4){
                if (address[0].equals("0")){
                    is=false;
                }else {
                    for (String s : address) {
                        int index = Integer.parseInt(s);
                        if (index < 0 || index > 255) {
                            is = false;
                            break;
                        } else {
                            is = true;
                        }
                    }
                }
            }
        }
        return is;
    }
    private void inView(){
        address_et=view.findViewById(R.id.address_et);
        port_et=view.findViewById(R.id.port_et);
        save_bt=view.findViewById(R.id.save_bt);
        enter_bt=view.findViewById(R.id.enter_bt);
        cancel_bt=view.findViewById(R.id.cancel_bt);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.7), (int) (dm.widthPixels * 0.5));
        }
    }
}
