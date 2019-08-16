package com.ecarxclub.app.presenter.shop.fragment;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.home.HomeDrivingInfoRes;
import com.ecarxclub.app.model.shop.ShopBannerRes;
import com.ecarxclub.app.model.shop.integral.WaterRes;
import com.ecarxclub.app.model.shop.fgshop.ShopListRes;
import com.ecarxclub.app.model.shop.fgshop.ShopTagsRes;
import com.ecarxclub.app.model.shop.fgshop.TodayClockRes;

/**
 * Created by cwp
 * on 2019/4/26 15:14.
 * 积分商城
 */
public interface ShopFgView extends BaseView{
//    void onGetResult(WaterRes baseMsgRes);//获取签到积分
//    void onGetPointId(BaseMsgRes baseMsgRes);//领取签到积分
//    void onGetUserScore(BaseMsgRes baseMsgRes);//用户积分
//    void onGetClockDays(BaseMsgRes baseMsgRes);//连续签到天数
//    void onGetTodayClock(TodayClockRes todayClockRes);//当天签到记录

//    void onGetCheckScore(BaseMsgRes baseMsgRes);//签到

    void onGetShopTag(ShopTagsRes shopTagsRes);//标签

    void onGetShopList(ShopListRes shopTagsRes);//列表
//    void onDrivingRecodeV(HomeDrivingInfoRes baseMsgRes);//行驶记录信息
    void onGetShopBanner(ShopBannerRes baseMsgRes);//banner
}
