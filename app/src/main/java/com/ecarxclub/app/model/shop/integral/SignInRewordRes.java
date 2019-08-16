package com.ecarxclub.app.model.shop.integral;

import java.util.List;

/**
 * Created by cwp
 * on 2019/7/19 15:59.
 * 签到奖励
 */
public class SignInRewordRes {

    /**
     * data : [{"awardCover":"188积分","awardType":1,"canReceive":false,"createTime":"2019-07-18 17:38:15","days":30,"delete":false,"enable":true,"hasRecive":false,"id":"101","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/clockAward/20190719349620433173221376.png","integral":188},{"awardCover":"星巴克8折卡","awardType":2,"canReceive":false,"couponId":"6632732","createTime":"2019-07-18 17:38:11","days":100,"delete":false,"enable":true,"hasRecive":false,"id":"102","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/clockAward/20190719349620554577350656.png"},{"awardCover":"18888积分","awardType":3,"canReceive":false,"couponId":"6632732","createTime":"2019-07-18 17:44:26","days":200,"delete":false,"enable":true,"hasRecive":false,"id":"103","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/clockAward/20190719349620660470943744.png","integral":18888,"reserveCover":"爱奇艺会员"},{"awardCover":"神秘大礼包","awardType":4,"canReceive":false,"createTime":"2019-07-18 17:45:33","days":365,"delete":false,"enable":true,"hasRecive":false,"id":"104","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/clockAward/20190719349620748555522048.png"}]
     * msg : success
     * success : true
     */

    public String msg;
    public boolean success;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * awardCover : 188积分
         * awardType : 1
         * canReceive : false
         * createTime : 2019-07-18 17:38:15
         * days : 30
         * delete : false
         * enable : true
         * hasRecive : false
         * id : 101
         * imgPath : https://carlinx.oss-cn-hangzhou.aliyuncs.com/clockAward/20190719349620433173221376.png
         * integral : 188
         * couponId : 6632732
         * reserveCover : 爱奇艺会员
         */

        public String awardCover;
        public int awardType;
        public boolean canReceive;
        public String createTime;
        public int days;
        public boolean delete;
        public boolean enable;
        public boolean hasRecive;
        public String id;
        public String imgPath;
        public int integral;
        public String couponId;
        public String reserveCover;
        public String tip;
    }
}
