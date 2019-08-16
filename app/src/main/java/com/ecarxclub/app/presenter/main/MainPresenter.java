package com.ecarxclub.app.presenter.main;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.UserInfoRes;
import com.ecarxclub.app.model.home.AdvertisementRes;

/**
 * Created by cwp
 * on 2019/4/19 14:33.
 * 主页  mainactivity
 */
public class MainPresenter extends BasePresenter<MainView> {
    public MainPresenter(MainView baseView) {
        super(baseView);
    }

    //获取用户信息
    public void onGetUserInfo(){
        addDisposable(apiServer.getUserInfo(), new BaseObserver<UserInfoRes>(baseView) {
            @Override
            public void onSuccess(UserInfoRes o) {
                baseView.onUserInfo(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
    //广告
    public void onGetShowAdvert(String type){
        addDisposable(apiServer.getAdvertise(type), new BaseObserver<AdvertisementRes>(baseView) {
            @Override
            public void onSuccess(AdvertisementRes o) {
                baseView.onGetPopShowType(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //广告点击
    public void onClickAdvert(String id,String advid){
        addDisposable(apiServer.onClickAdv(id,advid), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onClickAdvert(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
