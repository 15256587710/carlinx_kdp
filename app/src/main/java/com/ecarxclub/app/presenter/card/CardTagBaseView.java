package com.ecarxclub.app.presenter.card;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.card.CardCouponTypeRes;

/**
 * Created by cwp
 * on 2019/4/25 11:43.
 */
public interface CardTagBaseView extends BaseView {
    void onGetCardTags(CardCouponTypeRes cardCouponTypeRes);
}
