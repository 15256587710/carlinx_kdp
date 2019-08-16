package com.ecarxclub.app.model.shop.integral;

import java.util.List;

/**
 * Created by cwp
 * on 2019/6/17 10:49.
 */
public class WaterRes {


    /**
     * data : {"bubble":[{"createTime":"2019-07-15 15:13:01","expire":false,"expireTime":1563261162000,"id":"736218309480390204","integral":100,"integralCover":"邀请好友","integralType":14,"remark":"邀请好友奖励积分100","status":0,"userId":"316268752222162944"},{"createTime":"2019-07-15 14:14:51","expire":false,"expireTime":1563257691000,"id":"348147471073021952","integral":122,"integralCover":"行驶里程","integralType":9,"remark":"行驶里程奖励积分122","status":0,"userId":"316268752222162944"},{"createTime":"2019-07-15 14:14:51","expire":false,"expireTime":1563257691000,"id":"348147469856673792","integral":37,"integralCover":"行驶里程","integralType":9,"remark":"行驶里程奖励积分37","status":0,"userId":"316268752222162944"},{"createTime":"2019-07-15 14:14:50","expire":false,"expireTime":1563257690000,"id":"348147468338335744","integral":36,"integralCover":"行驶里程","integralType":9,"remark":"行驶里程奖励积分36","status":0,"userId":"316268752222162944"}],"integral":10682,"tip":"气泡超过24小时后将自动消失"}
     * msg : success
     * success : true
     */

    public DataBean data;
    public String msg;
    public boolean success;

    public static class DataBean {
        /**
         * bubble : [{"createTime":"2019-07-15 15:13:01","expire":false,"expireTime":1563261162000,"id":"736218309480390204","integral":100,"integralCover":"邀请好友","integralType":14,"remark":"邀请好友奖励积分100","status":0,"userId":"316268752222162944"},{"createTime":"2019-07-15 14:14:51","expire":false,"expireTime":1563257691000,"id":"348147471073021952","integral":122,"integralCover":"行驶里程","integralType":9,"remark":"行驶里程奖励积分122","status":0,"userId":"316268752222162944"},{"createTime":"2019-07-15 14:14:51","expire":false,"expireTime":1563257691000,"id":"348147469856673792","integral":37,"integralCover":"行驶里程","integralType":9,"remark":"行驶里程奖励积分37","status":0,"userId":"316268752222162944"},{"createTime":"2019-07-15 14:14:50","expire":false,"expireTime":1563257690000,"id":"348147468338335744","integral":36,"integralCover":"行驶里程","integralType":9,"remark":"行驶里程奖励积分36","status":0,"userId":"316268752222162944"}]
         * integral : 10682.0
         * tip : 气泡超过24小时后将自动消失
         */

        public double integral;
        public String tip;
        public List<BubbleBean> bubble;

        public static class BubbleBean {
            /**
             * createTime : 2019-07-15 15:13:01
             * expire : false
             * expireTime : 1563261162000
             * id : 736218309480390204
             * integral : 100
             * integralCover : 邀请好友
             * integralType : 14
             * remark : 邀请好友奖励积分100
             * status : 0
             * userId : 316268752222162944
             */

            public String createTime;
            public boolean expire;
            public long expireTime;
            public String id;
            public int integral;
            public String integralCover;
            public int integralType;
            public String remark;
            public int status;
            public String userId;
        }
    }
}
