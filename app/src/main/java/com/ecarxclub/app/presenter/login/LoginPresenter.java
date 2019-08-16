package com.ecarxclub.app.presenter.login;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.model.login.LoginRes;
import com.ecarxclub.app.utils.SharedPreferencesUtils;


public class LoginPresenter extends BasePresenter<LoginView> {
    public LoginPresenter(LoginView baseView){
        super(baseView);
    }

    //登录
    public void login(String name,String pwd){
        addDisposable(apiServer.Login(name, pwd, SharedPreferencesUtils.getParam(YxbApplication.getContext(), UrlOkHttpUtils.YXB_PUSH_ID,"").toString()), new BaseObserver<LoginRes>(baseView) {
            @Override
            public void onSuccess(LoginRes o) {
                baseView.onLoginResult(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

}
