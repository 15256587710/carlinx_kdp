package com.ecarxclub.app.adapter.mine.message;

import android.content.Context;
import android.view.ViewGroup;

import com.ecarxclub.app.model.mine.MessageRes;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by cwp
 * on 2019/4/25 16:38.
 */
public class MessageAdapter extends RecyclerArrayAdapter<MessageRes.DataBean.ListBean> {
    public MessageAdapter(Context context){
        super(context);
    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(parent);
    }

}
