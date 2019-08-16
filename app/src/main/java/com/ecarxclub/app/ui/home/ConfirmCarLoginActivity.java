package com.ecarxclub.app.ui.home;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.presenter.home.scan.ScanCodePresenter;
import com.ecarxclub.app.presenter.home.scan.ScanCodeView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/7/25 17:58.
 */
public class ConfirmCarLoginActivity extends BaseActivityP<ScanCodePresenter> implements ScanCodeView {

    @BindView(R.id.img_carlogin_back)
    ImageView imgBack;
    @BindView(R.id.btn_carlogin_login)
    Button btnLogin;
    @BindView(R.id.btn_carlogin_cancel)
    Button btnCancel;
    private String mScanCode;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_carlogin;
    }
    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        if (getIntent().getExtras()!=null){
            mScanCode=getIntent().getExtras().getString("scancode");
            ToastUtils.i("initView","========"+mScanCode);
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ToastUtils.i("onNewIntent","_____"+intent.getExtras().getString("scancode"));
    }

    @Override
    public void initClick() {
        RxView.clicks(imgBack).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });

        RxView.clicks(btnCancel).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });

        //确认登录
        RxView.clicks(btnLogin).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                presenter.getcarLogin(mScanCode);
            }
        });

    }
    @Override
    public void onCarLogin(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()){
            showtoast("登录成功");
        }
    }

    @Override
    protected ScanCodePresenter createPresenter() {
        return new ScanCodePresenter(this);
    }


}
