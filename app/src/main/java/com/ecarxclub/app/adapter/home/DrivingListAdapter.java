package com.ecarxclub.app.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ecarxclub.app.R;
import com.ecarxclub.app.model.home.MyDrivingListRes;
import com.ecarxclub.app.ui.home.DrivingDetailsActivity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by cwp
 * on 2019/4/25 16:38.
 * 车行驶记录
 */
public class DrivingListAdapter extends RecyclerArrayAdapter<MyDrivingListRes.DataBean.ListBean> {
    private int pos;
    private Context context;

    public DrivingListAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShopFgViewHolder(parent);
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        pos=position;
        super.OnBindViewHolder(holder, position);
    }

    class ShopFgViewHolder extends BaseViewHolder<MyDrivingListRes.DataBean.ListBean> {//
        private TextView tvTime,tvStartAddress,tvCity,tvEndAddress,tvStartTime;
        private ImageView imgLeft;
        private LinearLayout linearLayout;

        public ShopFgViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_driving_rcylist);
            tvTime = $(R.id.item_driving_time);
            tvStartTime = $(R.id.item_dd_list_time);
            tvCity = $(R.id.item_dd_list_city);
            tvStartAddress = $(R.id.item_dd_list_stataddress);
            tvEndAddress = $(R.id.item_dd_list_endaddress);
            imgLeft = $(R.id.item_img_left);
            linearLayout = $(R.id.item_ll_driving_next);
        }

        @Override
        public void setData(final MyDrivingListRes.DataBean.ListBean data) {
            if(data!=null){
                if (data.isShow){
//                    tvTime.setText(TimeUtils.getTimeTwo(data.date+""));
                    tvTime.setText(data.endDate);
                    tvTime.setVisibility(View.VISIBLE);
                }else {
                    tvTime.setVisibility(View.GONE);
                }
                tvStartTime.setText(data.startTime);
                tvCity.setText(data.city);
                tvStartAddress.setText(data.startLocation);
                tvEndAddress.setText(data.endLocation);

                if (pos%2==0){
                    imgLeft.setBackgroundResource(R.mipmap.pic_green);
                }else {
                    imgLeft.setBackgroundResource(R.mipmap.pic_blue);
                }
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, DrivingDetailsActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString("drvId",data.id);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });

//                Glide.with(getContext())
//                        .load(data.productIcon)
//                        .apply(YxbContent.options)
//                        .into(img);


            }
        }
    }




}
