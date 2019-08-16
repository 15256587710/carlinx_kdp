package com.ecarxclub.app.model.mine;

import java.util.List;

/**
 * Created by cwp
 * on 2019/6/21 11:25.
 */
public class LogistiesListRes {

    /**
     * data : [{"key":"2019-06-21 09:31:38","value":"【杭州市】快件已被 聚光中心B座快件收发室丰巢快递柜5号柜FC5714070 代签收。如有问题请电联业务员：蔡爱平【19817404276】。相逢是缘,如果您对我的服务感到满意,给个五星好不好？【请在评价小件员处给予五星好评】"},{"key":"2019-06-21 08:08:56","value":"【杭州市】浙江杭州滨江区公司 派件员 蔡爱平 19817404276 正在为您派件"},{"key":"2019-06-21 02:47:12","value":"【嘉兴市】已离开 浙江杭州分拨中心；发往 浙江杭州滨江区公司"},{"key":"2019-06-21 02:47:03","value":"【嘉兴市】已到达 浙江杭州分拨中心"},{"key":"2019-06-21 02:26:27","value":"【嘉兴市】已到达 浙江杭州分拨中心"},{"key":"2019-06-20 05:46:48","value":"【深圳市】已离开 广东深圳公司；发往 浙江杭州分拨中心"},{"key":"2019-06-20 05:43:53","value":"【深圳市】广东深圳公司 已揽收"},{"key":"2019-06-20 03:31:28","value":"【深圳市】广东深圳公司龙岗区吉平集包分部 已揽收"},{"key":"2019-06-20 03:18:55","value":"【深圳市】已离开 广东深圳公司龙岗区吉平集包分部；发往 浙江萧山分拨中心"},{"key":"2019-06-20 01:20:57","value":"【深圳市】广东深圳公司龙岗区吉平集包分部 已揽收"},{"key":"2019-06-19 13:52:22","value":"【深圳市】广东深圳公司广视安-KH分部 已揽收"}]
     * msg : success
     * success : true
     */

    public String msg;
    public boolean success;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * key : 2019-06-21 09:31:38
         * value : 【杭州市】快件已被 聚光中心B座快件收发室丰巢快递柜5号柜FC5714070 代签收。如有问题请电联业务员：蔡爱平【19817404276】。相逢是缘,如果您对我的服务感到满意,给个五星好不好？【请在评价小件员处给予五星好评】
         */

        public String key;
        public String value;
    }
}