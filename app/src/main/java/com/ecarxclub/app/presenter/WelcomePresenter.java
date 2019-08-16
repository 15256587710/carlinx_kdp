package com.ecarxclub.app.presenter;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.home.AdvertisementRes;

/**
 * Created by cwp
 * on 2019/5/21 9:58.
 */
public class WelcomePresenter extends BasePresenter<WelcomeView> {

    public WelcomePresenter(WelcomeView baseView) {
        super(baseView);
    }


    //广告
    public void onGetShowAdvert(String type){
        addDisposable(apiServer.getAdvertise(type), new BaseObserver<AdvertisementRes>(baseView) {
            @Override
            public void onSuccess(AdvertisementRes o) {
                baseView.onGetPopShowType(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //广告点击
    public void onClickAdvert(String id,String advid){
        addDisposable(apiServer.onClickAdv(id,advid), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onClickAdvert(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    // 渠道下载量统计
    public void onCensus(String phoneid){
        addDisposable(apiServer.onCensus(phoneid), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onCensus(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

}
