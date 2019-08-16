package com.ecarxclub.app.ui.mine.coupon;

import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.mine.MyCouponRes;
import com.ecarxclub.app.presenter.mine.coupon.BaseCouponPresenter;
import com.ecarxclub.app.presenter.mine.coupon.BaseCouponView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.CommonUtils;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/4/24 16:10.
 * 卡券详情  我的
 */
public class CouponDetailsActivity extends BaseActivityP<BaseCouponPresenter> implements BaseCouponView{
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_cpd_type)
    TextView tvType;
    @BindView(R.id.tv_cpd_tag)
    TextView tvTag;
    @BindView(R.id.tv_cpd_title)
    TextView tvTitle;
    @BindView(R.id.tv_cpd_name)
    TextView tvName;
    @BindView(R.id.tv_cpd_time)
    TextView tvTime;
    @BindView(R.id.tv_cpd_address)
    TextView tvAddress;
    @BindView(R.id.tv_cpd_people)
    TextView tvPeople;
    @BindView(R.id.tv_cpd_ordernum)
    TextView tvOrderNum;
    @BindView(R.id.tv_cpd_gettime)
    TextView tvGetTime;
    @BindView(R.id.tv_cpd_phone)
    TextView tvPhone;
    @BindView(R.id.tv_cpd_status)
    TextView tvStatus;

    private MyCouponRes.DataBean.ListBean couponBean;
    private String mCouponId;//卡券id

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_coupondetails;
    }
    @Override
    public void initView() {
        tvToolbarTitle.setText("卡券详情");
    }

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        couponBean= (MyCouponRes.DataBean.ListBean) getIntent().getExtras().getSerializable("couponInfo");
        mCouponId=getIntent().getExtras().getString("couponid");
//        presenter.onCouponDetails(mCouponId);
        if(couponBean!=null){
            if(couponBean.coupon.type!=null){
                tvType.setText(couponBean.coupon.type.typeName);
            }
            if(couponBean.coupon.targs!=null){
                tvTag.setText(couponBean.coupon.targs.tagName);
            }
            tvTitle.setText(couponBean.coupon.seller.sellerName);
            tvName.setText(couponBean.coupon.couponName+couponBean.coupon.couponTitle);
            tvTime.setText("限制时间："+couponBean.coupon.limitTimeStart+"-"+couponBean.coupon.limitTimeEnd);
            tvAddress.setText("限制地点："+couponBean.coupon.limitRegion);
            tvPeople.setText("限制人数："+couponBean.coupon.limitRemark);

            tvOrderNum.setText(couponBean.couponId);
            tvGetTime.setText(couponBean.createTime);
            tvPhone.setText(CommonUtils.hidePhone(couponBean.receiveMobile));
            if(couponBean.couponStatus==1){
                tvStatus.setText("待使用");

            }else if(couponBean.couponStatus==2){
                tvStatus.setText("已使用");
            }else {
                tvStatus.setText("已过期");
            }
        }
    }

    @Override
    public void onCouponDetails(BaseMsgRes baseMsgRes) {

    }

    @Override
    protected BaseCouponPresenter createPresenter() {
        return new BaseCouponPresenter(this);
    }

}
