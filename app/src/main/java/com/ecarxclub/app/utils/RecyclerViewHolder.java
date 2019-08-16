package com.ecarxclub.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.listener.RecyclerViewOnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


/**
 * Created by Arron_Zhang
 * time: 2016/4/14.
 * RecyclerView 适配器对应的ViewHolder
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private RecyclerViewOnItemClickListener listener;
    private final SparseArray<View> mViews;
    public View itemView;
    private Context context;
    private String mUrl="";//UrlOkHttpUtils.YXB_URL
    private RequestOptions options=new RequestOptions().placeholder(R.mipmap.ic_app_default_shop).error(R.mipmap.ic_app_default_shop);

    public RecyclerViewHolder(Context context, View itemView, RecyclerViewOnItemClickListener listener) {
        super(itemView);
        this.context = context;
        this.listener = listener;
        this.itemView = itemView;
        this.mViews = new SparseArray<>();

        itemView.setTag(R.string.app_name, this);//tag_key
        //点击事件
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * @param viewId
     * @param text
     * @return
     */
    public RecyclerViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        if (NotNull.isNotNull(view)) {
            view.setText(text);
        }
        return this;
    }

    /**
     * @param viewId
     * @param text
     * @return
     */
    public RecyclerViewHolder setEdText(int viewId, String text) {
        EditText view = getView(viewId);
        if (NotNull.isNotNull(view)) {
            view.setText(text);
        }
        return this;
    }


    public RecyclerViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * @param viewId
     * @return
     */
    public RecyclerViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }


    /**
     * 图片设置
     */
    public RecyclerViewHolder setImageFormUrl(int viewId, String url) {
        Glide.with(context)
                .load(mUrl + url)
                .apply(options)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .error(R.mipmap.ic_default_goods)//加载失败
//                .placeholder(R.mipmap.ic_default_goods)//等待加载
                .into((ImageView) getView(viewId));
        return this;
    }

    /**
     * 图片设置
     */
    public RecyclerViewHolder setImageBigFormUrl(int viewId, String url) {
        Glide.with(context)
                .load(mUrl + url)
                .apply(options)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .error(R.mipmap.ic_default_goods)
//                .placeholder(R.mipmap.ic_default_goods)
                .into((ImageView) getView(viewId));
        return this;
    }

    public RecyclerViewHolder setImageFormUrlRoundImage(int viewId, String url) {
        Glide.with(context)
                .load(mUrl + url)
                .apply(new RequestOptions().transform(new GlideCircleTransform(context)).placeholder(R.mipmap.ic_launcher))
//                .transform(new GlideCircleTransform(context))
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .error(R.mipmap.ic_default_goods)
//                .placeholder(R.mipmap.ic_default_goods)
                .into((ImageView) getView(viewId));
        return this;
    }

    public void inVisibleView(int viewId) {
        getView(viewId).setVisibility(View.VISIBLE);
    }

    public void hideView(int viewId) {
        getView(viewId).setVisibility(View.GONE);
    }

//    /**
//     * @param viewId
//     * @return
//     */
//    public RecyclerViewHolder setCheckBox(int viewId, boolean isChecked) {
//        CheckBox checkBox = getView(viewId);
//        checkBox.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_default_goods, 0, 0, 0);
//        checkBox.setCompoundDrawablePadding(DisplayUtil.px2dip(context, 10));
//        checkBox.setChecked(isChecked);
//        return this;
//    }


    /**
     * 图片设置
     */
    public RecyclerViewHolder setBigImageFormUrl(int viewId, String url) {
        Glide.with(context).
                load(mUrl + url)
//                .centerCrop()
//                .error(R.mipmap.ic_default_goods)
//                .placeholder(R.mipmap.ic_default_goods)
                .into((ImageView) getView(viewId));
        return this;
    }

    /**
     * 默认图片为16:9的标准屏幕
     */
    public RecyclerViewHolder setStandardImageFormUrl(int viewId, String url) {
        Glide.with(context)
                .load(mUrl + url)
//                .centerCrop()
//                .error(R.mipmap.ic_default_goods)
//                .placeholder(R.mipmap.ic_default_goods)
                .into((ImageView) getView(viewId));
        return this;
    }


    /**
     * 普通的点击事件
     */
    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.OnRecycleItemClick(v, getLayoutPosition());
        }
    }

    /**
     * 长按点击事件
     */
    @Override
    public boolean onLongClick(View v) {
        if (listener != null) {
            listener.onRecycleItemLongClick(v, getLayoutPosition());
        }
        return true;
    }
}
