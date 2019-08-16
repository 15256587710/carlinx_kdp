package com.ecarxclub.app.model.event;

/**
 * on 2018/06/27
 * 银联支付
 */

public class EventMessagePay {

    public int action;
    public Object status;
    public Object object;
    public Object orderType;

    public EventMessagePay(int action, Object object) {
        this.action = action;
        this.object = object;
    }
    public EventMessagePay(int action, Object object, Object status) {
        this.action = action;
        this.object = object;
        this.status=status;
    }
    public EventMessagePay(int action, Object object, Object status, Object oType) {
        this.action = action;
        this.object = object;
        this.status=status;
        this.orderType=oType;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "action=" + action +
                ", object=" + object +
                "status=" + status +
                '}';
    }
}
