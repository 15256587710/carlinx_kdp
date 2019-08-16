package com.ecarxclub.app.presenter.mine.message;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.mine.MessageRes;

/**
 * Created by cwp
 * on 2019/4/26 13:53.
 */
public class MessagePresenter extends BasePresenter<MessageView> {
    public MessagePresenter(MessageView baseView) {
        super(baseView);
    }

    //获取消息列表
    public void onMessageList(int index,int size){
        addDisposable(apiServer.getMessageList(index,size), new BaseObserver<MessageRes>(baseView) {
            @Override
            public void onSuccess(MessageRes o) {
                baseView.onGetMessageList(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }


    //已读消息
    public void onReadMsg(String id){
        addDisposable(apiServer.readMsg(id), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onReadMsg(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
