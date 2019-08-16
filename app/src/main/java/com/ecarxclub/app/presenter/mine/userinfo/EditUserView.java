package com.ecarxclub.app.presenter.mine.userinfo;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.UserInfoRes;

/**
 * Created by cwp
 * on 2019/5/17 16:12.
 */
public interface EditUserView extends BaseView {
    void onUserInfo(UserInfoRes baseMsgRes);//用户信息

    void onGetResult(BaseMsgRes baseMsgRes);//修改头像
    //修改用户信息
    void onEditUser(BaseMsgRes baseMsgRes);
}
