package com.ecarxclub.app.presenter.shop;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.shop.integral.TaskListRes;
import com.ecarxclub.app.model.shop.integral.WaterRes;

/**
 * Created by cwp
 * on 2019/7/11 10:21.
 */
public interface IntegralCenterView extends BaseView {
    void onGetPointList(WaterRes waterRes);//获取签到积分
    void onGetTaskList(TaskListRes baseMsgRes);//任务
    void onGetPointId(BaseMsgRes baseMsgRes);//领取积分
    void onShareSuccess(BaseMsgRes baseMsgRes);//分享里程
}
