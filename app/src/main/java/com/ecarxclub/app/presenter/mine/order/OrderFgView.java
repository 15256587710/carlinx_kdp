package com.ecarxclub.app.presenter.mine.order;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.mine.MyOrderListRes;
import com.ecarxclub.app.model.pay.PayOrderSuccessRes;

/**
 * Created by cwp
 * on 2019/4/26 13:35.
 */
public interface OrderFgView extends BaseView {
    void onOrderList(MyOrderListRes baseMsgRes);

    void onPayOrder(PayOrderSuccessRes payOrderSuccess);
}
