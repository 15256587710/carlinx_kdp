package com.ecarxclub.app.model.login;

/**
 * Created by cwp
 * on 2019/7/18 11:11.
 */
public class LoginNewRes {

    /**
     * data : {"auth":{"token":"56B609BDDAE16FC66D810C4767FD8B5AEBA3328BFA22DE4D0FC6A2B706CAA359","userId":348887874025951232,"userName":"不要迷恋哥"},"success":true}
     * msg : success
     * success : true
     */

    public DataBean data;
    public String msg;
    public boolean success;

    public static class DataBean {
        /**
         * auth : {"token":"56B609BDDAE16FC66D810C4767FD8B5AEBA3328BFA22DE4D0FC6A2B706CAA359","userId":348887874025951232,"userName":"不要迷恋哥"}
         * success : true
         */

        public AuthBean auth;
        public boolean success;
        public long key;

        public static class AuthBean {
            /**
             * token : 56B609BDDAE16FC66D810C4767FD8B5AEBA3328BFA22DE4D0FC6A2B706CAA359
             * userId : 348887874025951232
             * userName : 不要迷恋哥
             */

            public String token;
            public long userId;
            public String userName;
        }
    }
}
