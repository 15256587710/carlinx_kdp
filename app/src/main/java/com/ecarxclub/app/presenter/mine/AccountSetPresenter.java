package com.ecarxclub.app.presenter.mine;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;

/**
 * Created by cwp
 * on 2019/4/24 16:49.
 */
public class AccountSetPresenter extends BasePresenter<AccountSetView> {
    public AccountSetPresenter(AccountSetView baseView) {
        super(baseView);
    }

    //退出登录
    public void onLogOut(){
        addDisposable(apiServer.LogOut(), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onLogOutResult(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
