package com.example.simple.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.simple.AppClient;
import com.example.simple.R;
import com.example.simple.activity.RegisterInfoActivity;
import com.example.simple.adapter.OrdinaryAdapter;
import com.example.simple.bean.BeanAttend;
import com.example.simple.bean.BeanDepartment;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrdinaryFragment extends Fragment implements AdapterView.OnItemClickListener {
    private View view;
    private BeanDepartment department;
    private List<BeanAttend> attendList;
    private OrdinaryAdapter adapter;
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
            assert department != null;
            BeanAttend.setDepartmentName(department.getDeptName());
            inView();
            startHospitalDepartmentRequest();
        }
    }
    private void inView(){
        ListView ordinary_lv = view.findViewById(R.id.ordinary_lv);
        attendList=new ArrayList<>();
        adapter=new OrdinaryAdapter(view.getContext(),attendList);
        ordinary_lv.setAdapter(adapter);
        ordinary_lv.setOnItemClickListener(this);
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
                                JSONArray array=new JSONArray(jsonObject.getString("ROWS_DETAIL"));
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object=array.getJSONObject(i);
                                    BeanAttend attend=new BeanAttend();
                                    attend.setDepartmentId(object.getString("departmentId"));
                                    attend.setType(object.getString("type"));
                                    attend.setDoctorId(object.getString("doctorId"));
                                    attend.setHospitalId(object.getString("hospitalId"));
                                    attend.setNum(object.getString("num"));
                                    attend.setTime(object.getString("time"));
                                    attend.setTime(object.getString("time"));
                                    attendList.add(attend);
                                }
                                adapter.notifyDataSetChanged();
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        BeanAttend attend = attendList.get(i);
        Intent intent=new Intent(view.getContext(), RegisterInfoActivity.class);
        intent.putExtra("BeanAttend",attend);
        startActivity(intent);
    }
}
