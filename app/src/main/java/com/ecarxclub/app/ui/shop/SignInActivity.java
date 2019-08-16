package com.ecarxclub.app.ui.shop;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ecarxclub.app.R;
import com.ecarxclub.app.adapter.RecyclerViewAdapter;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.shop.fgshop.ShopListRes;
import com.ecarxclub.app.model.shop.fgshop.ShopTagsRes;
import com.ecarxclub.app.model.shop.integral.SignInRes;
import com.ecarxclub.app.model.shop.integral.SignInRewordRes;
import com.ecarxclub.app.presenter.shop.SignInPresenter;
import com.ecarxclub.app.presenter.shop.SignInView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.ui.MainActivity;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.RecyclerViewHolder;
import com.ecarxclub.app.utils.ToastUtils;
import com.ecarxclub.app.utils.dialog.SignInGetDialog;
import com.ecarxclub.app.utils.popup.ShopScroePopup;
import com.jakewharton.rxbinding.view.RxView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/7/10 16:09.
 * 签到
 */
public class SignInActivity extends BaseActivityP<SignInPresenter> implements SignInView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.img_signin)
    ImageView imgSignin;
    @BindView(R.id.tv_signin)
    TextView tvSignin;

    @BindView(R.id.tv_signin_txt)
    TextView tvInfo;
    @BindView(R.id.tv_signin_day)
    TextView tvDay;
    @BindView(R.id.tv_signin_integral)
    TextView tvIntegral;
    @BindView(R.id.rcv_signin)
    RecyclerView recyclerView;
    @BindView(R.id.rfsv_signin_reward)
    RecyclerView recyclerViewReword;
    @BindView(R.id.tv_signin_shop)
    TextView tvShop;

    RecyclerViewAdapter recyclerViewAdapter;
    private List<SignInRewordRes.DataBean> mListGetSign;
    private int mGetPos=-1;
    private SignInGetDialog signInGetDialog;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_signin;
    }

    @Override
    public void initView() {
        tvToolbarTitle.setText("签到");
        QMUIStatusBarUtil.setStatusBarDarkMode(this);
        getData();
    }

    private void getData(){
        presenter.getSignInP();
        presenter.getSignInReword();
    }
    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
        //签到
        RxView.clicks(imgSignin).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                presenter.getCheckScore();//获取待领取积分 {"success":true,"msg":"success","data":[]}
            }
        });

        RxView.clicks(tvShop).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                getShopTags();
            }
        });
    }

    private void getShopTags() {//热门兑换
        presenter.getShopTag();
    }
    @Override
    protected SignInPresenter createPresenter() {
        return new SignInPresenter(this);
    }

    @Override
    public void onSignIn(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()){
            showPopup((int) Float.parseFloat(baseMsgRes.getData().toString()) + "");
        }
    }

    @Override
    public void onSignInRecode(SignInRes signInRes) {
        if (signInRes.success){
            if (signInRes.data.today){
                tvSignin.setText("");
                tvSignin.setBackgroundResource(R.mipmap.icon_success2);
                imgSignin.setEnabled(false);
            }else {
                tvSignin.setText("签到");
            }
            tvDay.setText(signInRes.data.days+"");
            tvInfo.setText(signInRes.data.tip);
            tvIntegral.setText("我的积分："+(int)signInRes.data.integral);
            if (signInRes.data.clockView!=null&&signInRes.data.clockView.size()>0){
                recyclerView.setLayoutManager(new GridLayoutManager(SignInActivity.this, signInRes.data.clockView.size()));
                recyclerView.setNestedScrollingEnabled(false);
                recyclerViewAdapter = new RecyclerViewAdapter<SignInRes.DataBean.ClockViewBean>(SignInActivity.this,
                        signInRes.data.clockView, R.layout.item_signin_recode) {
                    @Override
                    public void convert(RecyclerViewHolder holder, SignInRes.DataBean.ClockViewBean item,int pos) {
                        TextView tvIntegral=holder.getView(R.id.item_tv_signin_integral);
                        tvIntegral.setText("+"+item.integral );
                        if (item.clock){//签到
                            tvIntegral.setBackgroundResource(R.drawable.circle_shape_red_32);
                        }else {
                            tvIntegral.setBackgroundResource(R.drawable.circle_shape_gry);
                        }
                        TextView tvDay=holder.getView(R.id.item_tv_signin_day);
                        if (item.today){
                            tvDay.setText("今天");
                        }else {
                            if (item.date!=null&&item.date.length()>10){
                                String time=item.date.substring(5,10).replace("-",".");
                                tvDay.setText(time);
                            }
                        }
                    }
                };
                recyclerView.setAdapter(recyclerViewAdapter);
            }

        }
    }

    @Override
    public void onSignInReword(SignInRewordRes signInRewordRes) {
        if (signInRewordRes.success){
            if (signInRewordRes.data!=null&&signInRewordRes.data.size()>0){
                mListGetSign=signInRewordRes.data;
                recyclerViewReword.setLayoutManager(new GridLayoutManager(SignInActivity.this, signInRewordRes.data.size()));
                recyclerViewReword.setNestedScrollingEnabled(false);
                recyclerViewAdapter = new RecyclerViewAdapter<SignInRewordRes.DataBean>(SignInActivity.this,
                        signInRewordRes.data, R.layout.item_signin_reword) {
                    @Override
                    public void convert(RecyclerViewHolder holder, final SignInRewordRes.DataBean item, final int pos) {
                        holder.setText(R.id.item_tv_signin_day,item.days+"天");
                        if (item.reserveCover!=null){
                            holder.setText(R.id.item_tv_signin_title,item.awardCover+"+"+item.reserveCover);
                        }else {
                            holder.setText(R.id.item_tv_signin_title,item.awardCover);
                        }
                        holder.setImageFormUrl(R.id.item_img_signin,item.imgPath);
                        Button btnRecive=holder.getView(R.id.item_btn_signin_get);
                        if (item.hasRecive){//已领取
                            btnRecive.setEnabled(false);
                            btnRecive.setBackgroundResource(R.drawable.lay_bggry_radius4);
                            btnRecive.setTextColor(getResources().getColor(R.color.tab_n));
                            btnRecive.setText("已领取");
                        }else {
                            if (item.canReceive){//可领取
                                btnRecive.setEnabled(true);
                                btnRecive.setBackgroundResource(R.drawable.lay_bgyellow_radius4);
                                btnRecive.setTextColor(getResources().getColor(R.color.white));
                                btnRecive.setText("领取");
                            }else {
                                btnRecive.setEnabled(false);
                                btnRecive.setBackgroundResource(R.drawable.lay_bggry_radius4);
                                btnRecive.setTextColor(getResources().getColor(R.color.tab_n));
                                btnRecive.setText("领取");
                            }
                        }
                        btnRecive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mGetPos=pos;
                                presenter.getReciveReword(item.id);
                            }
                        });
                    }
                };
                recyclerViewReword.setAdapter(recyclerViewAdapter);
            }
        }
    }

    @Override
    public void onGetReword(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()){
            setUpdate();
            signInGetDialog = new SignInGetDialog(SignInActivity.this).builder();
            signInGetDialog.setData(mListGetSign.get(mGetPos));
            signInGetDialog.setCancelable(false);
            signInGetDialog.show();
        }
    }

    @Override
    public void onGetShopTag(ShopTagsRes shopTagsRes) {
        if (shopTagsRes.success){
            Bundle bundle = new Bundle();
            bundle.putInt("changeTag", 0);
            bundle.putSerializable("hotTag", shopTagsRes);
            startIntent(SignInActivity.this, HotExchangeActivity.class, bundle);
        }
    }

    private void setUpdate(){
        getData();
        EventBus.getDefault().post(new EventMessage(Constant.USER_SIGNIN,""));
    }

    public void showPopup(String num) {
        final ShopScroePopup shopScroePopup = new ShopScroePopup(SignInActivity.this);
        shopScroePopup.showPopupWindowMid();
        shopScroePopup.setData(num);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setUpdate();
                            shopScroePopup.dismiss();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
