package com.ecarxclub.app.model.mine;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cwp
 * on 2019/4/24 14:30.
 */
public class MyCouponRes implements Serializable{


    /**
     * data : {"list":[{"coupon":{"couponAmount":100,"couponName":"曹操优惠券","couponStock":0,"couponTitle":"8.8折券","createTime":"2019-04-26 13:24:58","enable":true,"id":"319143886465404928","limitRegion":"浙江省-杭州市","limitRegionId":"330100","limitRemark":"最高抵扣5元，除营口、大连、无锡地区均可使用","limitTimeEnd":"2020-04-26 00:00:00","limitTimeStart":"2019-04-26 00:00:00","remark":"<p>领取成功后，请使用领取手机号登陆曹操出行进行使用<\/p>","seller":{"createTime":"2019-04-25 15:43:16","enable":true,"id":"318816303144112128","sellerAppLogo":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-app-logo/20190425318816295795691520.png","sellerLogo":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-logo/20190426319144402461265920.png","sellerName":"曹操出行"},"sellerId":"318816303144112128","sort":3,"tagId":"318816478885449728","targs":{"createTime":"2019-04-25 15:43:58","enable":true,"id":"318816478885449728","tagIcon":"","tagName":"优惠券"},"type":{"createTime":"2019-04-26 15:18:26","enable":true,"id":"319172443350634496","typeIcon":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-coupon-type/20190426319172435939299328.svg","typeName":"出行类"},"typeId":"319172443350634496"},"couponId":"319143886465404928","couponStatus":1,"createTime":"2019-05-05 14:50:26","id":"322426886640766976","receiveMobile":"15256587710","receiveTime":"2019-05-05 14:50:26","userId":"316268752222162944"}],"pageNum":1,"pageSize":10,"pages":1,"total":1}
     * msg : success
     * success : true
     */

    public DataBean data;
    public String msg;
    public boolean success;

    public static class DataBean implements Serializable{
        /**
         * list : [{"coupon":{"couponAmount":100,"couponName":"曹操优惠券","couponStock":0,"couponTitle":"8.8折券","createTime":"2019-04-26 13:24:58","enable":true,"id":"319143886465404928","limitRegion":"浙江省-杭州市","limitRegionId":"330100","limitRemark":"最高抵扣5元，除营口、大连、无锡地区均可使用","limitTimeEnd":"2020-04-26 00:00:00","limitTimeStart":"2019-04-26 00:00:00","remark":"<p>领取成功后，请使用领取手机号登陆曹操出行进行使用<\/p>","seller":{"createTime":"2019-04-25 15:43:16","enable":true,"id":"318816303144112128","sellerAppLogo":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-app-logo/20190425318816295795691520.png","sellerLogo":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-logo/20190426319144402461265920.png","sellerName":"曹操出行"},"sellerId":"318816303144112128","sort":3,"tagId":"318816478885449728","targs":{"createTime":"2019-04-25 15:43:58","enable":true,"id":"318816478885449728","tagIcon":"","tagName":"优惠券"},"type":{"createTime":"2019-04-26 15:18:26","enable":true,"id":"319172443350634496","typeIcon":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-coupon-type/20190426319172435939299328.svg","typeName":"出行类"},"typeId":"319172443350634496"},"couponId":"319143886465404928","couponStatus":1,"createTime":"2019-05-05 14:50:26","id":"322426886640766976","receiveMobile":"15256587710","receiveTime":"2019-05-05 14:50:26","userId":"316268752222162944"}]
         * pageNum : 1
         * pageSize : 10
         * pages : 1
         * total : 1
         */

        public int pageNum;
        public int pageSize;
        public int pages;
        public int total;
        public List<ListBean> list;

        public static class ListBean implements Serializable{
            /**
             * coupon : {"couponAmount":100,"couponName":"曹操优惠券","couponStock":0,"couponTitle":"8.8折券","createTime":"2019-04-26 13:24:58","enable":true,"id":"319143886465404928","limitRegion":"浙江省-杭州市","limitRegionId":"330100","limitRemark":"最高抵扣5元，除营口、大连、无锡地区均可使用","limitTimeEnd":"2020-04-26 00:00:00","limitTimeStart":"2019-04-26 00:00:00","remark":"<p>领取成功后，请使用领取手机号登陆曹操出行进行使用<\/p>","seller":{"createTime":"2019-04-25 15:43:16","enable":true,"id":"318816303144112128","sellerAppLogo":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-app-logo/20190425318816295795691520.png","sellerLogo":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-logo/20190426319144402461265920.png","sellerName":"曹操出行"},"sellerId":"318816303144112128","sort":3,"tagId":"318816478885449728","targs":{"createTime":"2019-04-25 15:43:58","enable":true,"id":"318816478885449728","tagIcon":"","tagName":"优惠券"},"type":{"createTime":"2019-04-26 15:18:26","enable":true,"id":"319172443350634496","typeIcon":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-coupon-type/20190426319172435939299328.svg","typeName":"出行类"},"typeId":"319172443350634496"}
             * couponId : 319143886465404928
             * couponStatus : 1
             * createTime : 2019-05-05 14:50:26
             * id : 322426886640766976
             * receiveMobile : 15256587710
             * receiveTime : 2019-05-05 14:50:26
             * userId : 316268752222162944
             */

            public CouponBean coupon;
            public String couponId;
            public int couponStatus;
            public String createTime;
            public String id;
            public String receiveMobile;
            public String receiveTime;
            public String userId;

            public static class CouponBean implements Serializable{
                /**
                 * couponAmount : 100
                 * couponName : 曹操优惠券
                 * couponStock : 0
                 * couponTitle : 8.8折券
                 * createTime : 2019-04-26 13:24:58
                 * enable : true
                 * id : 319143886465404928
                 * limitRegion : 浙江省-杭州市
                 * limitRegionId : 330100
                 * limitRemark : 最高抵扣5元，除营口、大连、无锡地区均可使用
                 * limitTimeEnd : 2020-04-26 00:00:00
                 * limitTimeStart : 2019-04-26 00:00:00
                 * remark : <p>领取成功后，请使用领取手机号登陆曹操出行进行使用</p>
                 * seller : {"createTime":"2019-04-25 15:43:16","enable":true,"id":"318816303144112128","sellerAppLogo":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-app-logo/20190425318816295795691520.png","sellerLogo":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/Seller-logo/20190426319144402461265920.png","sellerName":"曹操出行"}
                 * sellerId : 318816303144112128
                 * sort : 3
                 * tagId : 318816478885449728
                 * targs : {"createTime":"2019-04-25 15:43:58","enable":true,"id":"318816478885449728","tagIcon":"","tagName":"优惠券"}
                 * type : {"createTime":"2019-04-26 15:18:26","enable":true,"id":"319172443350634496","typeIcon":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/seller-coupon-type/20190426319172435939299328.svg","typeName":"出行类"}
                 * typeId : 319172443350634496
                 */

                public int couponAmount;
                public String couponName;
                public int couponStock;
                public String couponTitle;
                public String createTime;
                public boolean enable;
                public String id;
                public String limitRegion;
                public String limitRegionId;
                public String limitRemark;
                public String limitTimeEnd;
                public String limitTimeStart;
                public String remark;
                public SellerBean seller;
                public String sellerId;
                public int sort;
                public String tagId;
                public TargsBean targs;
                public TypeBean type;
                public String typeId;

                public static class SellerBean implements Serializable{
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

                public static class TargsBean implements Serializable{
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

                public static class TypeBean implements Serializable{
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
    }
}
