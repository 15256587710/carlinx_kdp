package com.ecarxclub.app.model.home;

/**
 * Created by cwp
 * on 2019/4/22 17:59.
 */
public class WeatherRes {


    /**
     * success : true
     * msg : success
     * data : {"city":"杭州","temperature":"20℃~27℃","wash_car":"不宜","weather":"小雨","wind":"东北风微风"}
     */

    public boolean success;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * city : 杭州
         * temperature : 20℃~27℃
         * wash_car : 不宜
         * weather : 小雨
         * wind : 东北风微风
         */

        public String city;
        public String temperature;
        public String wash_car;
        public String weather;
        public String wind;
    }
}
