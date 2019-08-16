package com.ecarxclub.app.presenter.mine.address;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;

/**
 * Created by cwp
 * on 2019/4/23 16:03.
 */
public interface AddAddressView extends BaseView {
    void onAddResult(BaseMsgRes baseMsgRes);
    void onUpdateResult(BaseMsgRes baseMsgRes);
}
