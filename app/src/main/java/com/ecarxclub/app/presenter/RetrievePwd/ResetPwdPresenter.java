package com.ecarxclub.app.presenter.RetrievePwd;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;

/**
 * Created by cwp
 * on 2019/4/18 18:12.
 */
public class ResetPwdPresenter extends BasePresenter<ResetPwdVIew> {

    public ResetPwdPresenter(ResetPwdVIew baseView) {
        super(baseView);
    }
    public void resetPwd(String pwd,String token){
        addDisposable(apiServer.resetPwd(pwd,token), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onResetPwdResult(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
