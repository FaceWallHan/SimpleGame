package com.example.simple.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.simple.activity.MainActivity;
import com.example.simple.R;
import com.example.simple.dialog.SettingDialog;
import com.example.simple.utils.DataKeys;
import com.example.simple.utils.MyTools;

public class GuideFragment extends Fragment {
    private View view;
    private String imageUrl;
    private boolean isLast=false;

    public GuideFragment(String imageUrl, boolean isLast) {
        this.imageUrl = imageUrl;
        this.isLast=isLast;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (isLast){
            view=inflater.inflate(R.layout.image_last_fragment_layout,container,false);
        }else {
            view=inflater.inflate(R.layout.image_fragment_layout,container,false);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView guide_image=view.findViewById(R.id.guide_image);
        Glide.with(this).load(imageUrl).into(guide_image);
        if (isLast){
            Button setting=view.findViewById(R.id.setting);
            Button enter_bt=view.findViewById(R.id.enter_bt);
            setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SettingDialog dialog=new SettingDialog();
                    dialog.show(getFragmentManager(),"");
                }
            });
            enter_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyTools.getInstance().setData(DataKeys.isFirst,false);
                    startActivity(new Intent(getActivity(),MainActivity.class));
                    getActivity().finish();
                }
            });
        }
    }
}
