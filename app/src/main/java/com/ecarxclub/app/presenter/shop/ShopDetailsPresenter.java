package com.ecarxclub.app.presenter.shop;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.shop.ShopDetailsBannerRes;
import com.ecarxclub.app.model.shop.ShopDetailsRes;

/**
 * Created by cwp
 * on 2019/4/22 17:30.
 */
public class ShopDetailsPresenter extends BasePresenter<ShopDetailsView> {
    public ShopDetailsPresenter(ShopDetailsView baseView) {
        super(baseView);
    }

    //banner
    public void onGetBanner(String id){
        addDisposable(apiServer.getShopDetailsBanner(id), new BaseObserver<ShopDetailsBannerRes>(baseView) {
            @Override
            public void onSuccess(ShopDetailsBannerRes o) {
                baseView.onBannerDetails(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    // 获取商品详情
    public void onShopDetails(String id){
        addDisposable(apiServer.onShopDetails(id), new BaseObserver<ShopDetailsRes>(baseView) {
            @Override
            public void onSuccess(ShopDetailsRes o) {
                baseView.onShopDetails(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

}
