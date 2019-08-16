package com.ecarxclub.app.presenter.home;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.home.car.CarInfoRes;
import com.ecarxclub.app.model.home.car.CarStyleRes;

/**
 * Created by cwp
 * on 2019/7/22 13:49.
 */
public interface ImprovingCarView extends BaseView {
    void onCarInfo(CarInfoRes baseMsgRes);
    void onUpdateInfo(BaseMsgRes baseMsgRes);
    void onCarStyle(CarStyleRes baseMsgRes);
    void onCarColor(CarStyleRes carStyleRes);
}
