package com.ecarxclub.app.presenter.RetrievePwd;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;

import java.util.Map;

/**
 * Created by cwp
 * on 2019/4/18 17:04.
 */
public class ForgetPwdPresenter extends BasePresenter<ForgetPwdView> {
    public ForgetPwdPresenter(ForgetPwdView baseView) {
        super(baseView);
    }

    //获取验证码
    public void getForgetCode(String mobile){

        addDisposable(apiServer.getForgetCode(mobile), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onGetCode(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //指定手机号  领取验证码
    public void getZdPhoneCode(String mobile,String id){

        addDisposable(apiServer.getZdPhoneCode(mobile,id), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onGetPhoneCode(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }



    //校验验证码是否正确
    public void getCodeIsRight(String mobile,String code){
        addDisposable(apiServer.getCodeIsRight(mobile,code), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onCodeIsRight(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //指定手机号领取卡券
    public void getCoupon(Map<String,String> map) {
        addDisposable(apiServer.getCoupone(map), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onGetCoupone(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

}
