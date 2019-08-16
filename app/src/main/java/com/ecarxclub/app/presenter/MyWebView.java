package com.ecarxclub.app.presenter;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;

/**
 * Created by cwp
 * on 2019/5/21 11:15.
 */
public interface MyWebView extends BaseView {
    void onResult(BaseMsgRes baseMsgRes);
}
