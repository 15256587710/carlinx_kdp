package com.ecarxclub.app.presenter.main.home;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.UserInfoRes;
import com.ecarxclub.app.model.YinLianPayRes;
import com.ecarxclub.app.model.home.AdvertisementRes;
import com.ecarxclub.app.model.home.GetVersionRes;
import com.ecarxclub.app.model.home.HomeDrivingInfoRes;
import com.ecarxclub.app.model.home.OtherServiceRes;
import com.ecarxclub.app.model.home.WeatherRes;

/**
 * Created by cwp
 * on 2019/4/19 15:16.
 */
public interface HomeView extends BaseView {
    void onUserInfo(UserInfoRes baseMsgRes);//个人信息
    void onGetWeater(WeatherRes weatherRes);//天气
    void onGetPopShowType(AdvertisementRes advertisementRes);//广告弹窗

    void onClickAdvert(BaseMsgRes baseMsgRes);//广告点击
    void onVersion(GetVersionRes baseMsgRes);//更新
    void onServices(OtherServiceRes baseMsgRes);//第三方服务

    void onDrivingRecodeV(HomeDrivingInfoRes baseMsgRes);//首页行驶记录信息

}
