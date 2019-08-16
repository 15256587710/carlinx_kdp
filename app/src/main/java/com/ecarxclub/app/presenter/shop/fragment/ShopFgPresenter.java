package com.ecarxclub.app.presenter.shop.fragment;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.home.HomeDrivingInfoRes;
import com.ecarxclub.app.model.shop.ShopBannerRes;
import com.ecarxclub.app.model.shop.integral.WaterRes;
import com.ecarxclub.app.model.shop.fgshop.ShopListRes;
import com.ecarxclub.app.model.shop.fgshop.ShopTagsRes;
import com.ecarxclub.app.model.shop.fgshop.TodayClockRes;

import java.util.Map;

/**
 * Created by cwp
 * on 2019/4/26 15:15.
 */
public class ShopFgPresenter extends BasePresenter<ShopFgView> {
    public ShopFgPresenter(ShopFgView baseView) {
        super(baseView);
    }

    //积分商城banner
    public void getShopBannerP(){
        addDisposable(apiServer.getShopBanner(), new BaseObserver<ShopBannerRes>(baseView) {
            @Override
            public void onSuccess(ShopBannerRes o) {
                baseView.onGetShopBanner(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
    //行驶信息
//    public void onDrivingRecode(String id){
//        addDisposable(apiServer.getDrivingRecode(id), new BaseObserver<HomeDrivingInfoRes>(baseView) {
//            @Override
//            public void onSuccess(HomeDrivingInfoRes o) {
//                baseView.onDrivingRecodeV(o);
//            }
//
//            @Override
//            public void onError(String msg) {
//                baseView.showError(msg);
//            }
//        });
//    }

    //获取待领取积分
//    public void getPoint(){
//        addDisposable(apiServer.getBubble(), new BaseObserver<WaterRes>(baseView) {
//            @Override
//            public void onSuccess(WaterRes o) {
//                baseView.onGetResult(o);
//            }
//            @Override
//            public void onError(String msg) {
//                baseView.showError(msg);
//            }
//        });
//    }
//    //获取待领取积分
//    public void getPointId(String id){
//        addDisposable(apiServer.getBubbleid(id), new BaseObserver<BaseMsgRes>(baseView) {
//            @Override
//            public void onSuccess(BaseMsgRes o) {
//                baseView.onGetPointId(o);
//            }
//            @Override
//            public void onError(String msg) {
//                baseView.showError(msg);
//            }
//        });
//    }
//
//    //获取签到积分  签到
//    public void getCheckScore(){
//        addDisposable(apiServer.getCheckScore(), new BaseObserver<BaseMsgRes>(baseView) {
//            @Override
//            public void onSuccess(BaseMsgRes o) {
//                baseView.onGetCheckScore(o);
//            }
//            @Override
//            public void onError(String msg) {
//                baseView.showError(msg);
//            }
//        });
//    }
//
//
//    //获取当前签到记录
//    public void getTodayClock(){
//        addDisposable(apiServer.getTodayClock(), new BaseObserver<TodayClockRes>(baseView) {
//            @Override
//            public void onSuccess(TodayClockRes o) {
//                baseView.onGetTodayClock(o);
//            }
//            @Override
//            public void onError(String msg) {
//                baseView.showError(msg);
//            }
//        });
//    }
//
//    //获取连续签到天数
//    public void getClockDays(){
//        addDisposable(apiServer.getClockDays(), new BaseObserver<BaseMsgRes>(baseView) {
//            @Override
//            public void onSuccess(BaseMsgRes o) {
//                baseView.onGetClockDays(o);
//            }
//            @Override
//            public void onError(String msg) {
//                baseView.showError(msg);
//            }
//        });
//    }
//
//
//    //获取用户积分
//    public void getUserScore(){
//        addDisposable(apiServer.getUserScore(), new BaseObserver<BaseMsgRes>(baseView) {
//            @Override
//            public void onSuccess(BaseMsgRes o) {
//                baseView.onGetUserScore(o);
//            }
//            @Override
//            public void onError(String msg) {
//                baseView.showError(msg);
//            }
//        });
//    }

    //获取用户标签
    public void getShopTag(){
        addDisposable(apiServer.getShopTag(), new BaseObserver<ShopTagsRes>(baseView) {
            @Override
            public void onSuccess(ShopTagsRes o) {
                baseView.onGetShopTag(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //获取列表
    public void getShopList(Map<String ,String> map){
        addDisposable(apiServer.getShopList(map), new BaseObserver<ShopListRes>(baseView) {
            @Override
            public void onSuccess(ShopListRes o) {
                baseView.onGetShopList(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

}
