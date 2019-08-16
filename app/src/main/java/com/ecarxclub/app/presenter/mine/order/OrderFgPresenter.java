package com.ecarxclub.app.presenter.mine.order;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.mine.MyOrderListRes;
import com.ecarxclub.app.model.pay.PayOrderSuccessRes;

import java.util.Map;

/**
 * Created by cwp
 * on 2019/4/26 13:35.
 */
public class OrderFgPresenter extends BasePresenter<OrderFgView> {
    public OrderFgPresenter(OrderFgView baseView) {
        super(baseView);
    }

    //获取我的订单
    public void onGetOrderList(Map<String,String> map){
        addDisposable(apiServer.getOrderList(map), new BaseObserver<MyOrderListRes>(baseView) {
            @Override
            public void onSuccess(MyOrderListRes o) {
                baseView.onOrderList(o);
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
