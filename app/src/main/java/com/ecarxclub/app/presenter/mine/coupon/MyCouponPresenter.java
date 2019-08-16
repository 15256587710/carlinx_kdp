package com.ecarxclub.app.presenter.mine.coupon;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.mine.MyCouponRes;

/**
 * Created by cwp
 * on 2019/4/24 14:32.
 */
public class MyCouponPresenter extends BasePresenter<MyCouPonView> {
    public MyCouponPresenter(MyCouPonView baseView) {
        super(baseView);
    }

    //获取我的卡券
    public void onGetCouponList(int index,int size,boolean count,String status){
        addDisposable(apiServer.getCouponList(index,size,count,status), new BaseObserver<MyCouponRes>(baseView) {
            @Override
            public void onSuccess(MyCouponRes o) {
                baseView.onListResult(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

}
