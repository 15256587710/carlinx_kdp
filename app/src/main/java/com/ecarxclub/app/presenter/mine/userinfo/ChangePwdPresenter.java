package com.ecarxclub.app.presenter.mine.userinfo;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;

/**
 * Created by cwp
 * on 2019/5/20 13:37.
 */
public class ChangePwdPresenter extends BasePresenter<ChangePwdView> {
    public ChangePwdPresenter(ChangePwdView baseView) {
        super(baseView);
    }

    //修改密码
    public void changeUserInfo(String oldpwd,String newpwd){
        addDisposable(apiServer.changePwd(oldpwd,newpwd), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onChangePwd(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

}
