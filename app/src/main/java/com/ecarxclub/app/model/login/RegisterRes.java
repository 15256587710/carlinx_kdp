package com.ecarxclub.app.model.login;

/**
 * Created by cwp
 * on 2019/4/18 13:38.
 */
public class RegisterRes {
    /**
     * success : true
     * msg : success
     * data : {"id":"0","createTime":"2019-04-18 15:00:12","mobile":"15256587710","icon":null,"nickName":"手机用户15256587710","realName":null,"birthday":null,"gender":null,"idCardNo":null,"mainAddressId":null,"enable":true}
     */

    private boolean success;
    private String msg;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 0
         * createTime : 2019-04-18 15:00:12
         * mobile : 15256587710
         * icon : null
         * nickName : 手机用户15256587710
         * realName : null
         * birthday : null
         * gender : null
         * idCardNo : null
         * mainAddressId : null
         * enable : true
         */

        private String id;
        private String createTime;
        private String mobile;
        private Object icon;
        private String nickName;
        private Object realName;
        private Object birthday;
        private Object gender;
        private Object idCardNo;
        private Object mainAddressId;
        private boolean enable;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public Object getRealName() {
            return realName;
        }

        public void setRealName(Object realName) {
            this.realName = realName;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public Object getGender() {
            return gender;
        }

        public void setGender(Object gender) {
            this.gender = gender;
        }

        public Object getIdCardNo() {
            return idCardNo;
        }

        public void setIdCardNo(Object idCardNo) {
            this.idCardNo = idCardNo;
        }

        public Object getMainAddressId() {
            return mainAddressId;
        }

        public void setMainAddressId(Object mainAddressId) {
            this.mainAddressId = mainAddressId;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }


}
