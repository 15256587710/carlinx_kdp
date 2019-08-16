package com.ecarxclub.app.presenter.shop;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.shop.integral.TaskListRes;
import com.ecarxclub.app.model.shop.integral.WaterRes;

/**
 * Created by cwp
 * on 2019/7/11 10:22.
 */
public class IntegralCenterPresenter extends BasePresenter<IntegralCenterView> {
    public IntegralCenterPresenter(IntegralCenterView baseView) {
        super(baseView);
    }
    //获取待领取积分
    public void getWaterPoint(){
        addDisposable(apiServer.getBubble(), new BaseObserver<WaterRes>(baseView) {
            @Override
            public void onSuccess(WaterRes o) {
                baseView.onGetPointList(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //获取任务列表
    public void getTaskListPre(){
        addDisposable(apiServer.getVersionList(), new BaseObserver<TaskListRes>(baseView) {
            @Override
            public void onSuccess(TaskListRes o) {
                baseView.onGetTaskList(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //获取待领取积分
    public void getPointId(String id){
        addDisposable(apiServer.getBubbleid(id), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onGetPointId(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //分享里程成功
    public void getShareSuccess(){
        addDisposable(apiServer.shareSuccess(), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onShareSuccess(o);
            }
            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
