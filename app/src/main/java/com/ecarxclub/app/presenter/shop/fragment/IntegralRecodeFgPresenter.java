package com.ecarxclub.app.presenter.shop.fragment;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.shop.IntegralRecordRes;

/**
 * Created by cwp
 * on 2019/7/16 9:57.
 */
public class IntegralRecodeFgPresenter extends BasePresenter<IntegralRecodeFgView> {
    public IntegralRecodeFgPresenter(IntegralRecodeFgView baseView) {
        super(baseView);
    }
    //积分记录列表
    public void onRecordList(String type,int index,int size){
        addDisposable(apiServer.getIntegralRecord(type,index,size), new BaseObserver<IntegralRecordRes>(baseView) {
            @Override
            public void onSuccess(IntegralRecordRes o) {
                baseView.onReordList(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
