package com.ecarxclub.app.presenter.login.newlogin;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.login.LoginNewRes;
import com.ecarxclub.app.model.login.LoginRes;
import com.ecarxclub.app.utils.SharedPreferencesUtils;

/**
 * Created by cwp
 * on 2019/7/18 11:25.
 */
public class BindPhonePresenter extends BasePresenter<BindPhoneView> {
    public BindPhonePresenter(BindPhoneView baseView) {
        super(baseView);
    }

    //获取验证码
    public void getCodeP(String phone){
        addDisposable(apiServer.getLoginNewCode(phone), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onGetCode(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //微信手机号登录
    public void loginWechatPhone(String phone,String code,String key){
        addDisposable(apiServer.WecheatRegist(phone,code,key,SharedPreferencesUtils.getParam(YxbApplication.getContext(), UrlOkHttpUtils.YXB_PUSH_ID,"").toString())
                , new BaseObserver<LoginRes>(baseView) {
            @Override
            public void onSuccess(LoginRes o) {
                baseView.onWechatPhone(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
    //手机号登录
    public void loginPhone(String phone,String code){
        addDisposable(apiServer.PhoneLogin(phone,code,SharedPreferencesUtils.getParam(YxbApplication.getContext(), UrlOkHttpUtils.YXB_PUSH_ID,"").toString())
                , new BaseObserver<LoginRes>(baseView) {
                    @Override
                    public void onSuccess(LoginRes o) {
                        baseView.onPhoneLogin(o);
                    }
                    @Override
                    public void onError(String msg) {
                        baseView.showError(msg);
                    }
                });
    }

}
