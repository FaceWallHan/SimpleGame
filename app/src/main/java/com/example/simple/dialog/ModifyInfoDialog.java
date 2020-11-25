package com.example.simple.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.simple.R;

public class ModifyInfoDialog extends DialogFragment implements View.OnClickListener {
    private View view;
    private EditText inputInfo;
    private Button cancel,determine;
    private String content;
    private OnSubmitListener listener;

    public void setListener(OnSubmitListener listener) {
        this.listener = listener;
    }
    public interface OnSubmitListener{
        void onSubmit(String content);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
        params.windowAnimations = R.style.bottomSheet_animation;
        getDialog().getWindow().setAttributes(params);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);
        view=inflater.inflate(R.layout.modify_info_layout,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inView();
    }
    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(700, 500);
    }

    private void inView(){
        inputInfo=view.findViewById(R.id.inputInfo);
        cancel=view.findViewById(R.id.cancel);
        determine=view.findViewById(R.id.determine);
        cancel.setOnClickListener(this);
        determine.setOnClickListener(this);
        content=getTag();
        inputInfo.setText(content);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel:
                dismiss();
                break;
            case R.id.determine:
                String info=inputInfo.getText().toString().trim();
                if (!info.equals("")&&listener!=null){
                    listener.onSubmit(info);
                }
                dismiss();
                break;
        }
    }
}
