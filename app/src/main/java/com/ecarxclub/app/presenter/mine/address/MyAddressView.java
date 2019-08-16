package com.ecarxclub.app.presenter.mine.address;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.mine.MyAddressRes;

/**
 * Created by cwp
 * on 2019/4/23 15:11.
 */
public interface MyAddressView extends BaseView {
    //获取地址列表
    void onMyAddressList(MyAddressRes myAddressRes);

    //设置默认地址
    void onSetDefaultAddress(BaseMsgRes baseMsgRes);

    //删除地址
    void onDeleteAddress(BaseMsgRes baseMsgRes);
}
