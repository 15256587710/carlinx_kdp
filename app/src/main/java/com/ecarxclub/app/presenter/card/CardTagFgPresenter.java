package com.ecarxclub.app.presenter.card;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.card.CardListRes;

import java.util.Map;

/**
 * Created by cwp
 * on 2019/4/25 14:12.
 */
public class CardTagFgPresenter extends BasePresenter<CardTagsFgView> {
    public CardTagFgPresenter(CardTagsFgView baseView) {
        super(baseView);
    }

    //取券分类列表
    public void getCardList(Map<String ,String> map){
        addDisposable(apiServer.getCardList(map), new BaseObserver<CardListRes>(baseView) {
            @Override
            public void onSuccess(CardListRes o) {
                baseView.onCardTagList(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

}
