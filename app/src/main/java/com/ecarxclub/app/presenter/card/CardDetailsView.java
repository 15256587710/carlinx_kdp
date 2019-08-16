package com.ecarxclub.app.presenter.card;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.card.CardDetailsRes;

/**
 * Created by cwp
 * on 2019/4/30 16:32.
 */
public interface CardDetailsView extends BaseView {
    void onCardDetails(CardDetailsRes baseMsgRes);
    void onGetCoupone(BaseMsgRes baseMsgRes);
}
