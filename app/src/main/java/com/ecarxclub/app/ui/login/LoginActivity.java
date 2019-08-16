package com.ecarxclub.app.ui.login;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.login.LoginRes;
import com.ecarxclub.app.presenter.login.LoginPresenter;
import com.ecarxclub.app.presenter.login.LoginView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.ui.MainActivity;
import com.ecarxclub.app.ui.login.kdp.ForgetPwdActivity;
import com.ecarxclub.app.utils.CommonUtils;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.SharedPreferencesUtils;
import com.ecarxclub.app.utils.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import rx.functions.Action1;

import static com.ecarxclub.app.common.YxbApplication.WX_API;

/**
 * Created by cwp
 * on 2019/4/17 16:01.
 */
public class LoginActivity extends BaseActivityP<LoginPresenter> implements LoginView {
    @BindView(R.id.tv_login_goregister)
    TextView tvRegister;
    @BindView(R.id.et_login_phone)
    EditText etPhone;
    @BindView(R.id.et_login_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_login_forgetpwd)
    TextView tvForgetPwd;
    @BindView(R.id.img_login_head)
    ImageView imgHead;
    @BindView(R.id.img_login_lookpwd)
    ImageView imgLookPwd;//看密码

    private String mStrPhone, mStrPwd;
    private long exitTime = 0;
    private String mMobile;
    private String loginOut = "";// 1设置退出登录
    private boolean isPhoneShow, isPwdShow, isEtPwdShow;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarDarkMode(this);
        if (getIntent().getExtras() != null) {
            loginOut = getIntent().getExtras().getString("unLogin");
        }
        mMobile = SharedPreferencesUtils.getParam(LoginActivity.this, UrlOkHttpUtils.YXB_USER_MOBILE, "").toString();
        if (mMobile != null && mMobile.length() > 0) {
            etPhone.setText(mMobile);
            etPhone.setSelection(mMobile.length());
        }
        //极光id 登录时候需要
        String push_userid = JPushInterface.getRegistrationID(getApplicationContext());
//        ToastUtils.i("极光id ", "  " + push_userid);//100d855909364952e60
        SharedPreferencesUtils.setParam(LoginActivity.this, UrlOkHttpUtils.YXB_PUSH_ID, push_userid);

        mSwipeBackLayout.setSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
            }

            @Override
            public void onScrollOverThreshold() {
                goBack();
            }
        });
    }

    @Override
    public void initClick() {
        //立即注册
        RxView.clicks(tvRegister).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startIntent(LoginActivity.this, RegisterActivity.class);
            }
        });
//        登录
        RxView.clicks(btnLogin).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mStrPhone = etPhone.getText().toString();
                mStrPwd = etPwd.getText().toString();
                if (CommonUtils.isPhoneNumber(mStrPhone)) {
                    if (mStrPwd.length() > 5 && mStrPwd.length() < 12) {
                        presenter.login(mStrPhone, mStrPwd);
                    } else {
                        showtoast("请输入正确的密码格式");
                    }
                } else {
                    showtoast("请输入正确的手机号码");
                }
            }
        });

        //忘记密码
        RxView.clicks(tvForgetPwd).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
//                Bundle bundle=new Bundle();
//                bundle.putString("mflag","1");
//                startIntent(LoginActivity.this, RetrievePwdActivity.class,bundle);

                startIntent(LoginActivity.this, ForgetPwdActivity.class);
            }
        });
        //密码显示隐藏
        RxView.clicks(imgLookPwd).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (isEtPwdShow) {//显示
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imgLookPwd.setBackgroundResource(R.mipmap.pic_login_lookpwd_1);
                    isEtPwdShow = false;
                } else {
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imgLookPwd.setBackgroundResource(R.mipmap.pic_login_lookpwd_2);
                    isEtPwdShow = true;
                }
                etPwd.setSelection(etPwd.getText().toString().length());
            }
        });
        //
        RxView.clicks(imgHead).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                // 是否安装微信
                if (WX_API.isWXAppInstalled()) {// 已安装
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wchat_login";//signIn
                    WX_API.sendReq(req);
                } else { // 未安装
                    ToastUtils.showTextShort("无法使用该功能,请安装微信后重试");
                }
            }
        });
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    isPhoneShow = false;
                } else {
                    isPhoneShow = true;
                }
                isBtnShow();
            }
        });
        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (etPhone.getText().toString().isEmpty()){
                    isPhoneShow = false;
                }else {
                    isPhoneShow = true;
                }
                if (s.toString().isEmpty()) {
                    isPwdShow = false;
                } else {
                    isPwdShow = true;
                }
                isBtnShow();
            }
        });
    }

    private void isBtnShow() {
        if (isPhoneShow && isPwdShow) {
            btnLogin.setBackgroundResource(R.mipmap.pic_login_btn_t);
        } else {
            btnLogin.setBackgroundResource(R.mipmap.pic_login_btn);
        }
    }

    @Override
    public void onLoginResult(LoginRes loginRes) {
        if (loginRes.isSuccess()) {
            SharedPreferencesUtils.setParam(LoginActivity.this, UrlOkHttpUtils.YXB_USER_TOKEN, loginRes.getData().getToken());
            EventBus.getDefault().post(new EventMessage(Constant.LOGIN_SUCCESS, Constant.LOGIN_SUCCESS_TXT));
            if (loginOut.length() > 0) {
                startIntent(LoginActivity.this, MainActivity.class);
            }
            finish();
        }
    }

    private void goBack() {
        if (loginOut.length() > 0) {
            startIntent(LoginActivity.this, MainActivity.class);
        }
        finish();
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }


    /**
     * 再按一次推出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
