package com.example.simple.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.bean.BeanAttend;
import com.example.simple.bean.BeanDepartment;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrdinaryFragment extends Fragment {
    private View view;
    private BeanDepartment department;
    private ListView ordinary_lv;
    private List<BeanAttend> attendList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.ordinary_layout,container,false);
        return view;
    }
    public static OrdinaryFragment newInstance(BeanDepartment department){
        Bundle bundle = new Bundle();
        bundle.putSerializable("OrdinaryFragment", department);
        OrdinaryFragment fragment=new OrdinaryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            department = (BeanDepartment) bundle.getSerializable("OrdinaryFragment");
            inView();
            startHospitalDepartmentRequest();
        }
    }
    private void inView(){
        ordinary_lv=view.findViewById(R.id.ordinary_lv);
        attendList=new ArrayList<>();
        //先根据departmentId找出每个departmentName,然后再list显示出来，点击item的预约进入预约挂号界面，显示预约成功，点击成功后回到“首页”？？(SnackBar)
    }
    private void startHospitalDepartmentRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("getDutyByDepartmentId")
                .setJsonObject("hospitalId", AppClient.hospitalId)
                .setJsonObject("departmentId",department.getDeptId())
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                TypeToken<List<BeanAttend>>token=new TypeToken<List<BeanAttend>>(){};
                                String json=jsonObject.getString("ROWS_DETAIL");
                                attendList.addAll(new Gson().fromJson(json,token.getType()));
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
}
