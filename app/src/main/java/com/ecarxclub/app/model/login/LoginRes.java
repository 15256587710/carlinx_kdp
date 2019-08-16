package com.ecarxclub.app.model.login;

public class LoginRes {
    /**
     * success : true
     * data : {"userId":316268752222162944,"userName":"手机用户15256587710","token":"B7E7A9D051051CF2ECFC13441309B47D20733AF43D6E6C357F56E355ECAB1985"}
     */

    private boolean success;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userId : 316268752222162944
         * userName : 手机用户15256587710
         * token : B7E7A9D051051CF2ECFC13441309B47D20733AF43D6E6C357F56E355ECAB1985
         */

        private long userId;
        private String userName;
        private String token;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
