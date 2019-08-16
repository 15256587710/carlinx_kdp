package com.ecarxclub.app.presenter.home;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.home.DrivingDetailsRes;

/**
 * Created by cwp
 * on 2019/5/6 15:23.
 */
public interface DrivingDetailsView extends BaseView {
    void onGetDrivingDetails(DrivingDetailsRes baseMsgRes);
}
