package com.ecarxclub.app.presenter.mine.coupon;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;

/**
 * Created by cwp
 * on 2019/4/24 14:41.
 */
public class BaseCouponPresenter extends BasePresenter<BaseCouponView> {
    public BaseCouponPresenter(BaseCouponView baseView) {
        super(baseView);
    }

    // 获取卡券详情
    public void onCouponDetails(String id){
        addDisposable(apiServer.onCouponDetails(id), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onCouponDetails(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
