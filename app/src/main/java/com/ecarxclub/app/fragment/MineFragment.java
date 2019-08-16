package com.ecarxclub.app.fragment;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.base.BindEventBus;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.UserInfoNewRes;
import com.ecarxclub.app.model.UserInfoRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.presenter.mine.BaseMinePresenter;
import com.ecarxclub.app.presenter.mine.BaseMineView;
import com.ecarxclub.app.ui.home.MyCarActivity;
import com.ecarxclub.app.ui.mine.AboutUsActivity;
import com.ecarxclub.app.ui.mine.AccountSetActivity;
import com.ecarxclub.app.ui.mine.BindWechatSetActivity;
import com.ecarxclub.app.ui.mine.EditUserInfoActivity;
import com.ecarxclub.app.ui.mine.address.MyAddressActivity;
import com.ecarxclub.app.ui.mine.coupon.MyCouponActivity;
import com.ecarxclub.app.ui.mine.message.MyMessageActivity;
import com.ecarxclub.app.ui.mine.order.MyOrderActivity;
import com.ecarxclub.app.utils.CommonUtils;
import com.ecarxclub.app.utils.SharedPreferencesUtils;
import com.ecarxclub.app.utils.ToastUtils;
import com.andview.refreshview.XRefreshView;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

@BindEventBus
public class MineFragment extends BaseFragment<BaseMinePresenter> implements BaseMineView {
    @BindView(R.id.ll_mine_myaddress)
    LinearLayout llMyAddress;
    @BindView(R.id.ll_mine_accountset)
    LinearLayout llAccountSet;
    @BindView(R.id.ll_mine_callphone)
    LinearLayout llCallPhone;

    @BindView(R.id.ll_mine_aboutus)
    LinearLayout llAboutUs;
    @BindView(R.id.tv_mine_mycar)
    TextView tvCar;
    @BindView(R.id.tv_mine_order)
    TextView tvOrder;
    @BindView(R.id.tv_mine_coupon)
    TextView tvCoupon;
    @BindView(R.id.xRefreshview)
    XRefreshView xRefreshView;
    @BindView(R.id.ll_mine_userinfo)
    LinearLayout llUserInfo;//用户信息
    @BindView(R.id.tv_mine_msgnum)
    TextView tvMsgNum;//用户消息
    @BindView(R.id.rl_home_message)
    RelativeLayout rlMsg;
    @BindView(R.id.img_mine_head)
    ImageView imgHead;
    @BindView(R.id.tv_mine_leveltxt)
    TextView tvLevelTxt;
    @BindView(R.id.tv_mine_integral)
    TextView tvIntegral;
    @BindView(R.id.pb_mine_level)
    ProgressBar progressBarLevel;
    @BindView(R.id.img_mine_level)
    ImageView imgLevel;
    @BindView(R.id.tv_mine_phone)
    TextView tvPhoneNum;
    @BindView(R.id.tv_mine_loseintegral)
    TextView tvLoseIntegral;
    @BindView(R.id.tv_mine_nowintegral)
    TextView tvMyIntegral;
    @BindView(R.id.tv_mine_levelname)
    TextView tvLevelName;
    @BindView(R.id.img_mine_bglevel)
    ImageView imgBgLevel;
    @BindView(R.id.ll_mine_bind)
    LinearLayout llBindWechat;


