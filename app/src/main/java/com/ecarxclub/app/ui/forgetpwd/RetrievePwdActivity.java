package com.ecarxclub.app.ui.forgetpwd;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.presenter.RetrievePwd.ForgetPwdPresenter;
import com.ecarxclub.app.presenter.RetrievePwd.ForgetPwdView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.CommonUtils;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/4/18 16:23.
 * 忘记密码  指定手机号领取卡券
 */
public class RetrievePwdActivity extends BaseActivityP<ForgetPwdPresenter> implements ForgetPwdView {
    @BindView(R.id.tv_toolbar_left)
    ImageView tvToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_toolbar_right)
    TextView tvToolbarRight;
    @BindView(R.id.et_forget_phone)
    EditText etPhone;
    @BindView(R.id.et_forget_code)
    EditText etCode;
    @BindView(R.id.btn_forget_code)
    Button btnGetCode;
    @BindView(R.id.btn_forget_next)
    Button btnNext;
    @BindView(R.id.tv_get_phonetxt)
    TextView tvGetTxt;

    private String mStrPhone,mStrCode;

    private String mFlag,mCardId;//1找回密码 2指定手机号领取卡券
    private CountDownTimer timer;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_retrieve_pwd;
    }
    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
//        tvToolbarLeft.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.nav_icon_back), null, null, null);
    }

    @Override
    public void initData() {
        mFlag=getIntent().getExtras().getString("mflag");
        mCardId=getIntent().getExtras().getString("cardId");
        if("1".equals(mFlag)){
            tvToolbarTitle.setText("找回密码");
            tvGetTxt.setVisibility(View.GONE);
        }else {
            tvToolbarTitle.setText("指定手机号领取验证");
            tvGetTxt.setVisibility(View.VISIBLE);
            btnNext.setText("确定领取");
        }

    }

    @Override
    public void initClick() {
        RxView.clicks(tvToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
        //获取验证码
        RxView.clicks(btnGetCode).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mStrPhone=etPhone.getText().toString();
                if(CommonUtils.isPhoneNumber(mStrPhone)){
                    if("1".equals(mFlag)){
                        presenter.getForgetCode(mStrPhone);
                    }else {
                        presenter.getZdPhoneCode(mStrPhone,mCardId);
                    }
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
                    if(mStrCode.length()>0&&mStrCode.length()<=6){
                        if("1".equals(mFlag)){
                            presenter.getCodeIsRight(mStrPhone,mStrCode);
                        }else {
                            Map<String ,String> map=new HashMap<>();
                            map.put("couponId",mCardId);
                            map.put("mobile",mStrPhone);
                            presenter.getCoupon(map);
                        }
                    }else {
                        showtoast("请输入正确的验证码");
                    }
                }else {
                    showtoast("请输入正确的手机号码");
                }
            }
        });
    }

    private void onTimer(int time){
        /** 倒计时60秒，一次1秒 */
        timer = new CountDownTimer((time==0?60:time)*1000+200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                ToastUtils.i("time   ",millisUntilFinished / 1000+"_____"+millisUntilFinished);
                btnGetCode.setText( millisUntilFinished / 1000 +" s");
                btnGetCode.setEnabled(false);
            }
            @Override
            public void onFinish() {
                btnGetCode.setText("获取验证码");
                btnGetCode.setEnabled(true);
            }
        }.start();
    }

    //获取验证码成功
    @Override
    public void onGetCode(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()){
            onTimer(60);
            showtoast("获取验证码成功");
        }
    }

    //校验验证码
    @Override
    public void onCodeIsRight(BaseMsgRes baseMsgRes) {
        if(baseMsgRes.isSuccess()){
            Bundle bundle=new Bundle();
            bundle.putString(Constant.PHONENUMBER,mStrPhone);
            bundle.putString(Constant.TOKEN,""+baseMsgRes.getData());
            startIntent(RetrievePwdActivity.this,ReSetPwdActivity.class,bundle);
        }
    }

    //领取指定优惠券
    @Override
    public void onGetCoupone(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()){
            showtoast("领取成功");
            finish();
        }else {
            showtoast(baseMsgRes.getMsg());
        }
    }

    @Override
    public void onGetPhoneCode(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()){
            onTimer(60);
            showtoast("发送成功");
        }
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
