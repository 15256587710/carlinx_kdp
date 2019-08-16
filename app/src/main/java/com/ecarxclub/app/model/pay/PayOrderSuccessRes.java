package com.ecarxclub.app.model.pay;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cwp
 * on 2019/5/7 15:39.
 */
public class PayOrderSuccessRes {
    /**
     * data : {"appid":"wx7c0e5c74a07e5d7d","noncestr":"e3da71271401485da73b6f0935b00ebb","package":"Sign=WXPay","partnerid":"1531253881","prepayid":"wx0716162637534057870c3eda3907404456","sign":"1BD0663A50401D0CFCD0D00775FBFAAA","timestamp":"1557216986"}
     * msg : success
     * success : true
     */

    public DataBean data;
    public String msg;
    public boolean success;

    public static class DataBean {
        /**
         * appid : wx7c0e5c74a07e5d7d
         * noncestr : e3da71271401485da73b6f0935b00ebb
         * package : Sign=WXPay
         * partnerid : 1531253881
         * prepayid : wx0716162637534057870c3eda3907404456
         * sign : 1BD0663A50401D0CFCD0D00775FBFAAA
         * timestamp : 1557216986
         */

        public String appid;
        public String noncestr;
        @SerializedName("package")
        public String packageX;
        public String partnerid;
        public String prepayid;
        public String sign;
        public String timestamp;



        //支付宝
        public String payStr;


        //银联
        public String accessType;
        public String bizType;
        public String certId;
        public String encoding;
        public String merId;
        public String orderId;
        public String respCode;
        public String respMsg;
        public String signMethod;
        public String signature;
        public String tn;
        public String txnSubType;
        public String txnTime;
        public String txnType;
        public String version;
    }

    /**
     * data : {"payStr":"alipay_sdk=alipay-sdk-java-3.6.0.ALL&app_id=2019032963707863&biz_content=%7B%22body%22%3A%22test*1%22%2C%22out_trade_no%22%3A%22323163292258406400%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22test%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=JSON&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Ftest.carlinx.cn%2Fyxb%2Fapi%2Fpay%2Fnotify%2Falipay&sign=Xjjx8uWvUt0cHJzBW4c8qWCPlMwXLYCPeN8kNA3QD8r%2BSHowqOyFzCQF%2BrwNoDjjDK1sLaSAoyM9rEmyA%2Ba9A6hDa%2B8iTKUwQ7hDCkF0QZD670%2BghePusoW5RzUwn%2Bcg01H58X2OSAtUplCGV%2FjhHHo%2BAoceL6dkATbIlGxvySTimMuky6WRw04CGRI0GbX2pYhfNJwPvOmZYZxGsxOdbIdKYCDg3TOvfsDn6%2F1VBL1pdpqOcmfGULwXdgM4HxMVYugFzK1I7mxnXfZzBsClDYtiYHQ6WBHrocym67SiLHztozAua2jMb%2FoDT1WI1%2B0nCWRwQWn%2B73WFn24batcrvQ%3D%3D&sign_type=RSA2&timestamp=2019-05-07+15%3A36%3A38&version=1.0"}
     * msg : success
     * success : true
     */


}
