package com.ecarxclub.app.model.mine;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cwp
 * on 2019/7/26 14:07.
 * 确认收货
 */
public class ConfirmRecriceRes implements Serializable{

    /**
     * success : true
     * msg : success
     * data : {"integral":0,"banners":[{"createTime":"2019-07-26 13:32:19","id":"125","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-banner/20190426319165057407782912.jpg","jump":"1","itemId":123,"navPath":"https://www.baidu.com","title":"确认签收banner","enable":true,"sort":8,"bannerType":3},{"createTime":"2019-07-26 13:33:04","id":"126","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/poster/20190620339084232498483200.png","jump":"6","itemId":123,"navPath":"http://test.carlinx.cn/","title":"确认签收banner","enable":true,"sort":9,"bannerType":3}]}
     */

    public boolean success;
    public String msg;
    public DataBean data;

    public static class DataBean  implements Serializable{
        /**
         * integral : 0
         * banners : [{"createTime":"2019-07-26 13:32:19","id":"125","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-banner/20190426319165057407782912.jpg","jump":"1","itemId":123,"navPath":"https://www.baidu.com","title":"确认签收banner","enable":true,"sort":8,"bannerType":3},{"createTime":"2019-07-26 13:33:04","id":"126","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/poster/20190620339084232498483200.png","jump":"6","itemId":123,"navPath":"http://test.carlinx.cn/","title":"确认签收banner","enable":true,"sort":9,"bannerType":3}]
         */

        public int integral;
        public List<BannersBean> banners;

        public static class BannersBean  implements Serializable{
            /**
             * createTime : 2019-07-26 13:32:19
             * id : 125
             * imgPath : https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-banner/20190426319165057407782912.jpg
             * jump : 1
             * itemId : 123
             * navPath : https://www.baidu.com
             * title : 确认签收banner
             * enable : true
             * sort : 8
             * bannerType : 3
             */

            public String createTime;
            public String id;
            public String imgPath;
            public String jump;
            public int itemId;
            public String navPath;
            public String title;
            public boolean enable;
            public int sort;
            public int bannerType;
        }
    }
}
