package com.ecarxclub.app.presenter.home;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.home.DrivingDetailsRes;

/**
 * Created by cwp
 * on 2019/5/6 15:24.
 */
public class DrivingDetailsPresenter extends BasePresenter<DrivingDetailsView> {
    public DrivingDetailsPresenter(DrivingDetailsView baseView) {
        super(baseView);
    }


    //获取车辆详细记录
    public void onGetCarDetails(String drvId){
        addDisposable(apiServer.getDrivingDetails(drvId), new BaseObserver<DrivingDetailsRes>(baseView) {
            @Override
            public void onSuccess(DrivingDetailsRes o) {
                baseView.onGetDrivingDetails(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
