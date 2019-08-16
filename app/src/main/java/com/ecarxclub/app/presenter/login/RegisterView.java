package com.ecarxclub.app.presenter.login;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.login.LoginRes;
import com.ecarxclub.app.model.login.RegisterRes;

/**
 * Created by cwp
 * on 2019/4/18 15:04.
 * 注册
 */
public interface RegisterView extends BaseView{
    void onGetCode(BaseMsgRes baseMsgRes);
    void onRegisterResult(RegisterRes registerRes);
    void onLoginResult(LoginRes loginRes);
}
