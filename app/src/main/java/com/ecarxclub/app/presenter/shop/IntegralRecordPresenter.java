package com.ecarxclub.app.presenter.shop;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.shop.IntegralRecordRes;

/**
 * Created by cwp
 * on 2019/4/28 10:26.
 */
public class IntegralRecordPresenter extends BasePresenter<IntegralRecordView> {
    public IntegralRecordPresenter(IntegralRecordView baseView) {
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
