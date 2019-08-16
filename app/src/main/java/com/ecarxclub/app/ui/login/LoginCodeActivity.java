package com.ecarxclub.app.ui.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
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
import com.ecarxclub.app.presenter.login.newlogin.BindPhonePresenter;
import com.ecarxclub.app.presenter.login.newlogin.BindPhoneView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.ui.MainActivity;
import com.ecarxclub.app.ui.WebViewActivity;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.SharedPreferencesUtils;
import com.jakewharton.rxbinding.view.RxView;
import org.greenrobot.eventbus.EventBus;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/7/18 11:47.
 * 登录 验证码登录
 */
public class LoginCodeActivity extends BaseActivityP<BindPhonePresenter> implements BindPhoneView {
    @BindView(R.id.img_bindphone_back)
    ImageView imgBack;
    @BindView(R.id.tv_getcode_phone)
    TextView tvPhone;
    @BindView(R.id.btn_getcode_get)
    Button btnGetCode;
    @BindView(R.id.et_getcode_code)
    EditText etCode;
    @BindView(R.id.btn_getcode_sure)
    Button btnSure;
    @BindView(R.id.ll_loginnew_xy)
    LinearLayout llXy;

    private String mCode;
    private String mPhone,mKey;//微信手机号登录
    private String loginOut;// 1设置退出登录
    private CountDownTimer timer;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_code;
    }

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
    }

    @Override
    public void initData() {
        if (getIntent().getExtras()!=null){
            mPhone=getIntent().getExtras().getString("phonenum");
            mKey=getIntent().getExtras().getString("key");
            loginOut=getIntent().getExtras().getString("unLogin");
            tvPhone.setText("已发送至"+mPhone);
            onTimer(60);
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
        RxView.clicks(btnGetCode).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                presenter.getCodeP(mPhone);
            }
        });

        //登录
        RxView.clicks(btnSure).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mCode = etCode.getText().toString();
                if (mCode.length()>0&&mCode.length() <= 6) {
                    if (mKey!=null&&mKey.length()>0){//微信登录
                        presenter.loginWechatPhone(mPhone,mCode,mKey);
                    }else {
                        presenter.loginPhone(mPhone,mCode);
                    }
                } else {
                    showtoast("请输入正确格式的验证码");
                }
            }
        });
        //协议
        RxView.clicks(llXy).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Bundle bundle=new Bundle();
                //   https://app.carlinx.cn/privacy-policy.html
                bundle.putString("web_url","https://app.carlinx.cn/UserServiceProtocol.html");
                bundle.putString("welcome","regist");
                startIntent(LoginCodeActivity.this,WebViewActivity.class,bundle);
            }
        });
    }

    private void onTimer(int time){
        /** 倒计时60秒，一次1秒 */
        timer = new CountDownTimer((time==0?60:time)*1000+200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
//                ToastUtils.i("time   ",millisUntilFinished / 1000+"_____"+millisUntilFinished);
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

    @Override
    public void onGetCode(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()){
            onTimer(60);
            showtoast("发送成功");
        }else {
            showtoast(baseMsgRes.getMsg());
        }
    }

    //微信手机号码登录
    @Override
    public void onWechatPhone(LoginRes loginNewRes) {
        if (loginNewRes.isSuccess()){
            loginSuccess(loginNewRes.getData().getToken());
        }
    }

    private void loginSuccess(String token){
        SharedPreferencesUtils.setParam(LoginCodeActivity.this, UrlOkHttpUtils.YXB_USER_TOKEN,token);
        EventBus.getDefault().post(new EventMessage(Constant.LOGIN_SUCCESS,Constant.LOGIN_SUCCESS_TXT));
        if (loginOut.length()>0){
            startIntent(LoginCodeActivity.this, MainActivity.class);
        }
        EventBus.getDefault().post(new EventMessage(Constant.USER_FINISH_LOGINACT,""));
        finish();
    }

    //手机号登录
    @Override
    public void onPhoneLogin(LoginRes loginNewRes) {
        if (loginNewRes.isSuccess()){
            loginSuccess(loginNewRes.getData().getToken());
        }
    }

    @Override
    protected BindPhonePresenter createPresenter() {
        return new BindPhonePresenter(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null){
            timer.cancel();
        }
    }
}
