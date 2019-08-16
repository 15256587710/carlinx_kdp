package com.ecarxclub.app.presenter.shop.fragment;

import android.app.Activity;
import android.content.Context;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.shop.fgshop.ShopListRes;
import com.ecarxclub.app.utils.ToastUtils;

import java.util.Map;

/**
 * Created by cwp
 * on 2019/4/29 11:29.
 */
public class HotFgPresenter  extends BasePresenter<HotFgView> {
    Activity context;
    public HotFgPresenter(HotFgView baseView) {
        super(baseView);
    }
    public HotFgPresenter(HotFgView baseView, Activity context) {
        super(baseView);
        this.context=context;
    }
    //获取列表
    public void getShopList(Map<String ,String> map){
        addDisposable(apiServer.getShopList(map), new BaseObserver<ShopListRes>(baseView) {
            @Override
            public void onSuccess(final ShopListRes o) {
                if (baseView!=null){
                    baseView.onGetHotList(o);
                }else {
                    ToastUtils.i("空指针 ",""+baseView);
                }
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
