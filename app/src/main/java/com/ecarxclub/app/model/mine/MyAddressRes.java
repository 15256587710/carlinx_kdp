package com.ecarxclub.app.model.mine;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cwp
 * on 2019/4/23 15:10.
 */
public class MyAddressRes implements Serializable{

    /**
     * success : true
     * msg : success
     * data : {"pageNum":1,"pageSize":8,"total":-1,"pages":0,"list":[{"id":"318096173913214976","createTime":"2019-04-23 00:00:00","userId":"316268752222162944","addrUserName":"chenwp","addrUserMobile":"15256587710","province":"浙江省","city":"杭州市","region":"滨江区","address":"白金海岸1111","enable":true}]}
     */

    public boolean success;
    public String msg;
    public DataBean data;

    public static class DataBean implements Serializable{
        /**
         * pageNum : 1
         * pageSize : 8
         * total : -1
         * pages : 0
         * list : [{"id":"318096173913214976","createTime":"2019-04-23 00:00:00","userId":"316268752222162944","addrUserName":"chenwp","addrUserMobile":"15256587710","province":"浙江省","city":"杭州市","region":"滨江区","address":"白金海岸1111","enable":true}]
         */

        public int pageNum;
        public int pageSize;
        public int total;
        public int pages;
        public List<ListBean> list;

        public static class ListBean implements Serializable{
            /**
             * id : 318096173913214976
             * createTime : 2019-04-23 00:00:00
             * userId : 316268752222162944
             * addrUserName : chenwp
             * addrUserMobile : 15256587710
             * province : 浙江省
             * city : 杭州市
             * region : 滨江区
             * address : 白金海岸1111
             * enable : true
             */

            public String id;
            public String createTime;
            public String userId;
            public String addrUserName;
            public String addrUserMobile;
            public String province;
            public String city;
            public String region;
            public String address;
            public boolean enable;
            public boolean bind;
        }
    }
}
