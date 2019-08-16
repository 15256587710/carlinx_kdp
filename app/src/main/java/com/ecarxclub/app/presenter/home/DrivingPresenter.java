package com.ecarxclub.app.presenter.home;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.home.MyDrivingListRes;

/**
 * Created by cwp
 * on 2019/4/22 16:07.
 */
public class DrivingPresenter extends BasePresenter<DrivingView> {
    public DrivingPresenter(DrivingView baseView) {
        super(baseView);
    }
    //获取车辆列表记录
    public void onGetCarDetails(String carid,int index,int size){
        addDisposable(apiServer.getDrivingList(carid,index,size), new BaseObserver<MyDrivingListRes>(baseView) {
            @Override
            public void onSuccess(MyDrivingListRes o) {
                baseView.onGetList(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
