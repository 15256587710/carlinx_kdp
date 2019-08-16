package com.ecarxclub.app.model.card;

import java.util.List;

/**
 * Created by cwp
 * on 2019/4/22 18:18.
 * 卡券分类
 */
public class CardCouponTypeRes {

    /**
     * success : true
     * msg : success
     * data : [{"id":"293088549065068669","createTime":"2019-02-14 00:00:00","typeName":"金融类","typeIcon":"http://localhost:8080/advert_war_exploded/api/static/files/seller-coupon-type/20190321/306118670344654848.png","enable":true},{"id":"306468775199379456","createTime":"2019-03-22 00:00:00","typeName":"消费类","typeIcon":"http://localhost:8080/advert_war_exploded/api/static/files/seller-coupon-type/20190322/306468768333303808.png","enable":true}]
     */

    public boolean success;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * id : 293088549065068669
         * createTime : 2019-02-14 00:00:00
         * typeName : 金融类
         * typeIcon : http://localhost:8080/advert_war_exploded/api/static/files/seller-coupon-type/20190321/306118670344654848.png
         * enable : true
         */

        public String id;
        public String createTime;
        //type  分类
        public String typeName;
        public String typeIcon;
        public boolean enable;

        //类型 tag
        public String tagIcon;
        public String tagName;
    }
}
