package com.ecarxclub.app.model.card;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cwp
 * on 2019/4/25 14:05.
 */
public class CardListRes implements Serializable{


    /**
     * success : true
     * msg : success
     * data : {"pageNum":1,"pageSize":8,"total":-1,"pages":0,"list":[{"id":"318483144766525440","createTime":"2019-04-24 17:39:24","typeId":"306468775199379456","tagId":"315918759024005120","couponName":"淘宝代金券","couponTitle":"敬请期待","sellerId":"318481619151360000","remark":"<p>敬请期待<\/p>","limitTimeStart":"2019-04-24 00:00:00","limitTimeEnd":"2020-04-29 00:00:00","limitRegion":"330000-330100-330102","limitRemark":"","couponAmount":100,"couponStock":0,"sort":1,"enable":true,"seller":{"id":"318481619151360000","createTime":"2019-04-24 17:33:21","sellerLogo":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-logo/20190424318481599568154624.png","sellerName":"淘宝","sellerAppLogo":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-app-logo/20190424318481610787917824.png","enable":true},"type":{"id":"306468775199379456","createTime":"2019-03-22 00:00:00","typeName":"消费类","typeIcon":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-coupon-type/20190424318482574257295360.png","enable":true},"targs":{"id":"315918759024005120","createTime":"2019-04-17 15:49:27","tagName":"折扣券3","tagIcon":"","enable":true}},{"id":"306469382639456256","createTime":"2019-03-22 00:00:00","typeId":"306468775199379456","tagId":"293088549065068670","couponName":"米其林轮胎","couponTitle":"超值静音","sellerId":"293088549065068667","remark":"<p>优惠券说明<\/p>","limitTimeStart":"2019-03-05 00:00:00","limitTimeEnd":"2019-03-31 00:00:00","limitRegion":"3234-32341","limitRemark":"不可与其他优惠同时使用","couponAmount":100,"couponStock":0,"sort":1,"enable":true,"seller":{"id":"293088549065068667","createTime":"2019-02-14 00:00:00","sellerLogo":"http://app.carlinx.cn:80/yxb/api/static/files/Seller-logo/20190411/313756713507491840.png","sellerName":"途虎","sellerAppLogo":"http://app.carlinx.cn:80/yxb/api/static/files/Seller-app-logo/20190411/313756773616062464.jpg","enable":true},"type":{"id":"306468775199379456","createTime":"2019-03-22 00:00:00","typeName":"消费类","typeIcon":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-coupon-type/20190424318482574257295360.png","enable":true},"targs":{"id":"293088549065068670","createTime":"2019-02-14 00:00:00","tagName":"折扣券1","tagIcon":"http://localhost:8080/advert_war_exploded/api/static/files/seller-coupon-tags/20190321/306116393693220864.png","enable":true}},{"id":"293088549065068668","createTime":"2019-02-14 00:00:00","typeId":"293088549065068669","tagId":"306147577848532992","couponName":"美孚超值小保养9.8折券","couponTitle":"附赠24小时救援服务","sellerId":"293088549065068667","remark":"很好的券remarkdddd","limitTimeStart":"2019-02-28 00:00:00","limitTimeEnd":"2019-03-01 00:00:00","limitRegion":"3234-32341","limitRemark":"不能与其他优惠券同时使用","couponAmount":1,"couponStock":22,"sort":1,"enable":true,"seller":{"id":"293088549065068667","createTime":"2019-02-14 00:00:00","sellerLogo":"http://app.carlinx.cn:80/yxb/api/static/files/Seller-logo/20190411/313756713507491840.png","sellerName":"途虎","sellerAppLogo":"http://app.carlinx.cn:80/yxb/api/static/files/Seller-app-logo/20190411/313756773616062464.jpg","enable":true},"type":{"id":"293088549065068669","createTime":"2019-02-14 00:00:00","typeName":"金融类","typeIcon":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-coupon-type/20190424318482537502609408.png","enable":true},"targs":{"id":"306147577848532992","createTime":"2019-03-21 00:00:00","tagName":"折扣券2","tagIcon":"http://localhost:8080/advert_war_exploded/api/static/files/seller-coupon-tags/20190321/306147574228848640.png","enable":true}}]}
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
         * list : [{"id":"318483144766525440","createTime":"2019-04-24 17:39:24","typeId":"306468775199379456","tagId":"315918759024005120","couponName":"淘宝代金券","couponTitle":"敬请期待","sellerId":"318481619151360000","remark":"<p>敬请期待<\/p>","limitTimeStart":"2019-04-24 00:00:00","limitTimeEnd":"2020-04-29 00:00:00","limitRegion":"330000-330100-330102","limitRemark":"","couponAmount":100,"couponStock":0,"sort":1,"enable":true,"seller":{"id":"318481619151360000","createTime":"2019-04-24 17:33:21","sellerLogo":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-logo/20190424318481599568154624.png","sellerName":"淘宝","sellerAppLogo":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-app-logo/20190424318481610787917824.png","enable":true},"type":{"id":"306468775199379456","createTime":"2019-03-22 00:00:00","typeName":"消费类","typeIcon":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-coupon-type/20190424318482574257295360.png","enable":true},"targs":{"id":"315918759024005120","createTime":"2019-04-17 15:49:27","tagName":"折扣券3","tagIcon":"","enable":true}},{"id":"306469382639456256","createTime":"2019-03-22 00:00:00","typeId":"306468775199379456","tagId":"293088549065068670","couponName":"米其林轮胎","couponTitle":"超值静音","sellerId":"293088549065068667","remark":"<p>优惠券说明<\/p>","limitTimeStart":"2019-03-05 00:00:00","limitTimeEnd":"2019-03-31 00:00:00","limitRegion":"3234-32341","limitRemark":"不可与其他优惠同时使用","couponAmount":100,"couponStock":0,"sort":1,"enable":true,"seller":{"id":"293088549065068667","createTime":"2019-02-14 00:00:00","sellerLogo":"http://app.carlinx.cn:80/yxb/api/static/files/Seller-logo/20190411/313756713507491840.png","sellerName":"途虎","sellerAppLogo":"http://app.carlinx.cn:80/yxb/api/static/files/Seller-app-logo/20190411/313756773616062464.jpg","enable":true},"type":{"id":"306468775199379456","createTime":"2019-03-22 00:00:00","typeName":"消费类","typeIcon":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-coupon-type/20190424318482574257295360.png","enable":true},"targs":{"id":"293088549065068670","createTime":"2019-02-14 00:00:00","tagName":"折扣券1","tagIcon":"http://localhost:8080/advert_war_exploded/api/static/files/seller-coupon-tags/20190321/306116393693220864.png","enable":true}},{"id":"293088549065068668","createTime":"2019-02-14 00:00:00","typeId":"293088549065068669","tagId":"306147577848532992","couponName":"美孚超值小保养9.8折券","couponTitle":"附赠24小时救援服务","sellerId":"293088549065068667","remark":"很好的券remarkdddd","limitTimeStart":"2019-02-28 00:00:00","limitTimeEnd":"2019-03-01 00:00:00","limitRegion":"3234-32341","limitRemark":"不能与其他优惠券同时使用","couponAmount":1,"couponStock":22,"sort":1,"enable":true,"seller":{"id":"293088549065068667","createTime":"2019-02-14 00:00:00","sellerLogo":"http://app.carlinx.cn:80/yxb/api/static/files/Seller-logo/20190411/313756713507491840.png","sellerName":"途虎","sellerAppLogo":"http://app.carlinx.cn:80/yxb/api/static/files/Seller-app-logo/20190411/313756773616062464.jpg","enable":true},"type":{"id":"293088549065068669","createTime":"2019-02-14 00:00:00","typeName":"金融类","typeIcon":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-coupon-type/20190424318482537502609408.png","enable":true},"targs":{"id":"306147577848532992","createTime":"2019-03-21 00:00:00","tagName":"折扣券2","tagIcon":"http://localhost:8080/advert_war_exploded/api/static/files/seller-coupon-tags/20190321/306147574228848640.png","enable":true}}]
         */

