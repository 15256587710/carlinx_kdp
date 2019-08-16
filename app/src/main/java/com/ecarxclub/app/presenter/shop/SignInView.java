package com.ecarxclub.app.presenter.shop;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.shop.fgshop.ShopTagsRes;
import com.ecarxclub.app.model.shop.integral.SignInRes;
import com.ecarxclub.app.model.shop.integral.SignInRewordRes;

/**
 * Created by cwp
 * on 2019/7/10 16:10.
 */
public interface SignInView extends BaseView {
    void onSignIn(BaseMsgRes baseMsgRes);//签到
    void onSignInRecode(SignInRes baseMsgRes);//签到记录
    void onSignInReword(SignInRewordRes baseMsgRes);//获取奖励列表
    void onGetReword(BaseMsgRes baseMsgRes);//领取奖励
    void onGetShopTag(ShopTagsRes shopTagsRes);//标签
}
