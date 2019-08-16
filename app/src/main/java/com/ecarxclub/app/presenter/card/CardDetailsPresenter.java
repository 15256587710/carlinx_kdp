package com.ecarxclub.app.presenter.card;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.card.CardDetailsRes;

import java.util.Map;

/**
 * Created by cwp
 * on 2019/4/30 16:33.
 */
public class CardDetailsPresenter extends BasePresenter<CardDetailsView> {
    public CardDetailsPresenter(CardDetailsView baseView) {
        super(baseView);
    }

    // 获取卡券详情
    public void onCardDetails(String id){
        addDisposable(apiServer.onCouponDetails(id), new BaseObserver<CardDetailsRes>(baseView) {
            @Override
            public void onSuccess(CardDetailsRes o) {
                baseView.onCardDetails(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //当前手机号领取卡券
    public void getCoupon(Map<String,String> map) {
        addDisposable(apiServer.getCoupone(map), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onGetCoupone(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

}
