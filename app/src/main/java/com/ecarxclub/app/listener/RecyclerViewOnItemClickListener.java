package com.ecarxclub.app.listener;

import android.view.View;

/**
 * RecyclerView接口，用于点击或者长按
 */
public interface RecyclerViewOnItemClickListener {
    //点击事件
    void OnRecycleItemClick(View view, int position);

    //长按事件
    void onRecycleItemLongClick(View view, int position);
}
