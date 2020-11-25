package com.example.simple.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.simple.R;
import com.example.simple.adapter.ManageAdapter;
import com.example.simple.bean.BeanManager;
import com.example.simple.net.NetCall;
import com.example.simple.net.VolleyTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PersonalManageFragment extends Fragment {
    private View view;
    private List<BeanManager> managerList;
    private ManageAdapter adapter;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inView();
        startManageRequest();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.personal_manage_layout,container,false);
        return view;
    }
    private void startManageRequest(){
        VolleyTo volleyTo=new VolleyTo();
        volleyTo.setUrl("accountByUserId")
                .setJsonObject("userid",PersonalFragment.userId)
                .setVolleyLo(new NetCall() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("RESULT").equals("S")){
                                TypeToken<List<BeanManager>>token=new  TypeToken<List<BeanManager>>(){};
                                String json=jsonObject.getString("ROWS_DETAIL");
                                managerList.addAll(new Gson().fromJson(json,token.getType()));
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
    private void inView(){
        ListView manage_lv = view.findViewById(R.id.manage_lv);
        LinearLayout add_item = view.findViewById(R.id.add_item);
        managerList=new ArrayList<>();
        adapter=new ManageAdapter(view.getContext(),managerList);
        manage_lv.setAdapter(adapter);
        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
