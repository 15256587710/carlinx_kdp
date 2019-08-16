package com.ecarxclub.app.model.home.car;

import java.util.List;

/**
 * Created by cwp
 * 车辆车型
 * on 2019/7/23 15:52.
 */
public class CarStyleRes {

    /**
     * success : true
     * msg : success
     * data : [{"createTime":null,"id":"100","styleName":"帝豪","parentIdentity":-1,"styleColor":null},{"createTime":null,"id":"300","styleName":"帝豪GS","parentIdentity":-1,"styleColor":null},{"createTime":null,"id":"400","styleName":"帝豪GSe","parentIdentity":-1,"styleColor":null},{"createTime":null,"id":"500","styleName":"远景","parentIdentity":-1,"styleColor":null},{"createTime":null,"id":"600","styleName":"远景X1","parentIdentity":-1,"styleColor":null},{"createTime":null,"id":"700","styleName":"远景X3","parentIdentity":-1,"styleColor":null},{"createTime":null,"id":"800","styleName":"远景S1","parentIdentity":-1,"styleColor":null},{"createTime":null,"id":"900","styleName":"远景SUV","parentIdentity":-1,"styleColor":null},{"createTime":null,"id":"1000","styleName":"博瑞","parentIdentity":-1,"styleColor":null},{"createTime":null,"id":"1100","styleName":"博瑞GE","parentIdentity":-1,"styleColor":null},{"createTime":null,"id":"1200","styleName":"缤越","parentIdentity":-1,"styleColor":null},{"createTime":null,"id":"1300","styleName":"槟瑞","parentIdentity":-1,"styleColor":null},{"createTime":null,"id":"1400","styleName":"博越","parentIdentity":-1,"styleColor":null},{"createTime":null,"id":"1500","styleName":"星越","parentIdentity":-1,"styleColor":null},{"createTime":null,"id":"1600","styleName":"嘉际","parentIdentity":-1,"styleColor":null},{"createTime":null,"id":"1700","styleName":"金刚","parentIdentity":-1,"styleColor":null}]
     */

    public boolean success;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * createTime : null
         * id : 100
         * styleName : 帝豪
         * parentIdentity : -1
         * styleColor : null
         */

        public Object createTime;
        public String id;
        public String styleName;
        public int parentIdentity;
        public String styleColor;
    }
}
