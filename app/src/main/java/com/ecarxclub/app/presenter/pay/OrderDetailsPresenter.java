package com.ecarxclub.app.presenter.pay;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.mine.ConfirmRecriceRes;
import com.ecarxclub.app.model.mine.LogistiesListRes;
import com.ecarxclub.app.model.pay.PayOrderSuccessRes;

import java.util.Map;

/**
 * Created by cwp
 * on 2019/5/8 13:47.
 */
public class OrderDetailsPresenter extends BasePresenter<OrderDetailsView> {
    public OrderDetailsPresenter(OrderDetailsView baseView) {
        super(baseView);
    }

    //我的地址列表
    public void onCancelOrder(String orderid){
        addDisposable(apiServer.orderCancel(orderid), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onCancelOrder(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //物流列表
    public void onLogistics(String name,String number){
        addDisposable(apiServer.getLogisties(name,number), new BaseObserver<LogistiesListRes>(baseView) {
            @Override
            public void onSuccess(LogistiesListRes o) {
                baseView.onLogistiesV(o);
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

    //确认签收
    public void getConformRecive(String orderid){
        addDisposable(apiServer.getConfirmRecive(orderid), new BaseObserver<ConfirmRecriceRes>(baseView) {
            @Override
            public void onSuccess(ConfirmRecriceRes o) {
                baseView.onConfirmRecive(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
