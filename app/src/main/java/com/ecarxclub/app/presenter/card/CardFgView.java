package com.ecarxclub.app.presenter.card;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.card.CardBannerRes;
import com.ecarxclub.app.model.card.CardCouponTypeRes;
import com.ecarxclub.app.model.card.CardListRes;

/**
 * Created by cwp
 * on 2019/4/23 9:42.
 */
public interface CardFgView extends BaseView {
    void onGetBannerResult(CardBannerRes cardBannerRes);
    void onCouponType(CardCouponTypeRes cardCouponTypeRes);
    void onCouponList(CardListRes cardListRes);

}
