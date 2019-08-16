package com.ecarxclub.app.presenter.home;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.home.car.CarInfoRes;
import com.ecarxclub.app.model.home.car.CarStyleRes;

import java.util.Map;

/**
 * Created by cwp
 * on 2019/7/22 13:49.
 */
public class ImprovingCarPresenter extends BasePresenter<ImprovingCarView> {
    public ImprovingCarPresenter(ImprovingCarView baseView) {
        super(baseView);
    }


    //获取车辆信息
    public void getCarInfos(String id){
        addDisposable(apiServer.getCarInfo(id), new BaseObserver<CarInfoRes>(baseView) {
            @Override
            public void onSuccess(CarInfoRes o) {
                baseView.onCarInfo(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //完善车辆信息
    public void updateCarInfo(Map<String,String> map){
        addDisposable(apiServer.updateCarDetails(map), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onUpdateInfo(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //车辆车型
    public void getCarStyle(){
        addDisposable(apiServer.getCarStyle(), new BaseObserver<CarStyleRes>(baseView) {
            @Override
            public void onSuccess(CarStyleRes o) {
                baseView.onCarStyle(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //车辆颜色
    public void getCarColor(String id){
        addDisposable(apiServer.getCarColor(id), new BaseObserver<CarStyleRes>(baseView) {
            @Override
            public void onSuccess(CarStyleRes o) {
                baseView.onCarColor(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
