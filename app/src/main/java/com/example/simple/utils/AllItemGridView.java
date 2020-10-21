package com.example.simple.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class AllItemGridView extends GridView {
    public AllItemGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AllItemGridView(Context context) {
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
