package com.ecarxclub.app.ui.mine;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.presenter.mine.AccountSetPresenter;
import com.ecarxclub.app.presenter.mine.AccountSetView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.ui.login.LoginActivity;
import com.ecarxclub.app.ui.login.LoginNewActivity;
import com.ecarxclub.app.utils.CommonUtils;
import com.ecarxclub.app.utils.GlideCacheUtil;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.SharedPreferencesUtils;
import com.ecarxclub.app.utils.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import org.greenrobot.eventbus.EventBus;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/4/24 16:48.
 * 账户设置
 */
public class AccountSetActivity extends BaseActivityP<AccountSetPresenter> implements AccountSetView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.tv_mine_aset_logout)
    TextView tvLogOut;
    @BindView(R.id.tv_set_packageVersion)
    TextView tvVersionNum;
    @BindView(R.id.tv_set_cache)
    TextView tvCache;

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });

        //退出登录
        RxView.clicks(tvLogOut).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                presenter.onLogOut();
            }
        });


        //清除缓存
        RxView.clicks(tvCache).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
//                CommonUtils.clearAllCache(AccountSetActivity.this);
                    GlideCacheUtil.getInstance().clearImageAllCache(AccountSetActivity.this);
                    tvCache.setText("0.00MB");
                    ToastUtils.showTextShort("清除缓存成功");
//                ImageFileCache.ClearCache() ;
            }
        });

    }

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarTitle.setText("账户设置");
        // App版本
        tvVersionNum.setText("V "+CommonUtils.getAppVersionName(this));

//        try {
//            ToastUtils.i("__"+ GlideCacheUtil.getInstance().getCacheSize(AccountSetActivity.this)+" ","huancun   "
//                    +CommonUtils.getCacheSize(AccountSetActivity.this)+"===="
//                    );
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        tvCache.setText(GlideCacheUtil.getInstance().getCacheSize(AccountSetActivity.this) );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_accountsetting;
    }



    //退出登录
    @Override
    public void onLogOutResult(BaseMsgRes baseMsgRes) {
        if(baseMsgRes.isSuccess()){
            Bundle bundle=new Bundle();
            bundle.putString("unLogin","1");
            SharedPreferencesUtils.setParam(AccountSetActivity.this, UrlOkHttpUtils.YXB_USER_TOKEN,"");
            EventBus.getDefault().post(new EventMessage(Constant.LOGIN_OUT,""));
            startIntent(AccountSetActivity.this, LoginActivity.class,bundle);//LoginActivity  LoginNewActivity
        }
    }

    @Override
    protected AccountSetPresenter createPresenter() {
        return new AccountSetPresenter(this);
    }


}
