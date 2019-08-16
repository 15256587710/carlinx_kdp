package com.ecarxclub.app.presenter.mine.address;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;

import java.util.Map;

/**
 * Created by cwp
 * on 2019/4/23 16:04.
 */
public class AddAddressPresenter extends BasePresenter<AddAddressView> {
    public AddAddressPresenter(AddAddressView baseView) {
        super(baseView);
    }

    //添加地址
    public void onAddAddress(Map<String, String> map){
        addDisposable(apiServer.addAddressResult(map), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onAddResult(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //编辑地址
    public void onEditAddress(Map<String, String> map){
        addDisposable(apiServer.updateAddressResult(map), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onAddResult(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

}
