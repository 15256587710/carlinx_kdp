package com.ecarxclub.app.model.shop.integral;

import java.util.List;

/**
 * Created by cwp
 * on 2019/7/16 10:42.
 */
public class SignInRes {

    /**
     * data : {"clockView":[{"clock":false,"date":"2019-07-16 10:41:36","integral":2,"today":true},{"clock":false,"date":"2019-07-17 10:41:36","integral":5,"today":false},{"clock":false,"date":"2019-07-18 10:41:36","integral":10,"today":false},{"clock":false,"date":"2019-07-19 10:41:36","integral":6,"today":false},{"clock":false,"date":"2019-07-20 10:41:36","integral":8,"today":false},{"clock":false,"date":"2019-07-21 10:41:36","integral":10,"today":false},{"clock":false,"date":"2019-07-22 10:41:36","integral":10,"today":false}],"days":1,"integral":10749,"tip":"连续签到7日后可每天获得10积分","today":false}
     * msg : success
     * success : true
     */

    public DataBean data;
    public String msg;
    public boolean success;

    public static class DataBean {
        /**
         * clockView : [{"clock":false,"date":"2019-07-16 10:41:36","integral":2,"today":true},{"clock":false,"date":"2019-07-17 10:41:36","integral":5,"today":false},{"clock":false,"date":"2019-07-18 10:41:36","integral":10,"today":false},{"clock":false,"date":"2019-07-19 10:41:36","integral":6,"today":false},{"clock":false,"date":"2019-07-20 10:41:36","integral":8,"today":false},{"clock":false,"date":"2019-07-21 10:41:36","integral":10,"today":false},{"clock":false,"date":"2019-07-22 10:41:36","integral":10,"today":false}]
         * days : 1
         * integral : 10749.0
         * tip : 连续签到7日后可每天获得10积分
         * today : false
         */

        public int days;
        public double integral;
        public String tip;
        public boolean today;
        public List<ClockViewBean> clockView;

        public static class ClockViewBean {
            /**
             * clock : false
             * date : 2019-07-16 10:41:36
             * integral : 2
             * today : true
             */

            public boolean clock;
            public String date;
            public int integral;
            public boolean today;
        }
    }
}
