package com.ecarxclub.app.model.shop;

import java.util.List;

/**
 * Created by cwp
 * on 2019/4/28 10:24.
 */
public class IntegralRecordRes {


    /**
     * data : {"integral":10682,"record":{"list":[{"changeCover":"首次绑定车辆奖励积分30","changeIntegral":30,"changeType":8,"createTime":"2019-07-15 11:24:57","finalIntegral":10682,"id":"348104715952852992","operationId":"0","prevIntegral":10652,"remark":"首次绑定车辆奖励bubble:339511760463728640","userId":"316268752222162944"},{"changeCover":"签到积分","changeIntegral":2,"changeType":2,"createTime":"2019-07-15 11:15:09","finalIntegral":10652,"id":"348102246061445120","operationId":"0","prevIntegral":10650,"remark":"打卡签到积分","userId":"316268752222162944"},{"changeCover":"兑换商品","changeIntegral":-9,"changeType":3,"createTime":"2019-07-15 09:36:08","finalIntegral":10650,"id":"348077329530097664","operationId":"0","prevIntegral":10659,"remark":"兑换商品*1","userId":"316268752222162944"},{"changeCover":"取消订单","changeIntegral":9,"changeType":4,"createTime":"2019-07-13 00:20:08","finalIntegral":10659,"id":"347212631456747520","operationId":"0","prevIntegral":10650,"remark":"取消订单返还积分:9.00","userId":"316268752222162944"},{"changeCover":"取消订单","changeIntegral":9,"changeType":4,"createTime":"2019-07-13 00:20:08","finalIntegral":10650,"id":"347212631389638656","operationId":"0","prevIntegral":10641,"remark":"取消订单返还积分:9.00","userId":"316268752222162944"},{"changeCover":"取消订单","changeIntegral":9,"changeType":4,"createTime":"2019-07-13 00:20:08","finalIntegral":10641,"id":"347212631322529792","operationId":"0","prevIntegral":10632,"remark":"取消订单返还积分:9.00","userId":"316268752222162944"},{"changeCover":"取消订单","changeIntegral":9,"changeType":4,"createTime":"2019-07-13 00:20:08","finalIntegral":10632,"id":"347212630731132928","operationId":"0","prevIntegral":10623,"remark":"取消订单返还积分:9.00","userId":"316268752222162944"},{"changeCover":"取消订单","changeIntegral":9,"changeType":4,"createTime":"2019-07-13 00:20:07","finalIntegral":10623,"id":"347212629799997440","operationId":"0","prevIntegral":10614,"remark":"取消订单返还积分:9.00","userId":"316268752222162944"},{"changeCover":"签到积分","changeIntegral":2,"changeType":2,"createTime":"2019-07-12 14:52:13","finalIntegral":10614,"id":"347069712510881792","operationId":"0","prevIntegral":10612,"remark":"打卡签到积分","userId":"316268752222162944"},{"changeCover":"兑换商品","changeIntegral":-9,"changeType":3,"createTime":"2019-07-12 11:43:52","finalIntegral":10612,"id":"347022310768775168","operationId":"0","prevIntegral":10621,"remark":"兑换商品*1","userId":"316268752222162944"}],"pageNum":1,"pageSize":10,"pages":0,"total":-1}}
     * msg : success
     * success : true
     */

    public DataBean data;
    public String msg;
    public boolean success;

    public static class DataBean {
        /**
         * integral : 10682.0
         * record : {"list":[{"changeCover":"首次绑定车辆奖励积分30","changeIntegral":30,"changeType":8,"createTime":"2019-07-15 11:24:57","finalIntegral":10682,"id":"348104715952852992","operationId":"0","prevIntegral":10652,"remark":"首次绑定车辆奖励bubble:339511760463728640","userId":"316268752222162944"},{"changeCover":"签到积分","changeIntegral":2,"changeType":2,"createTime":"2019-07-15 11:15:09","finalIntegral":10652,"id":"348102246061445120","operationId":"0","prevIntegral":10650,"remark":"打卡签到积分","userId":"316268752222162944"},{"changeCover":"兑换商品","changeIntegral":-9,"changeType":3,"createTime":"2019-07-15 09:36:08","finalIntegral":10650,"id":"348077329530097664","operationId":"0","prevIntegral":10659,"remark":"兑换商品*1","userId":"316268752222162944"},{"changeCover":"取消订单","changeIntegral":9,"changeType":4,"createTime":"2019-07-13 00:20:08","finalIntegral":10659,"id":"347212631456747520","operationId":"0","prevIntegral":10650,"remark":"取消订单返还积分:9.00","userId":"316268752222162944"},{"changeCover":"取消订单","changeIntegral":9,"changeType":4,"createTime":"2019-07-13 00:20:08","finalIntegral":10650,"id":"347212631389638656","operationId":"0","prevIntegral":10641,"remark":"取消订单返还积分:9.00","userId":"316268752222162944"},{"changeCover":"取消订单","changeIntegral":9,"changeType":4,"createTime":"2019-07-13 00:20:08","finalIntegral":10641,"id":"347212631322529792","operationId":"0","prevIntegral":10632,"remark":"取消订单返还积分:9.00","userId":"316268752222162944"},{"changeCover":"取消订单","changeIntegral":9,"changeType":4,"createTime":"2019-07-13 00:20:08","finalIntegral":10632,"id":"347212630731132928","operationId":"0","prevIntegral":10623,"remark":"取消订单返还积分:9.00","userId":"316268752222162944"},{"changeCover":"取消订单","changeIntegral":9,"changeType":4,"createTime":"2019-07-13 00:20:07","finalIntegral":10623,"id":"347212629799997440","operationId":"0","prevIntegral":10614,"remark":"取消订单返还积分:9.00","userId":"316268752222162944"},{"changeCover":"签到积分","changeIntegral":2,"changeType":2,"createTime":"2019-07-12 14:52:13","finalIntegral":10614,"id":"347069712510881792","operationId":"0","prevIntegral":10612,"remark":"打卡签到积分","userId":"316268752222162944"},{"changeCover":"兑换商品","changeIntegral":-9,"changeType":3,"createTime":"2019-07-12 11:43:52","finalIntegral":10612,"id":"347022310768775168","operationId":"0","prevIntegral":10621,"remark":"兑换商品*1","userId":"316268752222162944"}],"pageNum":1,"pageSize":10,"pages":0,"total":-1}
         */

