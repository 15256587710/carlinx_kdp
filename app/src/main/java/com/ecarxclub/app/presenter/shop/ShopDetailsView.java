package com.ecarxclub.app.presenter.shop;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.shop.ShopDetailsBannerRes;
import com.ecarxclub.app.model.shop.ShopDetailsRes;

/**
 * Created by cwp
 * on 2019/4/22 17:29.
 */
public interface ShopDetailsView extends BaseView {
    void onBannerDetails(ShopDetailsBannerRes baseMsgRes);

    //商品详情
    void onShopDetails(ShopDetailsRes baseMsgRes);
}
