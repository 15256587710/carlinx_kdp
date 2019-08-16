package com.ecarxclub.app.presenter.shop;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.mine.MyAddressRes;
import com.ecarxclub.app.model.pay.PayOrderRes;
import com.ecarxclub.app.model.pay.PayOrderSuccessRes;

import java.util.Map;

/**
 * Created by cwp
 * on 2019/4/29 16:55.
 */
public class ExchangeDetailsPresenter extends BasePresenter<ExchangeDetailsView> {
    public ExchangeDetailsPresenter(ExchangeDetailsView baseView) {
        super(baseView);
    }

    //我的地址列表
    public void getAddressList(boolean count,int index,int size){
        addDisposable(apiServer.getAddressList(count,index,size), new BaseObserver<MyAddressRes>(baseView) {
            @Override
            public void onSuccess(MyAddressRes o) {
                baseView.onMyAddressList(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //获取用户积分
    public void getUserScore(){
        addDisposable(apiServer.getUserScore(), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onGetUserScore(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }


    //提交订单信息
    public void getUpOrder(Map<String,String> map){
        addDisposable(apiServer.upOrder(map), new BaseObserver<PayOrderRes>(baseView) {
            @Override
            public void onSuccess(PayOrderRes o) {
                baseView.onUpOrder(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //支付
    public void getPayOrderr(Map<String,String> map){
        addDisposable(apiServer.PayOrder(map), new BaseObserver<PayOrderSuccessRes>(baseView) {
            @Override
            public void onSuccess(PayOrderSuccessRes o) {
                baseView.onPayOrder(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

}
