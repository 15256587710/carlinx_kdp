package com.ecarxclub.app.utils.banner.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.utils.GlideRoundTransform;
import com.ecarxclub.app.utils.banner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;


/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */

public class NetworkImageHolderView implements Holder<String> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//CENTER_CROP
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
//        imageView.setImageResource(R.mipmap.ic_default_goods);
//        ImageLoader.getInstance().displayImage(data,imageView, Apps.options);//imageloader下载图片
        Glide.with(context).load( data).apply(new RequestOptions().
                placeholder(R.mipmap.ic_app_default_card_banner).error(R.mipmap.ic_app_default_card_banner).
                transform(new GlideRoundTransform(context,5))).into(imageView);

    }
}
