package com.ecarxclub.app.presenter.main;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.UserInfoRes;
import com.ecarxclub.app.model.home.AdvertisementRes;

/**
 * Created by cwp
 * on 2019/4/19 14:32.
 */
public interface MainView extends BaseView {
    void onUserInfo(UserInfoRes baseMsgRes);
    void onGetPopShowType(AdvertisementRes advertisementRes);//广告弹窗
    void onClickAdvert(BaseMsgRes baseMsgRes);//广告点击
}
