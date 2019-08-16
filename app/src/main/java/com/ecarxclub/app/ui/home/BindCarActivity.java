package com.ecarxclub.app.ui.home;

import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.presenter.home.BindCarPresenter;
import com.ecarxclub.app.presenter.home.BindCarView;
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
 * on 2019/6/12 15:39.
 * 绑定车辆
 */
public class BindCarActivity extends BaseActivityP<BindCarPresenter> implements BindCarView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.et_bindcar_idcard)
    EditText etIdCard;//身份证

    @BindView(R.id.et_bindcar_code)
    EditText etCode;
    @BindView(R.id.et_bindcar_phone)
    EditText etPhone;
    @BindView(R.id.tv_bindcar_getyzm)
    TextView tvYzm;
    @BindView(R.id.btn_bindcar_select)
    Button btnSelect;

    private CountDownTimer timer;
    private String mPhone,mCode,mCard;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_bindcar;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarTitle.setText("绑定车辆");
    }

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
        //获取验证码
        RxView.clicks(tvYzm).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mPhone=etPhone.getText().toString();
                if(CommonUtils.isPhoneNumber(mPhone)){
                    presenter.onBindCarCode(mPhone);
                }else {
                    ToastUtils.showTextShort("请输入正确的手机号");
                }
            }
        });
        //绑定
        RxView.clicks(btnSelect).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mPhone=etPhone.getText().toString();
                if(CommonUtils.isPhoneNumber(mPhone)){
                    mCode=etCode.getText().toString();
                    if(mCode!=null&&mCode.length()<=6){
                        mCard=etIdCard.getText().toString();
                        if(mCard!=null&&mCard.length()==6){
                            presenter.onBindCar(mPhone,mCode,mCard);
                        }else {
                            ToastUtils.showTextShort("请输入正确的身份证后六位");
                        }
                    }else {
                        ToastUtils.showTextShort("请输入正确的验证码");
                    }
                }else {
                    ToastUtils.showTextShort("请输入正确的手机号");
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
                tvYzm.setText( millisUntilFinished / 1000 +" s");
                tvYzm.setEnabled(false);
            }
            @Override
            public void onFinish() {
                tvYzm.setText("获取验证码");
                tvYzm.setEnabled(true);
            }
        }.start();
    }

    //绑定车辆
    @Override
    public void onBindCar(BaseMsgRes baseMsgRes) {
        if(baseMsgRes.isSuccess()){
            startIntent(BindCarActivity.this,ChooseCarActivity.class);
        }
    }

    //验证码
    @Override
    public void onBindCarCode(BaseMsgRes baseMsgRes) {
        if(baseMsgRes.isSuccess()){
            onTimer(60);
            ToastUtils.showTextShort("验证码发送成功");
        }
    }

    @Override
    protected BindCarPresenter createPresenter() {
        return new BindCarPresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null){
            timer.cancel();
        }
    }
}
