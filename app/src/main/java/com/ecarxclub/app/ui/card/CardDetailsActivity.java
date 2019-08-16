package com.ecarxclub.app.ui.card;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.card.CardDetailsRes;
import com.ecarxclub.app.presenter.card.CardDetailsPresenter;
import com.ecarxclub.app.presenter.card.CardDetailsView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.ui.forgetpwd.RetrievePwdActivity;
import com.ecarxclub.app.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/4/30 16:32.
 * 商品详情
 */
public class CardDetailsActivity extends BaseActivityP<CardDetailsPresenter> implements CardDetailsView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.img_card_cd_header)
    ImageView imgHeader;
    @BindView(R.id.tv_card_cd_name)
    TextView tvName;
    @BindView(R.id.tv_card_cd_title)
    TextView tvTitle;
    @BindView(R.id.tv_card_cd_type)
    TextView tvType;
    @BindView(R.id.tv_card_cd_tag)
    TextView tvTag;

    @BindView(R.id.tv_card_cd_time)
    TextView tvTime;
    @BindView(R.id.tv_card_cd_address)
    TextView tvAddress;
    @BindView(R.id.tv_card_cd_people)
    TextView tvPeople;
    @BindView(R.id.tv_card_cd_info)
    TextView tvRemark;
    @BindView(R.id.tv_card_cd_zd)
    TextView tvZdPhone;//指定手机号码
    @BindView(R.id.tv_card_cd_mine)
    TextView tvMinePhone;//当前手机号
    @BindView(R.id.ll_cd_phone)
    LinearLayout llPhone;
    @BindView(R.id.tv_cd_null)
    TextView tvNoCard;

//    private CardListRes.DataBean.ListBean listBean;
    private CardDetailsRes.DataBean mCardRes;
    private String mCardId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_coupondetails;
    }

    @Override
    public void initView() {
        tvToolbarTitle.setText("商品详情");
    }

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
        //指定手机号领取
        RxView.clicks(tvZdPhone).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (YxbContent.getLoginToken()){
                    if (YxbApplication.userInfoRes!=null&&YxbApplication.userInfoRes.data!=null&&YxbApplication.userInfoRes.data.mainCarId!=null) {//是否绑定车辆
                        if (mCardRes.couponStock>0){
                            Bundle bundle=new Bundle();
                            bundle.putString("mflag","2");
                            bundle.putString("cardId",mCardRes.id);
                            startIntent(CardDetailsActivity.this, RetrievePwdActivity.class,bundle);
                        }else {
                            ToastUtils.showTextShort("库存不足");
                        }
                    }else {
                        YxbContent.goBindCarAct(CardDetailsActivity.this);
                    }
                }else {
                    YxbContent.goLoginAct(CardDetailsActivity.this);
                }
            }
        });
        //自己手机号领取
        RxView.clicks(tvMinePhone).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (YxbContent.getLoginToken()){
                    if (YxbApplication.userInfoRes!=null&&YxbApplication.userInfoRes.data!=null&&YxbApplication.userInfoRes.data.mainCarId!=null){//是否绑定车辆
                        if (mCardRes.couponStock>0){
                            Map<String ,String> map=new HashMap<>();
                            map.put("couponId",mCardId);
                            presenter.getCoupon(map);
                        }else {
                            ToastUtils.showTextShort("库存不足");
                        }
                    }else {
                        YxbContent.goBindCarAct(CardDetailsActivity.this);
                    }
                }else {
                    YxbContent.goLoginAct(CardDetailsActivity.this);
                }
            }
        });
    }

    @Override
    public void initData() {
//        listBean = (CardListRes.DataBean.ListBean) getIntent().getExtras().getSerializable("cardModel");
        mCardId=getIntent().getExtras().getString("cardid");
        presenter.onCardDetails(mCardId);
//        if (listBean != null) {
//            tvName.setText(listBean.couponName);
//            tvTitle.setText(listBean.couponTitle);
//            tvType.setText(listBean.type.typeName);
//            tvTag.setText(listBean.targs.tagName);
//            tvTime.setText(listBean.limitTimeStart+"-"+listBean.limitTimeEnd);
//            tvAddress.setText(listBean.limitRegion);
//            tvPeople.setText(listBean.limitRemark);
//            tvRemark.setText(Html.fromHtml(listBean.remark));
//            Glide.with(context)
//                    .load(listBean.seller.sellerLogo)
//                    .apply(YxbContent.options)
//                    .into(imgHeader);
//        }
    }

    @Override
    public void onCardDetails(CardDetailsRes cardDetailsRes) {
        if(cardDetailsRes.success){
            mCardRes=cardDetailsRes.data;
            tvName.setText(mCardRes.couponName);
            tvTitle.setText(mCardRes.couponTitle);
            tvType.setText(mCardRes.type.typeName);
            tvTag.setText(mCardRes.targs.tagName);
            String mStartTime="";
            if (mCardRes.limitTimeStart!=null&&mCardRes.limitTimeStart.contains(" ")){
                mStartTime=mCardRes.limitTimeStart.substring(0,mCardRes.limitTimeStart.indexOf(" "));
                if (mStartTime.length()==10){
                    mStartTime=mStartTime.substring(0,4)+"年"
                            +mStartTime.substring(5,7)+"月"
                            +mStartTime.substring(8,10)+"日";
                }
            }
            String mEndTime="";
            if (mCardRes.limitTimeEnd!=null&&mCardRes.limitTimeEnd.contains(" ")){
                mEndTime=mCardRes.limitTimeEnd.substring(0,mCardRes.limitTimeEnd.indexOf(" "));
                if (mEndTime.length()==10){
                    mEndTime=mEndTime.substring(0,4)+"年"
                            +mEndTime.substring(5,7)+"月"
                            +mEndTime.substring(8,10)+"日";
                }
            }
            tvTime.setText(mStartTime+"-"+mEndTime);
            tvAddress.setText(mCardRes.limitRegion);
            tvPeople.setText(mCardRes.limitRemark);
            tvRemark.setText(Html.fromHtml(mCardRes.remark));
            Glide.with(context)
                    .load(mCardRes.seller.sellerLogo)
                    .apply(YxbContent.options)
                    .into(imgHeader);
            if (mCardRes.couponStock>0){
                llPhone.setVisibility(View.VISIBLE);
                tvNoCard.setVisibility(View.GONE);
            }else {
                llPhone.setVisibility(View.GONE);
                tvNoCard.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onGetCoupone(BaseMsgRes baseMsgRes) {
        ToastUtils.showTextShort(baseMsgRes.getMsg());
    }

    @Override
    protected CardDetailsPresenter createPresenter() {
        return new CardDetailsPresenter(this);
    }


}
