package com.ecarxclub.app.model.shop.fgshop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cwp
 * on 2019/4/26 17:18.
 * 积分商城标签
 */
public class ShopTagsRes implements Serializable{

    /**
     * success : true
     * msg : success
     * data : [{"id":"316658111731601408","createTime":"2019-04-19 16:47:23","tagName":"汽车周边","tagIcon":"","enable":true,"sort":1},{"id":"316658887975636992","createTime":"2019-04-19 16:50:28","tagName":"手机数码","tagIcon":"","enable":true,"sort":2},{"id":"316658932347179008","createTime":"2019-04-19 16:50:38","tagName":"家居生活","tagIcon":"","enable":true,"sort":3},{"id":"316658963934482432","createTime":"2019-04-19 16:50:46","tagName":"家用电器","tagIcon":"","enable":true,"sort":4},{"id":"317713756090142720","createTime":"2019-04-22 14:42:08","tagName":"食品保健","tagIcon":"","enable":true,"sort":5}]
     */

    public boolean success;
    public String msg;
    public List<DataBean> data;

    public static class DataBean implements Serializable{
        /**
         * id : 316658111731601408
         * createTime : 2019-04-19 16:47:23
         * tagName : 汽车周边
         * tagIcon :
         * enable : true
         * sort : 1
         */

        public String id;
        public String createTime;
        public String tagName;
        public String tagIcon;
        public boolean enable;
        public int sort;
    }
}
