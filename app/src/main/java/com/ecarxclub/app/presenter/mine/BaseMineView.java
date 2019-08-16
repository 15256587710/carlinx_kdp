package com.ecarxclub.app.presenter.mine;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.UserInfoNewRes;
import com.ecarxclub.app.model.UserInfoRes;

/**
 * Created by cwp
 * on 2019/5/8 9:49.
 */
public interface BaseMineView extends BaseView{
    void onGetUserScore(BaseMsgRes baseMsgRes);//获取用户积分
    void onUserInfo(UserInfoRes baseMsgRes);//用户信息
    void onUserNewInfo(UserInfoNewRes baseMsgRes);//用户信息
}
