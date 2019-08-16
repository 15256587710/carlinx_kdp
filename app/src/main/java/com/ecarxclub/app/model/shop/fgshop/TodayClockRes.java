package com.ecarxclub.app.model.shop.fgshop;

/**
 * Created by cwp
 * on 2019/4/26 16:14.
 */
public class TodayClockRes {

    /**
     * data : {"clockTime":1556264442000,"createTime":"2019-04-26 15:40:42","days":1,"id":"319178047251156992","integral":2,"userId":"316268752222162944"}
     * msg : success
     * success : true
     */

    public DataBean data;
    public String msg;
    public boolean success;

    public static class DataBean {
        /**
         * clockTime : 1556264442000
         * createTime : 2019-04-26 15:40:42
         * days : 1
         * id : 319178047251156992
         * integral : 2.0
         * userId : 316268752222162944
         */

        public long clockTime;
        public String createTime;
        public int days;
        public String id;
        public double integral;
        public String userId;
    }
}
