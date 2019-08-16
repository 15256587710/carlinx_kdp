package com.ecarxclub.app.ui.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.base.BindEventBus;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.login.LoginNewRes;
import com.ecarxclub.app.model.login.LoginRes;
import com.ecarxclub.app.presenter.login.newlogin.BindPhonePresenter;
import com.ecarxclub.app.presenter.login.newlogin.BindPhoneView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.CommonUtils;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/7/18 11:25.
 */
@BindEventBus
public class BindPhoneActivity extends BaseActivityP<BindPhonePresenter> implements BindPhoneView {
    @BindView(R.id.img_bindphone_back)
    ImageView imgBack;
    @BindView(R.id.btn_loginbind_getcode)
    Button btnCode;
    @BindView(R.id.et_loginbind_phone)
    EditText etPhone;

    private String mPhone="";
    private String mKey;//微信第一次登录  key
    private String loginOut;// 1设置退出登录
    @Override
    protected int getLayoutId() {
        return R.layout.activity_bindphone;
    }
    @Override
    protected BindPhonePresenter createPresenter() {
        return new BindPhonePresenter(this);
    }
    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        if (getIntent().getExtras()!=null){
            mKey=getIntent().getExtras().getString("wechat_key");
            loginOut=getIntent().getExtras().getString("unLogin");
        }
    }

    @Override
    public void initClick() {
        //返回
        RxView.clicks(imgBack).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });

        //获取验证码
        RxView.clicks(btnCode).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mPhone=etPhone.getText().toString();
                if (CommonUtils.isPhoneNumber(mPhone)){
                    presenter.getCodeP(mPhone);
                }else {
                    showtoast("请输入正确的手机号");
                }
            }
        });
    }

    @Override
    public void onGetCode(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()){
            Bundle bundle=new Bundle();
            bundle.putString("key",mKey);
            bundle.putString("phonenum",mPhone);
            bundle.putString("unLogin",loginOut);
            startIntent(BindPhoneActivity.this,LoginCodeActivity.class,bundle);
        }
    }

    @Override
    public void onWechatPhone(LoginRes loginNewRes) {

    }

    @Override
    public void onPhoneLogin(LoginRes loginNewRes) {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusLogin(EventMessage eventMessage) {
        ToastUtils.e("BindPhoneActivity " + eventMessage.action, "  obj" + eventMessage.object);
        switch (eventMessage.action) {
            case Constant.USER_FINISH_LOGINACT://登录成功  结束当前activity
                finish();
                break;
        }
    }
}
