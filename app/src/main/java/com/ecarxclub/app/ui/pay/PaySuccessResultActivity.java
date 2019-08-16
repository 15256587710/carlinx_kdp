package com.ecarxclub.app.ui.pay;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.mine.ConfirmRecriceRes;
import com.ecarxclub.app.presenter.pay.PaySuccessResultPresenter;
import com.ecarxclub.app.presenter.pay.PaySuccessResultView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.ui.MainActivity;
import com.ecarxclub.app.ui.WebViewActivity;
import com.ecarxclub.app.ui.card.CardDetailsActivity;
import com.ecarxclub.app.ui.mine.order.MyOrderActivity;
import com.ecarxclub.app.ui.shop.IntegralCenterActivity;
import com.ecarxclub.app.ui.shop.SignInActivity;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.SharedPreferencesUtils;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.utils.banner.demo.NetworkImageHolderView;
import com.ecarxclub.app.utils.banner.holder.CBViewHolderCreator;
import com.ecarxclub.app.utils.banner.listener.OnItemClickListener;
import com.ecarxclub.app.utils.banner.view.ConvenientBanner;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/5/7 10:35.
 * 支付成功跳转页
 */
public class PaySuccessResultActivity extends BaseActivityP<PaySuccessResultPresenter> implements PaySuccessResultView, OnItemClickListener {
    @BindView(R.id.tv_pays_home)
    TextView tvBackHome;
    @BindView(R.id.tv_pays_look)
    TextView tvLookOrder;
    @BindView(R.id.tv_pays_score)
    TextView tvScroe;
    @BindView(R.id.tv_od_info)
    TextView tvInfo;

    @BindView(R.id.cb_convenientbanner)
    ConvenientBanner convenientBanner;

    private String mMoney = "", mScroe = "";//mMoney!=null  购买成功    确认收货
    private ConfirmRecriceRes confirmRecriceRes;
    private List<String> listImg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success;
    }

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        if (getIntent().getExtras() != null) {
            mMoney = getIntent().getExtras().getString("money");
            if (mMoney != null && mMoney.length() > 0) {
                mScroe = getIntent().getExtras().getString("score");
                tvScroe.setText(mScroe + "积分+" + mMoney + "元");
            } else {
                confirmRecriceRes = (ConfirmRecriceRes) getIntent().getExtras().getSerializable("ConfrimModel");
                ToastUtils.i("size " + confirmRecriceRes.data.banners.size(), " 积分 " + confirmRecriceRes.data.integral);
                if (confirmRecriceRes.data.integral > 0) {
                    tvScroe.setText("恭喜你获得" + confirmRecriceRes.data.integral + "积分");
                } else {
                    tvScroe.setVisibility(View.GONE);
                }
                tvInfo.setText("交易成功");
                tvBackHome.setText("更多积分");
                if (confirmRecriceRes.data.banners != null && confirmRecriceRes.data.banners.size() > 0) {
                    listImg = new ArrayList<>();
                    for (int i = 0; i < confirmRecriceRes.data.banners.size(); i++) {
                        listImg.add(confirmRecriceRes.data.banners.get(i).imgPath);
                    }
                    setPages(listImg);
                }
            }
        }
    }

    private void setPages(List<String> listImg) {
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, listImg)
                .setPageIndicator(new int[]{R.mipmap.indicator_select, R.mipmap.indicator_unselect})
                .setOnItemClickListener(this)
        ;
    }

    @Override
    public void initClick() {
        RxView.clicks(tvBackHome).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (mMoney != null && mMoney.length() > 0) {
                    startIntent(PaySuccessResultActivity.this, MainActivity.class);
                } else {
                    startIntent(PaySuccessResultActivity.this, IntegralCenterActivity.class);
                }
            }
        });
        RxView.clicks(tvLookOrder).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startIntent(PaySuccessResultActivity.this, MyOrderActivity.class);
            }
        });
    }

    @Override
    public void onPaySuccess(BaseMsgRes baseMsgRes) {

    }

    @Override
    protected PaySuccessResultPresenter createPresenter() {
        return new PaySuccessResultPresenter(this);
    }


    @Override
    public void onItemClick(int position) {
        if ("1".equals(confirmRecriceRes.data.banners.get(position).jump)) {//不跳转

        } else if ("2".equals(confirmRecriceRes.data.banners.get(position).jump)) {//H5
            Bundle bundle = new Bundle();
            bundle.putString("web_url", confirmRecriceRes.data.banners.get(position).navPath);
            startIntent(PaySuccessResultActivity.this, WebViewActivity.class, bundle);
        } else if ("3".equals(confirmRecriceRes.data.banners.get(position).jump)) {//卡券详情
            Bundle bundle = new Bundle();
            bundle.putString("cardid", confirmRecriceRes.data.banners.get(position).itemId + "");
            startIntent(PaySuccessResultActivity.this, CardDetailsActivity.class, bundle);
        } else if ("4".equals(confirmRecriceRes.data.banners.get(position).jump)) {//签到
            goSignInAct();
        } else if ("5".equals(confirmRecriceRes.data.banners.get(position).jump)) {//积分中心
            startIntent(PaySuccessResultActivity.this, IntegralCenterActivity.class);
        } else if ("6".equals(confirmRecriceRes.data.banners.get(position).jump)) {//游戏
            goWebGame(confirmRecriceRes.data.banners.get(position).navPath);
        } else if ("7".equals(confirmRecriceRes.data.banners.get(position).jump)) {//排行榜
        } else if ("8".equals(confirmRecriceRes.data.banners.get(position).jump)) {//绑定车辆
            if (YxbContent.getLoginToken()) {
                YxbContent.goBindCarAct(PaySuccessResultActivity.this);
            } else {
                YxbContent.goLoginAct(PaySuccessResultActivity.this);
            }
        } else if ("9".equals(confirmRecriceRes.data.banners.get(position).jump)) {//会员权益
        }
    }

    private void goWebGame(String goUrl) {
        Bundle bundle = new Bundle();//"http://192.168.0.107:8080/#/?token="
        bundle.putString("web_url", goUrl + "?token=" + SharedPreferencesUtils.getParam(YxbApplication.getContext(), UrlOkHttpUtils.YXB_USER_TOKEN, "").toString() + "&wrap=android");
        bundle.putString("welcome", "integral");
        startIntent(PaySuccessResultActivity.this, WebViewActivity.class, bundle);
    }

    private void goSignInAct() {//签到
        if (YxbContent.getIsBindCar()) {//是否绑定车辆
            startIntent(PaySuccessResultActivity.this, SignInActivity.class);
        } else {
            YxbContent.goBindCarAct(PaySuccessResultActivity.this);
        }
    }


    @Override
    public void onSelectChoose(int postion) {

    }
}