        public double integral;
        public RecordBean record;

        public static class RecordBean {
            /**
             * list : [{"changeCover":"首次绑定车辆奖励积分30","changeIntegral":30,"changeType":8,"createTime":"2019-07-15 11:24:57","finalIntegral":10682,"id":"348104715952852992","operationId":"0","prevIntegral":10652,"remark":"首次绑定车辆奖励bubble:339511760463728640","userId":"316268752222162944"},{"changeCover":"签到积分","changeIntegral":2,"changeType":2,"createTime":"2019-07-15 11:15:09","finalIntegral":10652,"id":"348102246061445120","operationId":"0","prevIntegral":10650,"remark":"打卡签到积分","userId":"316268752222162944"},{"changeCover":"兑换商品","changeIntegral":-9,"changeType":3,"createTime":"2019-07-15 09:36:08","finalIntegral":10650,"id":"348077329530097664","operationId":"0","prevIntegral":10659,"remark":"兑换商品*1","userId":"316268752222162944"},{"changeCover":"取消订单","changeIntegral":9,"changeType":4,"createTime":"2019-07-13 00:20:08","finalIntegral":10659,"id":"347212631456747520","operationId":"0","prevIntegral":10650,"remark":"取消订单返还积分:9.00","userId":"316268752222162944"},{"changeCover":"取消订单","changeIntegral":9,"changeType":4,"createTime":"2019-07-13 00:20:08","finalIntegral":10650,"id":"347212631389638656","operationId":"0","prevIntegral":10641,"remark":"取消订单返还积分:9.00","userId":"316268752222162944"},{"changeCover":"取消订单","changeIntegral":9,"changeType":4,"createTime":"2019-07-13 00:20:08","finalIntegral":10641,"id":"347212631322529792","operationId":"0","prevIntegral":10632,"remark":"取消订单返还积分:9.00","userId":"316268752222162944"},{"changeCover":"取消订单","changeIntegral":9,"changeType":4,"createTime":"2019-07-13 00:20:08","finalIntegral":10632,"id":"347212630731132928","operationId":"0","prevIntegral":10623,"remark":"取消订单返还积分:9.00","userId":"316268752222162944"},{"changeCover":"取消订单","changeIntegral":9,"changeType":4,"createTime":"2019-07-13 00:20:07","finalIntegral":10623,"id":"347212629799997440","operationId":"0","prevIntegral":10614,"remark":"取消订单返还积分:9.00","userId":"316268752222162944"},{"changeCover":"签到积分","changeIntegral":2,"changeType":2,"createTime":"2019-07-12 14:52:13","finalIntegral":10614,"id":"347069712510881792","operationId":"0","prevIntegral":10612,"remark":"打卡签到积分","userId":"316268752222162944"},{"changeCover":"兑换商品","changeIntegral":-9,"changeType":3,"createTime":"2019-07-12 11:43:52","finalIntegral":10612,"id":"347022310768775168","operationId":"0","prevIntegral":10621,"remark":"兑换商品*1","userId":"316268752222162944"}]
             * pageNum : 1
             * pageSize : 10
             * pages : 0
             * total : -1
             */

            public int pageNum;
            public int pageSize;
            public int pages;
            public int total;
            public List<ListBean> list;

            public static class ListBean {
                /**
                 * changeCover : 首次绑定车辆奖励积分30
                 * changeIntegral : 30.0
                 * changeType : 8
                 * createTime : 2019-07-15 11:24:57
                 * finalIntegral : 10682.0
                 * id : 348104715952852992
                 * operationId : 0
                 * prevIntegral : 10652.0
                 * remark : 首次绑定车辆奖励bubble:339511760463728640
                 * userId : 316268752222162944
                 */

                public String changeCover;
                public double changeIntegral;
                public int changeType;
                public String createTime;
                public double finalIntegral;
                public String id;
                public String operationId;
                public double prevIntegral;
                public String remark;
                public String userId;
                public boolean isShow;
            }
        }
    }
}
