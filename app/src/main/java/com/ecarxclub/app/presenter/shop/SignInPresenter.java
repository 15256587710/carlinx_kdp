package com.ecarxclub.app.presenter.shop;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.shop.fgshop.ShopTagsRes;
import com.ecarxclub.app.model.shop.integral.SignInRes;
import com.ecarxclub.app.model.shop.integral.SignInRewordRes;

/**
 * Created by cwp
 * on 2019/7/10 16:09.
 */
public class SignInPresenter extends BasePresenter<SignInView> {
    public SignInPresenter(SignInView baseView) {
        super(baseView);
    }
    //获取签到积分  签到
    public void getCheckScore(){
        addDisposable(apiServer.getCheckScore(), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onSignIn(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //获取签到记录
    public void getSignInP(){
        addDisposable(apiServer.getSigninRecode(), new BaseObserver<SignInRes>(baseView) {
            @Override
            public void onSuccess(SignInRes o) {
                baseView.onSignInRecode(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
    //连续签到奖励
    public void getSignInReword(){
        addDisposable(apiServer.getSigninReword(), new BaseObserver<SignInRewordRes>(baseView) {
            @Override
            public void onSuccess(SignInRewordRes o) {
                baseView.onSignInReword(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //领取签到奖励
    public void getReciveReword(String id){
        addDisposable(apiServer.getReciveReword(id), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onGetReword(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //获取用户标签
    public void getShopTag(){
        addDisposable(apiServer.getShopTag(), new BaseObserver<ShopTagsRes>(baseView) {
            @Override
            public void onSuccess(ShopTagsRes o) {
                baseView.onGetShopTag(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

}
