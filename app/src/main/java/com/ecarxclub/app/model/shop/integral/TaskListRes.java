package com.ecarxclub.app.model.shop.integral;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cwp
 * on 2019/7/15 16:05.
 */
public class TaskListRes{

    /**
     * data : {"newTask":[{"createTime":"2019-07-15 14:51:25","delete":false,"finish":false,"finishCount":0,"id":"128","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/userTask/20190715348159916223107072.png","integral":30,"requireCount":1,"sort":5,"taskLable":2,"taskName":"绑定车辆","taskType":6,"unit":"积分/辆"},{"createTime":"2019-07-15 14:51:29","delete":false,"finish":false,"finishCount":0,"id":"129","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/userTask/20190715348160003263303680.png","integral":20,"requireCount":1,"sort":4,"taskLable":2,"taskName":"用户注册","taskType":7,"unit":"积分"},{"createTime":"2019-07-15 14:51:32","delete":false,"finish":false,"finishCount":0,"id":"130","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/userTask/20190715348160081034088448.png","integral":20,"requireCount":1,"sort":3,"taskLable":2,"taskName":"绑定微信","taskType":8,"unit":"积分"},{"createTime":"2019-07-15 14:51:35","delete":false,"finish":false,"finishCount":0,"id":"131","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/userTask/20190715348160190828384256.png","integral":20,"requireCount":1,"sort":2,"taskLable":2,"taskName":"车辆信息完善","taskType":9,"unit":"积分/辆"},{"createTime":"2019-07-15 14:51:38","delete":false,"finish":false,"finishCount":0,"id":"132","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/userTask/20190715348160268687249408.png","integral":20,"requireCount":1,"sort":1,"taskLable":2,"taskName":"个人信息完善","taskType":10,"unit":"积分"}],"todayTask":[{"createTime":"2019-07-08 13:40:13","delete":false,"finish":false,"finishCount":0,"id":"123","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/userTask/20190715348159344401059840.png","integral":5,"requireCount":1,"sort":5,"taskLable":1,"taskName":"签到","taskType":1,"unit":"积分"},{"createTime":"2019-07-08 13:40:51","delete":false,"finish":false,"finishCount":0,"id":"124","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/userTask/20190715348159542816804864.png","integral":100,"requireCount":3,"sort":4,"taskLable":1,"taskName":"邀请好友","taskType":2,"unit":"积分"},{"createTime":"2019-07-08 13:41:20","delete":false,"finish":false,"finishCount":0,"id":"125","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/userTask/20190715348159644725809152.png","integral":10,"requireCount":1,"sort":3,"taskLable":1,"taskName":"分享里程","taskType":3,"unit":"积分"},{"createTime":"2019-07-08 13:41:59","delete":false,"finish":false,"finishCount":0,"id":"126","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/userTask/20190715348159738778882048.png","integral":3,"requireCount":1,"sort":2,"taskLable":1,"taskName":"行驶里程","taskType":4,"unit":"积分/公里"},{"createTime":"2019-07-15 10:32:42","delete":false,"finish":false,"finishCount":0,"id":"127","imgPath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/userTask/20190715348159835512115200.png","integral":5,"requireCount":150,"sort":1,"taskLable":1,"taskName":"玩3次积分游戏","taskType":5,"unit":"积分"}]}
     * msg : success
     * success : true
     */

    public DataBean data;
    public String msg;
    public boolean success;

    public static class DataBean{
        public List<NewTaskBean> newTask;
        public List<TodayTaskBean> todayTask;

        public static class NewTaskBean{
            /**
             * createTime : 2019-07-15 14:51:25
             * delete : false
             * finish : false
             * finishCount : 0
             * id : 128
             * imgPath : https://carlinx.oss-cn-hangzhou.aliyuncs.com/userTask/20190715348159916223107072.png
             * integral : 30.0
             * requireCount : 1
             * sort : 5
             * taskLable : 2
             * taskName : 绑定车辆
             * taskType : 6
             * unit : 积分/辆
             */

            public String createTime;
            public boolean delete;
            public boolean finish;
            public int finishCount;
            public String id;
            public String imgPath;
            public double integral;
            public int requireCount;
            public int sort;
            public int taskLable;
            public String taskName;
            public int taskType;
            public String unit;
        }

        public static class TodayTaskBean{
            /**
             * createTime : 2019-07-08 13:40:13
             * delete : false
             * finish : false
             * finishCount : 0
             * id : 123
             * imgPath : https://carlinx.oss-cn-hangzhou.aliyuncs.com/userTask/20190715348159344401059840.png
             * integral : 5.0
             * requireCount : 1
             * sort : 5
             * taskLable : 1
             * taskName : 签到
             * taskType : 1
             * unit : 积分
             */

            public String createTime;
            public boolean delete;
            public boolean finish;
            public int finishCount;
            public String id;
            public String imgPath;
            public double integral;
            public int requireCount;
            public int sort;
            public int taskLable;
            public String taskName;
            public int taskType;
            public String unit;
            public String shareInterLink;//分享链接
            public String interLink;//跳转链接
            public String shareImage;//分享图片
            public String shareContent;//分享内容
            public String shareTitle;//分享标题


        }
    }
}
