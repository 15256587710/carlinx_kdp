package com.ecarxclub.app.utils.card;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.model.home.car.BindCarRes;
import com.ecarxclub.app.ui.home.ImprovingCarActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<BindCarRes.DataBean> mData;
    private float mBaseElevation;
    private Context context;

    public CardPagerAdapter(Context context) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        this.context=context;
    }

    public void addCardItem(List<BindCarRes.DataBean> mData) {
        this.mData=mData;
        for (int i=0;i<mData.size();i++){
            mViews.add(null);
        }
//        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_mycar_listvp, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cv_mycarlist);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }
        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ImprovingCarActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("carid",mData.get(position).id);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(BindCarRes.DataBean item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.item_mycar_title);
        TextView contentTextView = (TextView) view.findViewById(R.id.item_mycar_content);
        TextView tvTime=view.findViewById(R.id.item_mycar_time);
        ImageView imgCar=view.findViewById(R.id.item_img_car);
        Glide.with(context).load(item.carImage).apply(YxbContent.options_car).into(imgCar);
        if (item.license!=null&&item.license.length()>0){
            contentTextView.setText(item.license);
        }else {
            contentTextView.setText("未获取到车牌");
        }
        if (item.carStyle!=null&&item.carStyle.length()>0){
            titleTextView.setText(item.carStyle);
        }else {
            titleTextView.setText("未获取到车型");
        }
        if (item.buyTime!=null&&item.buyTime.length()>0){
            tvTime.setText(item.buyTime+" 购入");
        }else {
            tvTime.setText("未知时间 购入");
        }
    }

}
