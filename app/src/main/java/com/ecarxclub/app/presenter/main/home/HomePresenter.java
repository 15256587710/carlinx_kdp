package com.ecarxclub.app.presenter.main.home;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.UserInfoRes;
import com.ecarxclub.app.model.home.AdvertisementRes;
import com.ecarxclub.app.model.home.GetVersionRes;
import com.ecarxclub.app.model.home.HomeDrivingInfoRes;
import com.ecarxclub.app.model.home.OtherServiceRes;
import com.ecarxclub.app.model.home.WeatherRes;
import com.ecarxclub.app.utils.ToastUtils;

/**
 * Created by cwp
 * on 2019/4/19 15:17.
 */
public class HomePresenter extends BasePresenter<HomeView> {
    public HomePresenter(HomeView baseView) {
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

    //天气
    public void onGetWeather(String city){
        addDisposable(apiServer.getWeather(city), new BaseObserver<WeatherRes>(baseView) {
            @Override
            public void onSuccess(WeatherRes o) {
                baseView.onGetWeater(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //获取三方服务
    public void onGetServices(){
        addDisposable(apiServer.getServices(), new BaseObserver<OtherServiceRes>(baseView) {
            @Override
            public void onSuccess(OtherServiceRes o) {
                baseView.onServices(o);
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

    //更新
    public void onVersion(String version){
        addDisposable(apiServer.getVersion(version), new BaseObserver<GetVersionRes>(baseView) {
            @Override
            public void onSuccess(GetVersionRes o) {
                baseView.onVersion(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }


    //行驶信息
    public void onDrivingRecode(String id){
        addDisposable(apiServer.getDrivingRecode(id), new BaseObserver<HomeDrivingInfoRes>(baseView) {
            @Override
            public void onSuccess(HomeDrivingInfoRes o) {
                baseView.onDrivingRecodeV(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
