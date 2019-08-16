package com.ecarxclub.app.presenter.shop;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.mine.MyAddressRes;
import com.ecarxclub.app.model.pay.PayOrderRes;
import com.ecarxclub.app.model.pay.PayOrderSuccessRes;

/**
 * Created by cwp
 * on 2019/4/29 16:54.
 */
public interface ExchangeDetailsView extends BaseView {
    void onDataResult(BaseMsgRes baseMsgRes);
    //获取地址列表
    void onMyAddressList(MyAddressRes myAddressRes);

    void onGetUserScore(BaseMsgRes baseMsgRes);//用户积分

    void onUpOrder(PayOrderRes baseMsgRes);

    void onPayOrder(PayOrderSuccessRes payOrderSuccess);
}
