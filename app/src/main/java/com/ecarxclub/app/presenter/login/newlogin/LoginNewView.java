package com.ecarxclub.app.presenter.login.newlogin;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.login.LoginNewRes;

/**
 * Created by cwp
 * on 2019/7/18 10:11.
 */
public interface LoginNewView extends BaseView {
    void onWechatLogin(LoginNewRes baseMsgRes);
    void onGetCode(BaseMsgRes baseMsgRes);//获取验证码

}
