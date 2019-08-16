package com.ecarxclub.app.presenter.home;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.home.car.BindCarRes;

/**
 * Created by cwp
 * on 2019/5/6 11:10.
 */
public interface ChooseCarView extends BaseView{
    void onChooseV(BaseMsgRes baseMsgRes);
    void onGetAllCarV(BindCarRes bindCarRes);//所有车辆
}
