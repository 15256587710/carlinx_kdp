package com.ecarxclub.app.ui.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.login.LoginRes;
import com.ecarxclub.app.model.login.RegisterRes;
import com.ecarxclub.app.presenter.login.RegisterPresenter;
import com.ecarxclub.app.presenter.login.RegisterView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.ui.MainActivity;
import com.ecarxclub.app.ui.WebViewActivity;
import com.ecarxclub.app.utils.CommonUtils;
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
 * on 2019/4/17 16:01.
 */
public class RegisterActivity extends BaseActivityP<RegisterPresenter> implements RegisterView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.et_register_phone)
    EditText etPhone;
    @BindView(R.id.et_register_code)
    EditText etCode;
    @BindView(R.id.et_register_pwd)
    EditText etPwd;
    @BindView(R.id.btn_register_code)
    TextView tvGetCode;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.cb_regist_agree)
    CheckBox checkBox;
    @BindView(R.id.img_regist_lookpwd)
    ImageView imgLookPwd;
//    @BindView(R.id.ll_regist_info)
//    LinearLayout llInfo;

    private String mPhoneNumber,mPwd,mCode;
    private CountDownTimer timer;
    private boolean isPhoneExit,isCodeExit,isPwdExit,isPwdShow;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
//        StatusBarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));
        QMUIStatusBarUtil.setStatusBarLightMode(this);//setStatusBarDarkMode  setStatusBarLightMode
        tvToolbarTitle.setText("注册登录");
    }

    @Override
    public void initData() {

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
        RxView.clicks(tvGetCode).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mPhoneNumber=etPhone.getText().toString();
                if(CommonUtils.isPhoneNumber(mPhoneNumber)){
//                    presenter.getCode(mPhoneNumber);
                }else {
                    showtoast("请输入正确的手机号码");
                }
            }
        });
//密码显示
        RxView.clicks(imgLookPwd).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (isPwdShow){//显示
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imgLookPwd.setBackgroundResource(R.mipmap.pic_login_lookpwd_black);
                    isPwdShow=false;
                }else {
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imgLookPwd.setBackgroundResource(R.mipmap.pic_login_pwd_look);
                    isPwdShow=true;
                }
                etPwd.setSelection(etPwd.getText().toString().length());
            }
        });
        //注册
        RxView.clicks(btnRegister).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mPhoneNumber=etPhone.getText().toString();
                mPwd=etPwd.getText().toString();
                mCode=etCode.getText().toString();
                if(CommonUtils.isPhoneNumber(mPhoneNumber)){
                    if(mCode.length()<=6&&mCode.length()>0){
                        if(mPwd.length()>5&&mPwd.length()<16){
                            if (checkBox.isChecked()){
                                //                            presenter.reGister(mPhoneNumber,mPwd,mCode);
                            }else {
                                showtoast("请同意用户协议");
                            }
                        }else {
                            showtoast("请输入正确的密码格式");
                        }
                    }else {
                        showtoast("请输入正确的验证码");
                    }
                }else {
                    showtoast("请输入正确的手机号码");
                }
            }
        });

        //协议
//        RxView.clicks(llInfo).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
//            @Override
//            public void call(Void aVoid) {
//                Bundle bundle=new Bundle();
//                //   https://app.carlinx.cn/privacy-policy.html
//                bundle.putString("web_url","https://app.carlinx.cn/UserServiceProtocol.html");
//                bundle.putString("welcome","regist");
//                startIntent(RegisterActivity.this,WebViewActivity.class,bundle);
//            }
//        });

        //返回
        RxView.clicks(checkBox).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                isShowBtn();
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
                if (s.toString().isEmpty()){
                    isPhoneExit=false;
                }else {
                    isPhoneExit=true;
                }
                isShowBtn();
            }
        });
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    isCodeExit=false;
                }else {
                    isCodeExit=true;
                }
                isShowBtn();
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
                if (s.toString().isEmpty()){
                    isPwdExit=false;
                }else {
                    isPwdExit=true;
                }
                isShowBtn();
            }
        });
    }

    //注册按钮是否显示
    private void isShowBtn(){
        if (isPhoneExit&&isCodeExit&&isPwdExit&&checkBox.isChecked()){
            btnRegister.setBackgroundResource(R.mipmap.pic_login_btn_t);
        }else {
            btnRegister.setBackgroundResource(R.mipmap.pic_login_btn);
        }
    }

    private void onTimer(int time){
        /** 倒计时60秒，一次1秒 */
        timer = new CountDownTimer((time==0?60:time)*1000+200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                ToastUtils.i("time   ",millisUntilFinished / 1000+"_____"+millisUntilFinished);
                tvGetCode.setText( "重新发送("+millisUntilFinished / 1000 +")");
                tvGetCode.setEnabled(false);
            }
            @Override
            public void onFinish() {
                tvGetCode.setText("获取验证码");
                tvGetCode.setEnabled(true);
            }
        }.start();
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }


    @Override
    public void onGetCode(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()){
            onTimer(60);
        }
        ToastUtils.showTextShort("发送成功");
    }
    @Override
    public void onRegisterResult(RegisterRes registerRes) {
        if (registerRes.isSuccess()){
            presenter.login(mPhoneNumber,mPwd);
        }
    }

    @Override
    public void onLoginResult(LoginRes loginRes) {
        if (loginRes.isSuccess()){
            SharedPreferencesUtils.setParam(RegisterActivity.this, UrlOkHttpUtils.YXB_USER_TOKEN,loginRes.getData().getToken());
            EventBus.getDefault().post(new EventMessage(Constant.LOGIN_SUCCESS,Constant.LOGIN_SUCCESS_TXT));
            startIntent(RegisterActivity.this, MainActivity.class);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null){
            timer.cancel();
        }
    }
}
