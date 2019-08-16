package com.ecarxclub.app.presenter.login;


import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.login.LoginRes;
import com.ecarxclub.app.model.login.RegisterRes;
import com.ecarxclub.app.utils.SharedPreferencesUtils;


public class RegisterPresenter extends BasePresenter<RegisterView> {
    public RegisterPresenter(RegisterView baseView){
        super(baseView);
    }


    //获取验证码
    public void getCode(String name){
        addDisposable(apiServer.getCode(name), new BaseObserver<BaseMsgRes>(baseView) {
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


    //注册
    public void reGister(String mobile,String pwd,String code){
        addDisposable(apiServer.reGiisterNumber(mobile,pwd,code), new BaseObserver<RegisterRes>(baseView) {
            @Override
            public void onSuccess(RegisterRes o) {
                baseView.onRegisterResult(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
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
