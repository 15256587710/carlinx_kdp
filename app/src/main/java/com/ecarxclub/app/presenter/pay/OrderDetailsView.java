package com.ecarxclub.app.presenter.pay;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.mine.ConfirmRecriceRes;
import com.ecarxclub.app.model.mine.LogistiesListRes;
import com.ecarxclub.app.model.pay.PayOrderSuccessRes;

/**
 * Created by cwp
 * on 2019/5/8 13:46.
 */
public interface OrderDetailsView extends BaseView {
    void onCancelOrder(BaseMsgRes baseMsgRes);//取消订单
    void onLogistiesV(LogistiesListRes baseMsgRes);//物流
    void onPayOrder(PayOrderSuccessRes payOrderSuccess);//支付
    void onConfirmRecive(ConfirmRecriceRes baseMsgRes);//确认签收
}