    //刷新
    public static long lastRefreshTime;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tabmain_mine;
    }

    @Override
    public void initData() {
        if (YxbContent.getLoginToken()) {
//            onUserScore();
            onGetUserInfo();
            onGetUserIntegral();
        }
    }

    @Override
    public void initView() {
        initXRefreshView();
    }

    private void onUserScore() {
        presenter.getUserScore();
    }

    @Override
    public void initClick() {
        //我的地址
        RxView.clicks(llMyAddress).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startIntent(getActivity(), MyAddressActivity.class);
//                showtoast("测试bugly结果" + 2/0);
//                CrashReport.testJavaCrash();
//                if (ss.equals("2")){
//                }
            }
        });

        //我的消息
        RxView.clicks(rlMsg).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startIntent(getActivity(), MyMessageActivity.class);
            }
        });
        //绑定微信设置
        RxView.clicks(llBindWechat).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startIntent(getActivity(), BindWechatSetActivity.class);
            }
        });
        //客服电话
        RxView.clicks(llCallPhone).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                YxbContent.getCallPhone(getActivity());
            }
        });
        //我的订单
        RxView.clicks(tvOrder).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startIntent(getActivity(), MyOrderActivity.class);
            }
        });
        //我的车辆
        RxView.clicks(tvCar).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (YxbApplication.userInfoRes != null && YxbApplication.userInfoRes.data != null && YxbApplication.userInfoRes.data.mainCarId != null) {
                    startIntent(getActivity(), MyCarActivity.class);//CouponDetailsActivity
                } else {
                    YxbContent.goBindCarAct(getActivity());
                }
            }
        });
        //我的卡券
        RxView.clicks(tvCoupon).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startIntent(getActivity(), MyCouponActivity.class);
            }
        });

        //账户设置
        RxView.clicks(llAccountSet).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startIntent(getActivity(), AccountSetActivity.class);
            }
        });
        //关于我们
        RxView.clicks(llAboutUs).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startIntent(getActivity(), AboutUsActivity.class);
            }
        });

        //编辑用户信息
        RxView.clicks(llUserInfo).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startIntent(getActivity(), EditUserInfoActivity.class);
            }
        });
    }


    public void initXRefreshView() {
        // 设置是否可以下拉刷新
        xRefreshView.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        xRefreshView.setPullLoadEnable(false);
        xRefreshView.setPinnedTime(1000);
        // 设置上次刷新的时间
        xRefreshView.restoreLastRefreshTime(lastRefreshTime);
        // 设置时候可以自动刷新
        xRefreshView.setAutoRefresh(false);
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {//刷新
                initData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {//加载跟多
            }
        });
    }

    //获取用户信息
    public void onGetUserInfo() {
        presenter.onGetUserInfo();
    }

    //获取用户信息新
    private void onGetUserIntegral(){
        presenter.onGetUserNewInfo();
    }

    @Override
    protected BaseMinePresenter createPresenter() {
        return new BaseMinePresenter(this);
    }


    //获取个人积分
    @Override
    public void onGetUserScore(BaseMsgRes baseMsgRes) {
        xRefreshView.stopRefresh();
//        if (baseMsgRes.isSuccess()) {
//            tvContent.setText("积分：" + (int) Float.parseFloat(baseMsgRes.getData().toString()));
//        }
    }

    //用户信息
    @Override
    public void onUserInfo(UserInfoRes baseMsgRes) {
        xRefreshView.stopRefresh();
        if (baseMsgRes.success) {
            YxbApplication.userInfoRes = baseMsgRes;
//            tvName.setText(baseMsgRes.data.nickName);
            Glide.with(getActivity()).load(baseMsgRes.data.icon).apply(YxbContent.optionsRound).into(imgHead);
            if (baseMsgRes.data.mobile!=null){
                SharedPreferencesUtils.setParam(getActivity(), UrlOkHttpUtils.YXB_USER_MOBILE, baseMsgRes.data.mobile);
            }
            tvMsgNum.setVisibility(View.VISIBLE);
            if (baseMsgRes.data.notReadCount > 9) {
                tvMsgNum.setText("9+");
            } else if (baseMsgRes.data.notReadCount > 0) {
                tvMsgNum.setText(baseMsgRes.data.notReadCount + "");
            } else {
                tvMsgNum.setText("0");
                tvMsgNum.setVisibility(View.GONE);
            }
            if (baseMsgRes.data.nickName!=null&&baseMsgRes.data.nickName.length()>0){
                tvPhoneNum.setText(baseMsgRes.data.nickName);
            }else {
                tvPhoneNum.setText(CommonUtils.hidePhone(baseMsgRes.data.mobile));
            }
        }
    }

    @Override
    public void onUserNewInfo(UserInfoNewRes baseMsgRes) {
        if (baseMsgRes.success) {
            if (baseMsgRes.data.level!=null){
                if ("1".equals(baseMsgRes.data.level.memberIdentity)){
                    imgLevel.setBackgroundResource(R.mipmap.pic_member1);
                    imgBgLevel.setBackgroundResource(R.mipmap.pic_member01);
                }else if ("2".equals(baseMsgRes.data.level.memberIdentity)){
                    imgLevel.setBackgroundResource(R.mipmap.pic_member2);
                    imgBgLevel.setBackgroundResource(R.mipmap.pic_member02);
                }else if ("3".equals(baseMsgRes.data.level.memberIdentity)){
                    imgLevel.setBackgroundResource(R.mipmap.pic_member3);
                    imgBgLevel.setBackgroundResource(R.mipmap.pic_member03);
                }else if ("4".equals(baseMsgRes.data.level.memberIdentity)){
                    imgLevel.setBackgroundResource(R.mipmap.pic_member4);
                    imgBgLevel.setBackgroundResource(R.mipmap.pic_member04);
                }else if ("5".equals(baseMsgRes.data.level.memberIdentity)){
                    imgLevel.setBackgroundResource(R.mipmap.pic_member5);
                    imgBgLevel.setBackgroundResource(R.mipmap.pic_member05);
                }

//                Glide.with(getActivity()).load(baseMsgRes.data.level.levelIcon).apply(YxbContent.options_pop_tags).into(imgLevel);
//                Glide.with(getActivity()).load(baseMsgRes.data.level.backGroundPath).apply(YxbContent.options_pop_tags).into(imgBgLevel);
                progressBarLevel.setProgress((int)(((baseMsgRes.data.levelIntegral-baseMsgRes.data.level.startIntegral)
                        /(baseMsgRes.data.level.endIntegral-baseMsgRes.data.level.startIntegral))*100));
                tvLevelTxt.setText(baseMsgRes.data.level.levelRemark);
                tvIntegral.setText((int)baseMsgRes.data.levelIntegral +"/"+(int)baseMsgRes.data.level.endIntegral);
                tvLevelName.setText(baseMsgRes.data.level.memberName);
                tvMyIntegral.setText((int)baseMsgRes.data.integral+"积分");
                if (baseMsgRes.data.expireTip!=null){
                    tvLoseIntegral.setText(baseMsgRes.data.expireTip);
                }
            }
        }
    }

    @Override
    public void showError(String msg) {
        super.showError(msg);
        if (xRefreshView != null) {
            xRefreshView.stopRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusClick(EventMessage eventMessage) {
        ToastUtils.e("mineFragment _____action " + eventMessage.action, "  obj" + eventMessage.object);
        switch (eventMessage.action) {
//            case Constant.USER_SCORE://更新用户积分
//                onUserScore();
//                break;
            case Constant.USER_INFO_SUCCESS://修改用户信息
                onGetUserInfo();
                break;
            case Constant.LOGIN_SUCCESS://登录成功
                initData();
                break;
            case Constant.USER_BIND_WECHAT://绑定微信成功
                initData();
                break;
        }
    }
}
