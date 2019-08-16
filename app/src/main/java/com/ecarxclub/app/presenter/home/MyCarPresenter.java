package com.ecarxclub.app.presenter.home;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.home.car.BindCarRes;

/**
 * Created by cwp
 * on 2019/5/6 11:11.
 */
public class MyCarPresenter extends BasePresenter<MyCarView> {
    public MyCarPresenter(MyCarView baseView) {
        super(baseView);
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

    //获取所有车辆
    public void ongetAllCar(){
        addDisposable(apiServer.getonAllCar(), new BaseObserver<BindCarRes>(baseView) {
            @Override
            public void onSuccess(BindCarRes o) {
                baseView.onGetMyCar(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
