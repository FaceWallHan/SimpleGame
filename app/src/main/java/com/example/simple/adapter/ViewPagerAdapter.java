package com.example.simple.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    /**ViewPager2*/
    private List<Fragment> list;
    public ViewPagerAdapter(@NonNull FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.list=list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
//        return list.get(position/list.size());
    }
    /**
     *妄图解决引导页ViewPager当滑动到最后一页,再继续滑动切换页面的问题
     * */
    @Override
    public int getCount() {
        return list.size();
//        return Integer.MAX_VALUE;
    }
}
