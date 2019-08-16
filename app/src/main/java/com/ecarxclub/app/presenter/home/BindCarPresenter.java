package com.ecarxclub.app.presenter.home;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;

/**
 * Created by cwp
 * on 2019/5/6 11:11.
 */
public class BindCarPresenter extends BasePresenter<BindCarView> {
    public BindCarPresenter(BindCarView baseView) {
        super(baseView);
    }


    //绑定车辆 获取验证码
    public void onBindCarCode(String mobile){
        addDisposable(apiServer.postBindCarCode(mobile), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onBindCarCode(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //绑定车辆
    public void onBindCar(String mobile,String code,String icard){
        addDisposable(apiServer.getonBindCar(mobile,code,icard), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onBindCar(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
