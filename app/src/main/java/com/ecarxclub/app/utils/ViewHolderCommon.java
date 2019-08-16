package com.ecarxclub.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.YxbContent;
import com.bumptech.glide.Glide;


public class ViewHolderCommon {

    private final SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context mContext;
//    private RequestOptions options=new RequestOptions().placeholder(R.mipmap.ic_default_goods);

    private ViewHolderCommon(Context context, ViewGroup parent, int layoutId) {

        this.mViews = new SparseArray<View>();
        this.mContext = context;
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        // setTag 为了防止和Glide加载图片的settag方法冲突特加一个key
        mConvertView.setTag(R.string.app_name, this);
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    /**
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @return
     */
    public static ViewHolderCommon get(Context context, View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new ViewHolderCommon(context, parent, layoutId);
        }
        return (ViewHolderCommon) convertView.getTag(R.string.app_name);
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public void setBackageColor(int viewId,int inturl){
        getView(viewId).setBackgroundResource(inturl);
    }
    public void setTextColors(int viewId,int inturl){
        TextView view = getView(viewId);
        view.setTextColor(inturl);
    }

    public void setViewVisbilty(int viewId,int inturl){
        getView(viewId).setVisibility(inturl);
    }

    //设置textview  上方图片
    public void setTextTopImg(int viewId,String url){
//        Glide.with(mContext).load( url).apply(YxbContent.options).into(new TextViewDrawableTarget((TextView) getView(viewId)));
    }

    /**
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolderCommon setText(int viewId, String text) {
        TextView view = getView(viewId);
        if (NotNull.isNotNull(text)) {
            view.setText(text);
        } else {
            view.setText("");
        }
        return this;
    }


    /**
     * @param viewId
     * @return
     */
    /*public ViewHolderCommon setCheckBox(int viewId, boolean isChecked) {
        CheckBox checkBox = getView(viewId);
        checkBox.setCompoundDrawablesWithIntrinsicBounds(R.drawable.selector_checkbox, 0, 0, 0);
        checkBox.setCompoundDrawablePadding(DisplayUtil.px2dip(XinchangApplication.mContext, 10));
        checkBox.setChecked(isChecked);
        return this;
    }

    public ViewHolderCommon setCheckBoxView(int viewId) {
        CheckBox checkBox = getView(viewId);
        checkBox.setCompoundDrawablesWithIntrinsicBounds(R.drawable.selector_checkbox, 0, 0, 0);
        checkBox.setCompoundDrawablePadding(DisplayUtil.px2dip(XinchangApplication.mContext, 10));
        return this;
    }*/

    public void setClickableF(int viewId){
        getView(viewId).setClickable(false);
    }

    public void setClickableT(int viewId){
        getView(viewId).setClickable(true);
    }

    public void inVisibleView(int viewId) {
        getView(viewId).setVisibility(View.VISIBLE);
    }

    public void hideView(int viewId) {
        getView(viewId).setVisibility(View.GONE);
    }

    public void onClickListener(int viewId, final onMyClickListener myClickListener){
        getView(viewId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClickListener.onClickListener(view);
            }
        });
    }

    public interface onMyClickListener{
        void onClickListener(View view);
    }

    /**
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolderCommon setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * @param viewId
     * @return
     */
    public ViewHolderCommon setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 加载普通图片
     *
     * @param viewId
     * @return
     */
   /* public ViewHolderCommon setImageFormUrl(int viewId, String url) {
        Glide.with(mContext).load(Config.API_URL + url).error(R.drawable.nophoto).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.nophoto).centerCrop().into((ImageView) getView(viewId));
        return this;
    }

    *//**
     * 默认图片为16:9的标准屏幕
     *//*
    public ViewHolderCommon setStandardImageFormUrl(int viewId, String url) {
        Glide.with(mContext).load(Config.API_URL + url).centerCrop().error(R.drawable.nophoto_long).placeholder(R.drawable.nophoto_long).into((ImageView) getView(viewId));
        return this;
    }
*/
    /**
     * 加载普通图片
     *
     * @param viewId
     * @return
     */
    public ViewHolderCommon setLongImageFormUrl(int viewId, String url) {
        Glide.with(mContext).load( url).apply(YxbContent.options).into((ImageView) getView(viewId));
        return this;
    }

    /**
     * 加载大图
     *
     * @param viewId
     * @return
     */
    public ViewHolderCommon setBigImageFormUrl(int viewId, String url) {
        Glide.with(mContext)
                .load( url)
                .apply(YxbContent.options)
                .into((ImageView) getView(viewId));
        return this;
    }

    /**
     * @param viewId
     * @return
     */
    public ViewHolderCommon setImageFormUrl(int viewId,String inturl) {
        Glide.with(mContext)
                .load( inturl)
                .apply(YxbContent.options)
                .into((ImageView) getView(viewId));
        return this;
    }

    /**
     * 加载圆形图
     *
     * @param viewId
     * @return
     */
    public ViewHolderCommon setRoundImageFormUrl(int viewId, String url) {
        Glide.with(mContext).load(url).apply(YxbContent.optionsRound)
                .into((ImageView) getView(viewId));
        return this;
    }


    /**
     * @param viewId
     * @param url
     * @param isCircle 设置是否为圆形图
     * @return
     *//*
    public ViewHolderCommon setImageFormUrl(int viewId, String url, boolean isCircle) {
        if (isCircle) {
            Glide.with(mContext).load(Config.API_URL + url).transform(new GlideCircleTransform(mContext)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.nophoto).into((ImageView) getView(viewId));
        } else {
            Glide.with(mContext).load(Config.API_URL + url).centerCrop().placeholder(R.drawable.nophoto).diskCacheStrategy(DiskCacheStrategy.ALL).into((ImageView) getView(viewId));
        }
        return this;
    }

    *//**
     * @param viewId
     * @param url
     * @param radiosWidth 设置图片为圆角 宽度默认为4
     * @return
     *//*
    public ViewHolderCommon setImageFormUrl(int viewId, String url, int radiosWidth) {
        Glide.with(mContext).load(Config.API_URL + url).centerCrop().transform(new GlideRoundTransform(mContext, radiosWidth)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.nophoto).into((ImageView) getView(viewId));
        return this;
    }


    *//**
     * 设置头像
     *
     * @return
     *//*
    public ViewHolderCommon setImagePhotoCircle(int viewId, String url) {
        Glide.with(mContext).load(Config.API_URL + url).transform(new GlideCircleTransform(mContext)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.nophoto).into((ImageView) getView(viewId));
        return this;
    }

    *//**
     *  radiosWidth 设置图片为圆角 宽度默认为4
     *  width       设置图片宽度
     *  height      设置图片高度
     * @return
     */
    /*

    public ViewHolderCommon setImageFormUrl(final int viewId, final String url, final int radiosWidth, final int width, final int height) {
        Glide.with(mContext).load(Config.API_URL + url).asBitmap().centerCrop()
                .transform(new GlideRoundTransform(mContext, radiosWidth)).placeholder(R.drawable.nophoto)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>(width, height) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        ((ImageView) getView(viewId)).setImageBitmap(bitmap);
                    }
                });
        return this;
    }*/


    public int getPosition() {
        return mPosition;
    }


}
