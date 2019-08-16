package com.ecarxclub.app.ui.login.kdp;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.presenter.kdp.forgetpwd.ForgetPwdPresenter;
import com.ecarxclub.app.presenter.kdp.forgetpwd.ForgetPwdView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.CommonUtils;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/8/14 9:30.
 * 忘记密码  kdp
 */
public class ForgetPwdActivity extends BaseActivityP<ForgetPwdPresenter> implements ForgetPwdView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.et_forget_phone)
    EditText etPhone;
    @BindView(R.id.et_forget_code)
    EditText etCode;
    @BindView(R.id.btn_forget_code)
    TextView tvCode;
    @BindView(R.id.btn_forget_next)
    Button btnNext;

    private String mStrPhone,mStrCode;
    private boolean isPhoneShow,isCodeShow;
    private CountDownTimer timer;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarTitle.setText("忘记密码");
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
        //验证码
        RxView.clicks(tvCode).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mStrPhone=etPhone.getText().toString();
                if(CommonUtils.isPhoneNumber(mStrPhone)){
                    onTimer(60);
                }else {
                    showtoast("请输入正确的手机号码");
                }
            }
        });
        //下一步
        RxView.clicks(btnNext).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mStrPhone=etPhone.getText().toString();
                mStrCode=etCode.getText().toString();
                if(CommonUtils.isPhoneNumber(mStrPhone)){
                    if (mStrCode.length()>=4&&mStrCode.length()<=6){
                        startIntent(ForgetPwdActivity.this,ConfirmForPwdActivity.class);
                    }else {
                        showtoast("请输入正确的验证码");
                    }
                }else {
                    showtoast("请输入正确的手机号码");
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
                if (s.toString().isEmpty()){
                    isPhoneShow=false;
                }else {
                    isPhoneShow=true;
                }
                isBtnShow();
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
                    isCodeShow=false;
                }else {
                    isCodeShow=true;
                }
                isBtnShow();
            }
        });
    }

    private void isBtnShow(){
        if (isCodeShow&&isPhoneShow){
            btnNext.setBackgroundResource(R.mipmap.pic_login_btn_t);
        }else {
            btnNext.setBackgroundResource(R.mipmap.pic_login_btn);
        }
    }

    private void onTimer(int time){
        /** 倒计时60秒，一次1秒 */
        timer = new CountDownTimer((time==0?60:time)*1000+200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                ToastUtils.i("time   ",millisUntilFinished / 1000+"_____"+millisUntilFinished);
                tvCode.setText( "重新发送("+millisUntilFinished / 1000 +")");
                tvCode.setEnabled(false);
            }
            @Override
            public void onFinish() {
                tvCode.setText("获取验证码");
                tvCode.setEnabled(true);
            }
        }.start();
    }
    @Override
    protected ForgetPwdPresenter createPresenter() {
        return new ForgetPwdPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null){
            timer.cancel();
        }
    }
}
