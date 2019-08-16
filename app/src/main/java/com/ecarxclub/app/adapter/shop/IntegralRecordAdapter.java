package com.ecarxclub.app.adapter.shop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.model.shop.IntegralRecordRes;
import com.ecarxclub.app.utils.ToastUtils;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by cwp
 * on 2019/4/25 16:38.
 */
public class IntegralRecordAdapter extends RecyclerArrayAdapter<IntegralRecordRes.DataBean.RecordBean.ListBean> {
//    private String strTime="";
    private   IntegralRecordViewHolder integralRecordViewHolde;
    public IntegralRecordAdapter(Context context){
        super(context);
    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new IntegralRecordViewHolder(parent);
    }

    class IntegralRecordViewHolder extends BaseViewHolder<IntegralRecordRes.DataBean.RecordBean.ListBean> {//
        private TextView tvName, tvTime, tvNum;
        private TextView tvTopTime;

        public IntegralRecordViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_integral_record);
            tvName = $(R.id.item_tv_record_name);
            tvTime = $(R.id.item_tv_record_time);
            tvTopTime = $(R.id.item_tv_record_top);
            tvNum = $(R.id.item_tv_record_num);
        }

        @Override
        public void setData(IntegralRecordRes.DataBean.RecordBean.ListBean data) {
            tvName.setText(data.changeCover);
            tvTime.setText(data.createTime);
            if (data.changeIntegral>0){
                tvNum.setText("+" + (int) data.changeIntegral);
            }else {
                tvNum.setText((int) data.changeIntegral+"");
            }
            if(data.isShow){
                tvTopTime.setVisibility(View.VISIBLE);
                tvTopTime.setText(data.createTime.substring(0, 7));
            }else {
                tvTopTime.setVisibility(View.GONE);
            }
        }

    }

}
