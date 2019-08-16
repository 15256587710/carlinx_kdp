package com.ecarxclub.app.adapter.mine.coupon;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.model.mine.MyCouponRes;
import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by cwp
 * on 2019/4/25 16:38.
 */
public class MyCouponAdapter extends RecyclerArrayAdapter<MyCouponRes.DataBean.ListBean> {
    private Context context;
    public MyCouponAdapter(Context context){
        super(context);
        this.context=context;
    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyCouponViewHolder(parent);
    }


    class MyCouponViewHolder extends BaseViewHolder<MyCouponRes.DataBean.ListBean> {
        private TextView tvName,tvTitle,tvTime;
        private ImageView img;
        public MyCouponViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_mine_coupon);
            tvName=$(R.id.item_tv_coupon_name);
            tvTitle=$(R.id.item_tv_coupon_title);
            img=$(R.id.item_img_coupon);
            tvTime=$(R.id.item_tv_coupon_time);
        }

        @Override
        public void setData(MyCouponRes.DataBean.ListBean data) {
            tvName.setText(data.coupon.couponName+data.coupon.couponTitle);
            tvTitle.setText(data.coupon.limitRemark);
            if (data.coupon.seller!=null){
                Glide.with(context).load(data.coupon.seller.sellerAppLogo)
                        .apply(YxbContent.options)
                        .into(img);
            }
            String mStartTime="";
            if (data.coupon.limitTimeStart!=null&&data.coupon.limitTimeStart.contains(" ")){
                mStartTime=data.coupon.limitTimeStart.substring(0,data.coupon.limitTimeStart.indexOf(" "));
                if (mStartTime.length()==10){
                    mStartTime=mStartTime.substring(0,4)+"年"
                            +mStartTime.substring(5,7)+"月"
                            +mStartTime.substring(8,10)+"日";
                }
            }
            String mEndTime="";
            if (data.coupon.limitTimeEnd!=null&&data.coupon.limitTimeEnd.contains(" ")){
                mEndTime=data.coupon.limitTimeEnd.substring(0,data.coupon.limitTimeEnd.indexOf(" "));
                if (mEndTime.length()==10){
                    mEndTime=mEndTime.substring(0,4)+"年"
                            +mEndTime.substring(5,7)+"月"
                            +mEndTime.substring(8,10)+"日";
                }
            }
            tvTime.setText("有效时间："+mStartTime+"-"+mEndTime);
        }
    }
}
