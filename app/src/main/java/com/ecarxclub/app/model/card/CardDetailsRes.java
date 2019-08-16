package com.ecarxclub.app.model.card;

/**
 * Created by cwp
 * on 2019/6/4 14:08.
 */
public class CardDetailsRes {

    /**
     * success : true
     * msg : success
     * data : {"createTime":"2019-03-22 00:00:00","id":"306469382639456256","typeId":"306468775199379456","tagId":"293088549065068670","couponName":"米其林轮胎","couponTitle":"超值静音","sellerId":"293088549065068667","remark":"优惠券说明","limitTimeStart":"2019-03-05 00:00:00","limitTimeEnd":"2019-03-31 00:00:00","limitRegion":"3234-32341","limitRegionId":null,"limitRemark":"不可与其他优惠同时使用","couponAmount":100,"couponStock":2,"sort":1,"enable":true,"seller":null,"type":null,"targs":null}
     */

    public boolean success;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * createTime : 2019-03-22 00:00:00
         * id : 306469382639456256
         * typeId : 306468775199379456
         * tagId : 293088549065068670
         * couponName : 米其林轮胎
         * couponTitle : 超值静音
         * sellerId : 293088549065068667
         * remark : 优惠券说明
         * limitTimeStart : 2019-03-05 00:00:00
         * limitTimeEnd : 2019-03-31 00:00:00
         * limitRegion : 3234-32341
         * limitRegionId : null
         * limitRemark : 不可与其他优惠同时使用
         * couponAmount : 100
         * couponStock : 2
         * sort : 1
         * enable : true
         * seller : null
         * type : null
         * targs : null
         */

        public String createTime;
        public String id;
        public String typeId;
        public String tagId;
        public String couponName;
        public String couponTitle;
        public String sellerId;
        public String remark;
        public String limitTimeStart;
        public String limitTimeEnd;
        public String limitRegion;
        public Object limitRegionId;
        public String limitRemark;
        public int couponAmount;
        public int couponStock;//卡券库存属性
        public int sort;
        public boolean enable;
        public SellerBean seller;
        public TypeBean type;
        public TargsBean targs;

        public static class SellerBean{
            /**
             * createTime : 2019-04-25 15:43:16
             * enable : true
             * id : 318816303144112128
             * sellerAppLogo : https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-app-logo/20190425318816295795691520.png
             * sellerLogo : https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-logo/20190426319144402461265920.png
             * sellerName : 曹操出行
             */

            public String createTime;
            public boolean enable;
            public String id;
            public String sellerAppLogo;
            public String sellerLogo;
            public String sellerName;
        }

        public static class TargsBean{
            /**
             * createTime : 2019-04-25 15:43:58
             * enable : true
             * id : 318816478885449728
             * tagIcon :
             * tagName : 优惠券
             */

            public String createTime;
            public boolean enable;
            public String id;
            public String tagIcon;
            public String tagName;
        }

        public static class TypeBean{
            /**
             * createTime : 2019-04-26 15:18:26
             * enable : true
             * id : 319172443350634496
             * typeIcon : https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-coupon-type/20190426319172435939299328.svg
             * typeName : 出行类
             */

            public String createTime;
            public boolean enable;
            public String id;
            public String typeIcon;
            public String typeName;
        }
    }
}
