package com.ecarxclub.app.utils.banner.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自适应listview
 * 作者：LJJ on 2016/4/26 0026 10:57
 * 邮箱：361739775@qq.com
 */
public class MyOwnListView extends ListView {
    public MyOwnListView(Context context) {
        super(context);
    }

    public MyOwnListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyOwnListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public MyOwnListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
