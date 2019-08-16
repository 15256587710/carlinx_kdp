package com.ecarxclub.app.presenter.mine.order;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;

/**
 * Created by cwp
 * on 2019/4/26 11:58.
 */
public interface MyOrderBaseView extends BaseView {
    void onMyOrderResult(BaseMsgRes baseMsgRes);
}
