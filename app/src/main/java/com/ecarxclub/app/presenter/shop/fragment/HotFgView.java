package com.ecarxclub.app.presenter.shop.fragment;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.shop.fgshop.ShopListRes;

/**
 * Created by cwp
 * on 2019/4/29 11:27.
 */
public interface HotFgView extends BaseView {
    void onGetHotList(ShopListRes shopListRes);
}
