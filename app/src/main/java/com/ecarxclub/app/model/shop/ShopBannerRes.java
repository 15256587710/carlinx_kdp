package com.ecarxclub.app.model.shop;

import java.util.List;

/**
 * Created by cwp
 * on 2019/7/15 11:44.
 * 积分商城  banner，tag
 */
public class ShopBannerRes {

    /**
     * data : {"banner":[{"bannerType":2,"createTime":"2019-07-15 09:25:38","enable":true,"id":"123","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-banner/20190425318807033757437952.png","itemId":123,"jump":"1","navPath":"https://www.baidu.com","sort":9,"title":"商城banner测试1"},{"bannerType":2,"createTime":"2019-07-15 09:26:29","enable":true,"id":"124","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-banner/20190426319165057407782912.jpg","itemId":123,"jump":"2","navPath":"https://www.baidu.com","sort":8,"title":"商城banner测试2"}],"icon":[{"createTime":"2019-07-15 09:24:12","delete":false,"id":"125","imgPath":"www.baidu.com","interLink":"https://www.baidu.com","itemId":5454,"jump":"3","sort":3,"title":"icon3"},{"createTime":"2019-07-15 09:23:35","delete":false,"id":"124","imgPath":"www.baidu.com","interLink":"https://www.baidu.com","itemId":4343,"jump":"2","sort":2,"title":"icon2"},{"createTime":"2019-07-15 09:22:55","delete":false,"id":"123","imgPath":"www.baidu.com","interLink":"https://www.baidu.com","itemId":63817,"jump":"1","sort":1,"title":"icon1"}]}
     * msg : success
     * success : true
     */

    public DataBean data;
    public String msg;
    public boolean success;

    public static class DataBean {
        public List<BannerBean> banner;
        public List<IconBean> icon;

        public static class BannerBean {
            /**
             * bannerType : 2
             * createTime : 2019-07-15 09:25:38
             * enable : true
             * id : 123
             * imgPath : https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-banner/20190425318807033757437952.png
             * itemId : 123
             * jump : 1
             * navPath : https://www.baidu.com
             * sort : 9
             * title : 商城banner测试1
             */

            public int bannerType;
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

        public static class IconBean {
            /**
             * createTime : 2019-07-15 09:24:12
             * delete : false
             * id : 125
             * imgPath : www.baidu.com
             * interLink : https://www.baidu.com
             * itemId : 5454
             * jump : 3
             * sort : 3
             * title : icon3
             */

            public String createTime;
            public boolean delete;
            public String id;
            public String imgPath;
            public String interLink;
            public int itemId;
            public String jump;
            public int sort;
            public String title;
            public String shareImage;//分享图片
            public String shareContent;//分享内容
            public String shareTitle;//分享标题
            public String shareInterLink;//分享链接
        }
    }
}
