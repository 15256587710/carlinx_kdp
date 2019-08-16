package com.ecarxclub.app.adapter.shop;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ecarxclub.app.R;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.model.shop.fgshop.ShopListRes;
import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by cwp
 * on 2019/4/25 16:38.
 */
public class ShopFgAdapter extends RecyclerArrayAdapter<ShopListRes.DataBean.ListBean> {
    private int pos;
    public ShopFgAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShopFgViewHolder(parent);
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
//        ToastUtils.i("OnBindViewHolder",""+position);
        pos=position;
        super.OnBindViewHolder(holder, position);
    }

    class ShopFgViewHolder extends BaseViewHolder<ShopListRes.DataBean.ListBean> {//
        private TextView tvScroe, tvTitle;
        private ImageView img;

        public ShopFgViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_shop_fglist);
            tvScroe = $(R.id.item_tv_shoplist_score);
            tvTitle = $(R.id.item_tv_shoplist_title);
            img = $(R.id.item_img_shoplist);
        }

        @Override
        public void setData(ShopListRes.DataBean.ListBean data) {
            if(data!=null){
                tvScroe.setText(data.integral + "积分" + data.money + "元");
                tvTitle.setText(data.productName);
                Glide.with(getContext())
                        .load(data.productIcon)
                        .apply(YxbContent.options)
                        .into(img);
            }
        }
    }

}
