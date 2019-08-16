package com.ecarxclub.app.presenter.home;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.home.MyDrivingListRes;

/**
 * Created by cwp
 * on 2019/4/22 16:05.
 */
public interface DrivingView extends BaseView {
    void onGetList(MyDrivingListRes baseMsgRes);
}
