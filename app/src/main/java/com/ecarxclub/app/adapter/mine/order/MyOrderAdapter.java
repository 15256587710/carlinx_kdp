package com.ecarxclub.app.adapter.mine.order;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.RecyclerViewAdapter;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.model.card.CardListRes;
import com.ecarxclub.app.model.mine.MyOrderListRes;
import com.ecarxclub.app.utils.RecyclerViewHolder;
import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by cwp
 * on 2019/4/25 16:38.
 */
public class MyOrderAdapter extends RecyclerArrayAdapter<MyOrderListRes.DataBean.ListBean> {
    private int position;
    private onViewClickListener onViewClickListener;
    private Context context;
    private RecyclerViewAdapter recyclerViewAdapter;
    public MyOrderAdapter(Context context){
        super(context);
        this.context=context;
    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyOrderViewHolder(parent);
    }

    @Override
    public void OnBindViewHolder(BaseViewHolder holder, int position) {
        this.position = position;
        super.OnBindViewHolder(holder, position);
    }

    class MyOrderViewHolder extends BaseViewHolder<MyOrderListRes.DataBean.ListBean> {
        private TextView tvName,tvOrderId,tvStatus,tvInfo,tvNum;//tvType,tvTag,
        private Button btnPay;
        private ImageView img;
        private LinearLayout llBottom;
        private RecyclerView recyclerView;
        public MyOrderViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_myorder_list);
            tvName=$(R.id.item_tv_myorder_name);
            tvOrderId=$(R.id.item_tv_myorder_id);
            img=$(R.id.item_img_myorder);
            tvStatus=$(R.id.item_tv_myorder_status);
            tvInfo=$(R.id.item_tv_myorder_info);
            tvNum=$(R.id.item_tv_myorder_num);
            btnPay=$(R.id.item_btn_myorder_pay);
            llBottom=$(R.id.item_ll_myorder_bom);
            recyclerView=$(R.id.item_rv_tag_shop);
        }

        @Override
        public void setData(MyOrderListRes.DataBean.ListBean data) {
            tvName.setText(data.items.get(0).product.productName);
            tvOrderId.setText("订单号："+data.items.get(0).orderId);
            if(data.orderStatus==0){
                tvStatus.setText("待付款");
                llBottom.setVisibility(View.VISIBLE);
            }else if(data.orderStatus==1){
                tvStatus.setText("待发货");
                llBottom.setVisibility(View.GONE);
            }else if(data.orderStatus==2){
                tvStatus.setText("已发货");
                llBottom.setVisibility(View.GONE);
            }else if(data.orderStatus==3){
                tvStatus.setText("申请取消");
                llBottom.setVisibility(View.GONE);
            }else if(data.orderStatus==4){
                tvStatus.setText("已取消");
                llBottom.setVisibility(View.GONE);
            }else if(data.orderStatus==5){
                tvStatus.setText("已签收");
                llBottom.setVisibility(View.GONE);
            }
            if (data.items.size()>0&&data.items.get(0).product!=null&&data.items.get(0).product.tags.size()>0){
                recyclerView.setLayoutManager(new GridLayoutManager(context,data.items.get(0).product.tags.size()));
                recyclerViewAdapter=new RecyclerViewAdapter<MyOrderListRes.DataBean.ListBean.ItemsBean.ProductBean.TagsBean >
                        (context,data.items.get(0).product.tags,R.layout.item_shop_tag) {
                    @Override
                    public void convert(RecyclerViewHolder holder, MyOrderListRes.DataBean.ListBean.ItemsBean.ProductBean.TagsBean item,int pos) {
                        holder.setText(R.id.item_tv_tag,item.tag.tagName);
                    }
                };
                recyclerView.setAdapter(recyclerViewAdapter);
            }
            Glide.with(getContext())
                    .load(data.items.get(0).product.productIcon)
                    .apply(YxbContent.options)
                    .into(img);
//            tvType.setText(data.items.get(0).orderId);
            tvNum.setText("x"+data.items.get(0).amount);
            tvInfo.setText(data.expendIntegral+"积分+"+data.expendMoney+"元");


            btnPay.setTag(position);
            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewClickListener.onBtnPay((Integer) btnPay.getTag());
                }
            });
        }
    }

    public void setOnViewClickListener(onViewClickListener onViewClickListener){
        this.onViewClickListener=onViewClickListener;
    }


    public interface onViewClickListener{
        void onBtnPay(int postion);
    }



}
