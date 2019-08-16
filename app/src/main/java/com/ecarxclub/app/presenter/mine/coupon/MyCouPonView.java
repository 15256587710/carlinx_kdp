package com.ecarxclub.app.presenter.mine.coupon;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.mine.MyCouponRes;

/**
 * Created by cwp
 * on 2019/4/24 14:31.
 */
public interface MyCouPonView extends BaseView {
    void onListResult(MyCouponRes couponRes);
}
