package com.ecarxclub.app.presenter.mine.message;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.mine.MessageRes;

/**
 * Created by cwp
 * on 2019/4/26 13:52.
 */
public interface MessageView extends BaseView {
    void onGetMessageList(MessageRes messageRes);
    void onReadMsg(BaseMsgRes baseMsgRes);
}
