package com.ecarxclub.app.presenter.mine;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.UserInfoNewRes;
import com.ecarxclub.app.model.UserInfoRes;

/**
 * Created by cwp
 * on 2019/5/8 9:50.
 */
public class BaseMinePresenter extends BasePresenter<BaseMineView> {
    public BaseMinePresenter(BaseMineView baseView) {
        super(baseView);
    }

    //获取用户积分
    public void getUserScore(){
        addDisposable(apiServer.getUserScore(), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onGetUserScore(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //获取用户信息
    public void onGetUserInfo(){
        addDisposable(apiServer.getUserInfo(), new BaseObserver<UserInfoRes>(baseView) {
            @Override
            public void onSuccess(UserInfoRes o) {
                baseView.onUserInfo(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //获取用户信息
    public void onGetUserNewInfo(){
        addDisposable(apiServer.getUserNewInfo(), new BaseObserver<UserInfoNewRes>(baseView) {
            @Override
            public void onSuccess(UserInfoNewRes o) {
                baseView.onUserNewInfo(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

}
