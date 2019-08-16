package com.ecarxclub.app.model;

/**
 * Created by cwp
 * on 2019/4/29 15:53.
 */
public class UserInfoRes {

    /**
     * success : true
     * msg : success
     * data : {"createTime":"2019-04-18 15:00:12","id":"316268752222162944","mobile":"15256587710","icon":null,"nickName":"手机用户15256587710","realName":null,"birthday":null,"gender":null,"idCardNo":null,"mainAddressId":"318104672139350016","enable":true}
     */

    public boolean success;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * createTime : 2019-04-18 15:00:12
         * id : 316268752222162944
         * mobile : 15256587710
         * icon : null
         * nickName : 手机用户15256587710
         * realName : null
         * birthday : null
         * gender : null
         * idCardNo : null
         * mainAddressId : 318104672139350016
         * enable : true
         */

        public String createTime;
        public String id;
        public String mobile;
        public String icon;
        public String nickName;
        public String realName;
        public String birthday;
        public int gender;
        public String idCardNo;
        public String mainAddressId;
        public String registrationId;
        public boolean enable;
        public int notReadCount;
        public String location;
        public String mainCarId;//绑定车辆id
        public String weiUnionId;//微信id
        public String platform;
    }
}
