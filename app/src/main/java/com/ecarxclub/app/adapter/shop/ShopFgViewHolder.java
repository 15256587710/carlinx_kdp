package com.ecarxclub.app.adapter.shop;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.model.shop.fgshop.ShopListRes;
import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by cwp
 * on 2019/4/25 16:41.
 */
public class ShopFgViewHolder extends BaseViewHolder<ShopListRes.DataBean.ListBean> {//ShopListRes.DataBean.ListBean   CardCouponTypeRes.DataBean
    private TextView tvScroe,tvTitle;
    private ImageView img;
    public ShopFgViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_shop_fglist);
        tvScroe=$(R.id.item_tv_shoplist_score);
        tvTitle=$(R.id.item_tv_shoplist_title);
        img=$(R.id.item_img_shoplist);
    }

    @Override
    public void setData(ShopListRes.DataBean.ListBean data) {
        tvScroe.setText(data.productStock+"积分"+data.money+"元");
        tvTitle.setText(data.productCover);
        Glide.with(getContext())
                .load(data.productIcon)
                .apply(YxbContent.options)
                .into(img);
    }
}
