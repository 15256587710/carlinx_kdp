package com.ecarxclub.app.adapter.card;

import android.content.Context;
import android.view.ViewGroup;

import com.ecarxclub.app.model.card.CardListRes;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by cwp
 * on 2019/4/25 16:38.
 */
public class CardBaseAdapter extends RecyclerArrayAdapter<CardListRes.DataBean.ListBean> {
    public CardBaseAdapter(Context context){
        super(context);
    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardBaseViewHolder(parent);
    }

}
