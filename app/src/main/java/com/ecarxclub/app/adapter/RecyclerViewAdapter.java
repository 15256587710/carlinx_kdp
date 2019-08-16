package com.ecarxclub.app.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecarxclub.app.listener.RecyclerViewOnItemClickListener;
import com.ecarxclub.app.utils.NotNull;
import com.ecarxclub.app.utils.RecyclerViewHolder;

import java.util.List;


/**
 * Created by Arron_Zhang
 * time: 2016/4/14.
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    protected Context context; //上下文环境
    protected List<T> mDatas;//填充的数据
    protected LayoutInflater inflater;//加载布局文件
    protected RecyclerViewOnItemClickListener listener;//监听接口
    protected final int mItemLayoutId;//布局文件的ID

    /**
     * 构造方法
     *
     * @param context
     * @param mDatas
     * @param mItemLayoutId
     */
    public RecyclerViewAdapter(Context context, List<T> mDatas, int mItemLayoutId) {
        this.context = context;
        this.mDatas = mDatas;
        this.mItemLayoutId = mItemLayoutId;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 设置接口回调方法
     * 实现监听接口的方法
     *
     * @param listener 监听接口
     */
    public void setOnItemClickListener(RecyclerViewOnItemClickListener listener) {
        this.listener = listener;
    }


    /**
     * 添加数据到集合之中
     * 添加一页
     *
     * @param list
     */
    public void addPage(List<T> list) {
        if (NotNull.isNotNull(list)) {
            this.mDatas.addAll(list);
            this.notifyDataSetChanged();
        }
    }

    //添加数据
    public void addData(int position,List<T> lst) {
        mDatas.add(position, lst.get(0));
        notifyItemInserted(position);
    }

    //删除数据
    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 添加数据到集合之中
     * 添加一页到头部
     *
     * @param list
     */
    public void addPageOnHead(List<T> list) {
        if (NotNull.isNotNull(list)) {
            this.mDatas.addAll(0, list);
            this.notifyDataSetChanged();
        }
    }

    /**
     * 多类型的适配器
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     * 列表项的总数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(mItemLayoutId, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(context, itemView, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        /**
         * 进行数据的加载
         */
        convert(holder, mDatas.get(position),position);
    }

    public abstract void convert(RecyclerViewHolder holder, T item,int pos);


}



