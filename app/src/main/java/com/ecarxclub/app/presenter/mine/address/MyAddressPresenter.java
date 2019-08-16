package com.ecarxclub.app.presenter.mine.address;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.mine.MyAddressRes;

/**
 * Created by cwp
 * on 2019/4/23 15:12.
 */
public class MyAddressPresenter extends BasePresenter<MyAddressView> {
    public MyAddressPresenter(MyAddressView baseView) {
        super(baseView);
    }

    //我的地址列表
    public void getForgetCode(boolean count,int index,int size){
        addDisposable(apiServer.getAddressList(count,index,size), new BaseObserver<MyAddressRes>(baseView) {
            @Override
            public void onSuccess(MyAddressRes o) {
                baseView.onMyAddressList(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //设置默认地址
    public void setDefaultAddress(String id){
        addDisposable(apiServer.setDefaultAddress(id), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onSetDefaultAddress(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //删除地址
    public void deleteAddress(String id){
        addDisposable(apiServer.deleteAddress(id), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onSetDefaultAddress(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

}
