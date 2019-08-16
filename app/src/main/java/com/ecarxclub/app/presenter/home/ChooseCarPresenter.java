package com.ecarxclub.app.presenter.home;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.home.car.BindCarRes;

/**
 * Created by cwp
 * on 2019/6/12 17:16.
 */
public class ChooseCarPresenter extends BasePresenter<ChooseCarView> {
    public ChooseCarPresenter(ChooseCarView baseView) {
        super(baseView);
    }
    //获取所有车辆
    public void ongetAllCar(){
        addDisposable(apiServer.getonAllCar(), new BaseObserver<BindCarRes>(baseView) {
            @Override
            public void onSuccess(BindCarRes o) {
                baseView.onGetAllCarV(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //绑定车辆
    public void ongBindCar(String carId){
        addDisposable(apiServer.postBindCar(carId), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onChooseV(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
