package com.ecarxclub.app.model.pay;

/**
 * Created by cwp
 * on 2019/5/7 15:25.
 */
public class PayOrderRes {

    /**
     * success : true
     * msg : success
     * data : {"orderId":"323160022446116864","integral":2,"money":0.01,"sendFee":0}
     */

    public boolean success;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * orderId : 323160022446116864
         * integral : 2.0
         * money : 0.01
         * sendFee : 0.0
         */

        public String orderId;
        public double integral;
        public double money;
        public double sendFee;
    }
}