        public int pageNum;
        public int pageSize;
        public int total;
        public int pages;
        public List<ListBean> list;

        public static class ListBean implements Serializable{
            /**
             * id : 318483144766525440
             * createTime : 2019-04-24 17:39:24
             * typeId : 306468775199379456
             * tagId : 315918759024005120
             * couponName : 淘宝代金券
             * couponTitle : 敬请期待
             * sellerId : 318481619151360000
             * remark : <p>敬请期待</p>
             * limitTimeStart : 2019-04-24 00:00:00
             * limitTimeEnd : 2020-04-29 00:00:00
             * limitRegion : 330000-330100-330102
             * limitRemark :
             * couponAmount : 100
             * couponStock : 0
             * sort : 1
             * enable : true
             * seller : {"id":"318481619151360000","createTime":"2019-04-24 17:33:21","sellerLogo":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-logo/20190424318481599568154624.png","sellerName":"淘宝","sellerAppLogo":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-app-logo/20190424318481610787917824.png","enable":true}
             * type : {"id":"306468775199379456","createTime":"2019-03-22 00:00:00","typeName":"消费类","typeIcon":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-coupon-type/20190424318482574257295360.png","enable":true}
             * targs : {"id":"315918759024005120","createTime":"2019-04-17 15:49:27","tagName":"折扣券3","tagIcon":"","enable":true}
             */

            public String id;
            public String createTime;
            public String typeId;
            public String tagId;
            public String couponName;
            public String couponTitle;
            public String sellerId;
            public String remark;
            public String limitTimeStart;
            public String limitTimeEnd;
            public String limitRegion;
            public String limitRemark;
            public int couponAmount;
            public int couponStock;
            public int sort;
            public boolean enable;
            public SellerBean seller;
            public TypeBean type;
            public TargsBean targs;

            public static class SellerBean implements Serializable{
                /**
                 * id : 318481619151360000
                 * createTime : 2019-04-24 17:33:21
                 * sellerLogo : https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-logo/20190424318481599568154624.png
                 * sellerName : 淘宝
                 * sellerAppLogo : https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-app-logo/20190424318481610787917824.png
                 * enable : true
                 */

                public String id;
                public String createTime;
                public String sellerLogo;
                public String sellerName;
                public String sellerAppLogo;
                public boolean enable;
            }

            public static class TypeBean implements Serializable{
                /**
                 * id : 306468775199379456
                 * createTime : 2019-03-22 00:00:00
                 * typeName : 消费类
                 * typeIcon : https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-coupon-type/20190424318482574257295360.png
                 * enable : true
                 */

                public String id;
                public String createTime;
                public String typeName;
                public String typeIcon;
                public boolean enable;
            }

            public static class TargsBean implements Serializable{
                /**
                 * id : 315918759024005120
                 * createTime : 2019-04-17 15:49:27
                 * tagName : 折扣券3
                 * tagIcon :
                 * enable : true
                 */

                public String id;
                public String createTime;
                public String tagName;
                public String tagIcon;
                public boolean enable;
            }
        }
    }
}
