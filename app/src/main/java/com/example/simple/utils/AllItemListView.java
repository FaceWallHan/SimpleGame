package com.example.simple.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class AllItemListView extends GridView {
    public AllItemListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AllItemListView(Context context) {
        super(context);
    }
    /**
     * 设置不滚动
     * 多个item全部显示
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}
