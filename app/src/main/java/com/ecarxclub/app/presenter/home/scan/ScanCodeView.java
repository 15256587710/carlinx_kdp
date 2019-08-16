package com.ecarxclub.app.presenter.home.scan;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;

/**
 * Created by cwp
 * on 2019/7/24 11:28.
 */
public interface ScanCodeView extends BaseView {
    void onCarLogin(BaseMsgRes baseMsgRes);
}
