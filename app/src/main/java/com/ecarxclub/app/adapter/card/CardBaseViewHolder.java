package com.ecarxclub.app.adapter.card;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.model.card.CardListRes;
import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by cwp
 * on 2019/4/25 16:41.
 */
public class CardBaseViewHolder extends BaseViewHolder<CardListRes.DataBean.ListBean> {
    private TextView tvName,tvTitle,tvLook,tvType,tvTag;
    private ImageView img;
    public CardBaseViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_erv_card_list);
        tvName=$(R.id.item_erv_card_name);
        tvTitle=$(R.id.item_erv_card_title);
        tvLook=$(R.id.item_tv_look);
        img=$(R.id.item_img_card);
        tvType=$(R.id.item_tv_card_type);
        tvTag=$(R.id.item_tv_card_tag);
    }

    @Override
    public void setData(CardListRes.DataBean.ListBean data) {
        tvName.setText(data.couponName);
        tvTitle.setText(data.couponTitle);
        tvLook.setText("查看\n详情");
        Glide.with(getContext())
                .load(data.seller.sellerAppLogo)
                .apply(YxbContent.options)
                .into(img);

        tvType.setText(data.type.typeName);
        tvTag.setText(data.targs.tagName);
    }
}
