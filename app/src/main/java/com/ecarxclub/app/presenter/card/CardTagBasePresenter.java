package com.ecarxclub.app.presenter.card;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.card.CardCouponTypeRes;

/**
 * Created by cwp
 * on 2019/4/25 11:40.
 */
public class CardTagBasePresenter extends BasePresenter<CardTagBaseView> {
    public CardTagBasePresenter(CardTagBaseView baseView) {
        super(baseView);
    }

    //获取卡券分类
    public void getCardTags(){
        addDisposable(apiServer.getCouponType(), new BaseObserver<CardCouponTypeRes>(baseView) {
            @Override
            public void onSuccess(CardCouponTypeRes o) {
                baseView.onGetCardTags(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
