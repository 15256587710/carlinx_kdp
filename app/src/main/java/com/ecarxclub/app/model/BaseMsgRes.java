package com.ecarxclub.app.model;

/**
 * Created by cwp
 * on 2019/4/18 15:02.
 */
public class BaseMsgRes {
        /**
     * msg : 数据不全
     * data : null
     * success : false
     */

    private String msg;
    private Object data;
    private boolean success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
