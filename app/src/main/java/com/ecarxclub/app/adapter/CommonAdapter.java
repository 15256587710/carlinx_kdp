package com.ecarxclub.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ecarxclub.app.utils.NotNull;
import com.ecarxclub.app.utils.ViewHolderCommon;

import java.util.List;


//Baseadapter的封装类
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;
    protected int itemCount;

    // /**
    // public CommonAdapter(Context context, int count, int itemLayoutId){
    // this.mContext = context;
    // this.mInflater = LayoutInflater.from(context);
    // this.itemCount = count;
    // this.mItemLayoutId = itemLayoutId;
    // }

    public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }


    public void setList(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }


    public void addPage(List<T> list) {
        if (NotNull.isNotNull(list)) {
            this.mDatas.addAll(list);
            this.notifyDataSetChanged();
        }
    }

    public void addPageOnHead(List<T> list) {
        if (NotNull.isNotNull(list)) {
            this.mDatas.addAll(0, list);
            this.notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolderCommon viewHolderCommon = getViewHolder(convertView, parent);
        viewHolderCommon.setPosition(position);
        convert(viewHolderCommon, getItem(position));
        return viewHolderCommon.getConvertView();
    }

    public abstract void convert(ViewHolderCommon helper, T item);

    private ViewHolderCommon getViewHolder(View convertView, ViewGroup parent) {
        return ViewHolderCommon.get(mContext, convertView, parent, mItemLayoutId);
    }
}
