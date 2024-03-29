package com.ecarxclub.app.base;

import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.utils.ToastUtils;
import com.google.gson.JsonParseException;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * 作者： ch
 * 时间： 2016/12/27.13:56
 * 描述：
 * 来源：
 */

public abstract class BaseObserver<T> extends DisposableObserver<T> {

    protected BaseView view;

//    private boolean isShowDialog;


    public BaseObserver(BaseView view) {
        this.view = view;
    }

    public BaseObserver(BaseView view, boolean isShowDialog) {
        this.view = view;
//        this.isShowDialog = isShowDialog;
    }

    @Override
    protected void onStart() {
        if (view != null) {// && isShowDialog
            view.showLoading();
        }
    }

    @Override
    public void onNext(T o) {
        onSuccess(o);
    }

    @Override
    public void onError(Throwable e) {
        if (view != null) {// && isShowDialog
            view.hideLoading();
        }
        BaseException be = null;
        if (e != null) {
            if(e.getMessage()!=null){
                ToastUtils.e("error  11+ ",""+e.getMessage().toString());
            }
            if (e instanceof BaseException) {
                be = (BaseException) e;
                //回调到view层 处理 或者根据项目情况处理
                if (view != null) {
                    ToastUtils.e("nnnnnn "+be.getErrorMsg(),"  "+be.getErrorCode());
                    view.onErrorCode(new BaseModel(be.getErrorCode(), be.getErrorMsg()));
                } else {
                    ToastUtils.e("mmmmmmmm ",""+be.getErrorMsg());
                    onError(be.getErrorMsg());
                }
            } else {
                if (e instanceof HttpException) {
                    if(((HttpException) e).code()==401){
                        EventBus.getDefault().post(new EventMessage(Constant.UN_LOGIN,Constant.UN_LOGIN_TOKEN));
                        //   HTTP错误
                        be = new BaseException(BaseException.BAD_NETWORK_MSG, e, BaseException.BAD_NETWORK);
                    }else if(((HttpException) e).code()==500){
                        be = new BaseException(BaseException.HTTP_BACK_MSG, e, BaseException.HTTP_BACK);
                    }else if (((HttpException) e).code()==404){
                        be = new BaseException(BaseException.HTTP_CONNECT_ERROR_MSG, e, BaseException.HTTP_CONNECT_ERROR);
                    }else {
                        be = new BaseException(BaseException.OTHER_MSG, e, BaseException.OTHER);
                    }
                } else if (e instanceof ConnectException
                        || e instanceof UnknownHostException) {
                    //   连接错误
                    be = new BaseException(BaseException.CONNECT_ERROR_MSG, e, BaseException.CONNECT_ERROR);
                } else if (e instanceof InterruptedIOException) {
                    //  连接超时
                    be = new BaseException(BaseException.CONNECT_TIMEOUT_MSG, e, BaseException.CONNECT_TIMEOUT);
                } else if (e instanceof JsonParseException
                        || e instanceof JSONException
                        || e instanceof ParseException) {
                    //  解析错误
                    be = new BaseException(BaseException.PARSE_ERROR_MSG, e, BaseException.PARSE_ERROR);
                } else {
                    be = new BaseException(BaseException.OTHER_MSG, e, BaseException.OTHER);
                }
            }
        } else {
            be = new BaseException(BaseException.OTHER_MSG, e, BaseException.OTHER);
        }
        onError(be.getErrorMsg());

    }

    @Override
    public void onComplete() {
        if (view != null) {// && isShowDialog
            view.hideLoading();
        }

    }


    public abstract void onSuccess(T o);

    public abstract void onError(String msg);


}
