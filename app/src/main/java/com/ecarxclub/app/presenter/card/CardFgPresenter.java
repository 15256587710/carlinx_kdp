package com.ecarxclub.app.presenter.card;


import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.card.CardBannerRes;
import com.ecarxclub.app.model.card.CardCouponTypeRes;
import com.ecarxclub.app.model.card.CardListRes;

import java.util.Map;

/**
 * Created by cwp
 * on 2019/4/23 9:43.
 */
public class CardFgPresenter extends BasePresenter<CardFgView> {
    public CardFgPresenter(CardFgView baseView) {
        super(baseView);
    }

    //banner
    public void getCardBanner(){
        addDisposable(apiServer.getBanner(), new BaseObserver<CardBannerRes>(baseView) {
            @Override
            public void onSuccess(CardBannerRes o) {
                baseView.onGetBannerResult(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //卡券分类
    public void getCardType(){
        addDisposable(apiServer.getCouponType(), new BaseObserver<CardCouponTypeRes>(baseView) {
            @Override
            public void onSuccess(CardCouponTypeRes o) {
                baseView.onCouponType(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //卡券分类列表
    public void getCardList(Map<String, String> map){
        addDisposable(apiServer.getCardList(map), new BaseObserver<CardListRes>(baseView) {
            @Override
            public void onSuccess(CardListRes o) {
                baseView.onCouponList(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
