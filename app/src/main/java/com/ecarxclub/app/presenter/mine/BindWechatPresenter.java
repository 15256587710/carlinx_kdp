package com.ecarxclub.app.presenter.mine;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;

/**
 * Created by cwp
 * on 2019/7/19 9:42.
 */
public class BindWechatPresenter extends BasePresenter<BindWechatView> {
    public BindWechatPresenter(BindWechatView baseView) {
        super(baseView);
    }

    //
    public void bindWechat(String code){
        addDisposable(apiServer.bindWechat(code), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onBindWechat(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
