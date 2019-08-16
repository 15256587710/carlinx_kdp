package com.ecarxclub.app.presenter.home.scan;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.shop.integral.WaterRes;

/**
 * Created by cwp
 * on 2019/7/24 11:29.
 */
public class ScanCodePresenter extends BasePresenter<ScanCodeView> {
    public ScanCodePresenter(ScanCodeView baseView) {
        super(baseView);
    }

    //车机扫码登录
    public void getcarLogin(String code){
        addDisposable(apiServer.carLogin(code), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onCarLogin(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
