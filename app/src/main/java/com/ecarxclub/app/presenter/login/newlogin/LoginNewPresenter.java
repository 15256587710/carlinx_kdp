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
 * on 2019/7/18 10:11.
 */
public class LoginNewPresenter extends BasePresenter<LoginNewView> {
    public LoginNewPresenter(LoginNewView baseView) {
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

    //微信登录
    public void getWechatlogin(String code){
        addDisposable(apiServer.WechatLogin(code, SharedPreferencesUtils.getParam(YxbApplication.getContext(),
                UrlOkHttpUtils.YXB_PUSH_ID,"").toString()), new BaseObserver<LoginNewRes>(baseView) {
            @Override
            public void onSuccess(LoginNewRes o) {
                baseView.onWechatLogin(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

}
