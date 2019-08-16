package com.ecarxclub.app.ui.login;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ecarxclub.app.R;
import com.ecarxclub.app.base.BindEventBus;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.login.LoginNewRes;
import com.ecarxclub.app.presenter.login.newlogin.LoginNewPresenter;
import com.ecarxclub.app.presenter.login.newlogin.LoginNewView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.ui.MainActivity;
import com.ecarxclub.app.ui.WebViewActivity;
import com.ecarxclub.app.utils.CommonUtils;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.SharedPreferencesUtils;
import com.ecarxclub.app.utils.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import rx.functions.Action1;

import static com.ecarxclub.app.common.YxbApplication.WX_API;

/**
 * Created by cwp
 * on 2019/7/18 10:10.
 */
@BindEventBus
public class LoginNewActivity extends BaseActivityP<LoginNewPresenter> implements LoginNewView {
    @BindView(R.id.img_login_back)
    ImageView imgBack;
    @BindView(R.id.img_loginnew_wechat)
    ImageView imgWechat;
    @BindView(R.id.btn_loginnew_getcode)
    Button btnCode;
    @BindView(R.id.et_loginnew_phone)
    EditText etPhone;
    @BindView(R.id.ll_loginnew_xy)
    LinearLayout  llXy;
    private String mPhoneNum = "";
    private String loginOut="";// 1设置退出登录
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login2;
    }

    @Override
    protected LoginNewPresenter createPresenter() {
        return new LoginNewPresenter(this);
    }

    @Override
    public void initData() {
        if (getIntent().getExtras()!=null){
            loginOut=getIntent().getExtras().getString("unLogin");
        }
        mPhoneNum= SharedPreferencesUtils.getParam(LoginNewActivity.this, UrlOkHttpUtils.YXB_USER_MOBILE,"").toString();
        if (mPhoneNum!=null&&mPhoneNum.length()>0){
            etPhone.setText(mPhoneNum);
            etPhone.setSelection(mPhoneNum.length());
        }
        //极光id 登录时候需要
        String push_userid = JPushInterface.getRegistrationID(getApplicationContext());
//        ToastUtils.i("极光id ", "  " + push_userid);//100d855909364952e60
        SharedPreferencesUtils.setParam(LoginNewActivity.this, UrlOkHttpUtils.YXB_PUSH_ID, push_userid);
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
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
    }

    @Override
    public void initClick() {
        //返回
        RxView.clicks(imgBack).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                goBack();
            }
        });

        //返回
        RxView.clicks(btnCode).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mPhoneNum = etPhone.getText().toString();
                if (CommonUtils.isPhoneNumber(mPhoneNum)) {
                    presenter.getCodeP(mPhoneNum);
                } else {
                    showtoast("请输入正确的手机号码");
                }
            }
        });
        //微信登录
        RxView.clicks(imgWechat).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
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

        //协议
        RxView.clicks(llXy).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Bundle bundle=new Bundle();
                //   https://app.carlinx.cn/privacy-policy.html
                bundle.putString("web_url","https://app.carlinx.cn/UserServiceProtocol.html");
                bundle.putString("welcome","regist");
                startIntent(LoginNewActivity.this,WebViewActivity.class,bundle);
            }
        });
    }

    private void goBack(){
        if (loginOut.length()>0){
            startIntent(LoginNewActivity.this, MainActivity.class);
        }
        finish();
    }

    //微信授权登录
    private void wechatLogin(String code){
        presenter.getWechatlogin(code);
    }

    @Override
    public void onWechatLogin(LoginNewRes loginNewRes) {
        if (loginNewRes.success){
            if (loginNewRes.data.success){//注册过
                loginSuccess(loginNewRes.data.auth.token);
            }else {//第一次登录去绑定手机号
                Bundle bundle=new Bundle();
                bundle.putString("wechat_key",loginNewRes.data.key+"");
                bundle.putString("unLogin",loginOut);
                startIntent(LoginNewActivity.this,BindPhoneActivity.class,bundle);
            }
        }
    }

    private void loginSuccess(String token){
        SharedPreferencesUtils.setParam(LoginNewActivity.this, UrlOkHttpUtils.YXB_USER_TOKEN,token);
        EventBus.getDefault().post(new EventMessage(Constant.LOGIN_SUCCESS,Constant.LOGIN_SUCCESS_TXT));
        goBack();
    }

    //获取验证码
    @Override
    public void onGetCode(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()){
            Bundle bundle=new Bundle();
            bundle.putString("unLogin","1");
            bundle.putString("phonenum",mPhoneNum);
            startIntent(LoginNewActivity.this,LoginCodeActivity.class,bundle);
        }
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusLogin(EventMessage eventMessage) {
        ToastUtils.e("LoginNewActivity " + eventMessage.action, "  obj" + eventMessage.object);
        switch (eventMessage.action) {
            case Constant.USER_WECHAT:
                String mCode= (String) eventMessage.object;
                if (mCode.length()>0){
                    wechatLogin(mCode);
                }
                break;
            case Constant.USER_FINISH_LOGINACT://登录成功  结束当前activity
                finish();
                break;
        }
    }

}
