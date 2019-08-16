package com.ecarxclub.app.presenter.RetrievePwd;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;

/**
 * Created by cwp
 * on 2019/4/18 17:03.
 */
public interface ForgetPwdView extends BaseView{
    void onGetCode(BaseMsgRes baseMsgRes);//验证码
    void onCodeIsRight(BaseMsgRes baseMsgRes);//交易验证码
    void onGetCoupone(BaseMsgRes baseMsgRes);//领取卡券
    void onGetPhoneCode(BaseMsgRes baseMsgRes);//指定手机号获取验证码

}
