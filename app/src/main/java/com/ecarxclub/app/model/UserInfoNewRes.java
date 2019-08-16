package com.ecarxclub.app.model;

/**
 * Created by cwp
 * on 2019/7/18 15:36.
 */
public class UserInfoNewRes {

    /**
     * success : true
     * msg : success
     * data : {"imgUrl":"http://test.oss.carlinx.cn/photo/20190712347062519954804736.jpg","nickName":"好喷墨摸摸","integral":10847,"expireTip":"0积分在12月31日过期","level":{"createTime":"2019-07-18 13:40:15","id":"103","memberIdentity":"3","memberName":"黄金会员","startIntegral":3001,"endIntegral":10000,"levelRemark":"升级铂金会员","sort":8,"delete":false}}
     */

    public boolean success;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * imgUrl : http://test.oss.carlinx.cn/photo/20190712347062519954804736.jpg
         * nickName : 好喷墨摸摸
         * integral : 10847.0
         * expireTip : 0积分在12月31日过期
         * level : {"createTime":"2019-07-18 13:40:15","id":"103","memberIdentity":"3","memberName":"黄金会员","startIntegral":3001,"endIntegral":10000,"levelRemark":"升级铂金会员","sort":8,"delete":false}
         */

        public String imgUrl;
        public String nickName;
        public double integral;
        public String expireTip;
        public LevelBean level;
        public double levelIntegral;

        public static class LevelBean {
            /**
             * createTime : 2019-07-18 13:40:15
             * id : 103
             * memberIdentity : 3
             * memberName : 黄金会员
             * startIntegral : 3001.0
             * endIntegral : 10000.0
             * levelRemark : 升级铂金会员
             * sort : 8
             * delete : false
             */

            public String createTime;
            public String id;
            public String memberIdentity;//1普通会员，2白银，3黄金，4铂金，5钻石
            public String memberName;
            public double startIntegral;
            public double endIntegral;
            public String levelRemark;
            public int sort;
            public boolean delete;
            public String levelIcon;
            public String backGroundPath;
        }
    }
}
