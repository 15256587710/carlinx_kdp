package com.ecarxclub.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 作者：wecent
 * 时间：2017/11/20
 * 描述：
 */

public class ScrollViewForToolBar extends ScrollView {

    private OnScrollChangedListener mOnScrollChangedListener;

    public ScrollViewForToolBar(Context context) {
        super(context);
    }

    public ScrollViewForToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewForToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        mOnScrollChangedListener = listener;
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt);
    }
}
