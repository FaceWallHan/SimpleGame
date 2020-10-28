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
import com.example.simple.adapter.SearchNewsAdapter;
import com.example.simple.bean.BeanNews;

import java.util.ArrayList;
import java.util.List;

public class NewsChildFragment extends Fragment {
    public static final String NEWS_TYPE = "newsType";
    private String newsType;
    private View view;

    public static NewsChildFragment newInstance(String newsType){
        Bundle bundle = new Bundle();
        bundle.putString(NEWS_TYPE, newsType);
        NewsChildFragment fragment=new NewsChildFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.search_news_layout,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            newsType = bundle.getString(NEWS_TYPE);
        }
        List<BeanNews> newsList=new ArrayList<>();
        List<BeanNews> beanNews = AppClient.getInstance().getNewsList();
        for (int i = 0; i < beanNews.size(); i++) {
            BeanNews news = beanNews.get(i);
            if (newsType.equals(news.getNewsType())){
                newsList.add(news);
            }
        }
        ListView search_list = view.findViewById(R.id.search_list);
        search_list.setAdapter(new SearchNewsAdapter(view.getContext(),newsList));
        search_list.setEmptyView(view.findViewById(R.id.search_tv));
    }
}
