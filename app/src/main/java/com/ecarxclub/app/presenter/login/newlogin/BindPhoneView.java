package com.ecarxclub.app.presenter.login.newlogin;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.login.LoginNewRes;
import com.ecarxclub.app.model.login.LoginRes;

/**
 * Created by cwp
 * on 2019/7/18 11:25.
 */
public interface BindPhoneView extends BaseView {
    void onGetCode(BaseMsgRes baseMsgRes);//获取验证码

    void onWechatPhone(LoginRes loginNewRes);//微信手机号码登录
    void onPhoneLogin(LoginRes loginNewRes);//手机号码登录
}
