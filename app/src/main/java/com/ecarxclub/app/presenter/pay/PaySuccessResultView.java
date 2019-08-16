package com.ecarxclub.app.presenter.pay;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;

/**
 * Created by cwp
 * on 2019/5/7 10:33.
 */
public interface PaySuccessResultView extends BaseView {
    void onPaySuccess(BaseMsgRes baseMsgRes);
}
