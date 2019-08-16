package com.ecarxclub.app.presenter;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.home.AdvertisementRes;

/**
 * Created by cwp
 * on 2019/5/21 9:57.
 */
public interface WelcomeView extends BaseView {
    void onWelcome(BaseMsgRes baseMsgRes);
    //广告
    void onGetPopShowType(AdvertisementRes advertisementRes);

    void onClickAdvert(BaseMsgRes baseMsgRes);//广告点击
    void onCensus(BaseMsgRes baseMsgRes);//渠道下载统计


}
