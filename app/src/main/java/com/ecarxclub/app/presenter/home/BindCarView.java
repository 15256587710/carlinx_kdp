package com.ecarxclub.app.presenter.home;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;

/**
 * Created by cwp
 * on 2019/5/6 11:10.
 */
public interface BindCarView extends BaseView{
    void onBindCar(BaseMsgRes baseMsgRes);
    void onBindCarCode(BaseMsgRes baseMsgRes);
}
