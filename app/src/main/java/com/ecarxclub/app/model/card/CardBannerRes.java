package com.ecarxclub.app.model.card;

import java.util.List;

/**
 * Created by cwp
 * on 2019/4/22 18:16.
 * 卡券banner
 */
public class CardBannerRes {


    /**
     * data : {"list":[{"createTime":"2019-02-15 00:00:00","enable":true,"id":"293088549065068671","imgPath":"http://test.oss.carlinx.cn/seller-banner/20190628341935142325063680.png","itemId":123,"jump":"1","navPath":"https://www.baidu.com","sort":7,"title":"banner"},{"createTime":"2019-04-02 18:05:19","enable":true,"id":"310517132075077632","imgPath":"http://test.oss.carlinx.cn/seller-banner/20190628341928713597882368.png","itemId":123,"jump":"1","navPath":"https://www.baidu.com","sort":1,"title":"asdddd"}],"pageNum":1,"pageSize":2,"pages":1,"total":2}
     * msg : success
     * success : true
     */

    public DataBean data;
    public String msg;
    public boolean success;

    public static class DataBean {
        /**
         * list : [{"createTime":"2019-02-15 00:00:00","enable":true,"id":"293088549065068671","imgPath":"http://test.oss.carlinx.cn/seller-banner/20190628341935142325063680.png","itemId":123,"jump":"1","navPath":"https://www.baidu.com","sort":7,"title":"banner"},{"createTime":"2019-04-02 18:05:19","enable":true,"id":"310517132075077632","imgPath":"http://test.oss.carlinx.cn/seller-banner/20190628341928713597882368.png","itemId":123,"jump":"1","navPath":"https://www.baidu.com","sort":1,"title":"asdddd"}]
         * pageNum : 1
         * pageSize : 2
         * pages : 1
         * total : 2
         */

        public int pageNum;
        public int pageSize;
        public int pages;
        public int total;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * createTime : 2019-02-15 00:00:00
             * enable : true
             * id : 293088549065068671
             * imgPath : http://test.oss.carlinx.cn/seller-banner/20190628341935142325063680.png
             * itemId : 123
             * jump : 1
             * navPath : https://www.baidu.com
             * sort : 7
             * title : banner
             *  jump:跳转位置    1.不跳转     2.H5        3.卡券详情
                navPath:H5链接
                itemId:卡券子项Id
             */

            public String createTime;
            public boolean enable;
            public String id;
            public String imgPath;
            public int itemId;
            public String jump;
            public String navPath;
            public int sort;
            public String title;
        }
    }
}
