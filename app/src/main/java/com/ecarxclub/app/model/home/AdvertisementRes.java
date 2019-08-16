package com.ecarxclub.app.model.home;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cwp
 * on 2019/6/3 12:02.
 * 广告
 */
public class AdvertisementRes implements Serializable{

    /**
     * data : [{"beginTime":"2019-05-28 06:45:56","buttonPosition":1,"createTime":"2019-05-28 09:57:49","delete":false,"endTime":"2019-06-06 09:57:46","id":"330688170922479616","illustration":"车凌开屏广告","interLink":"www.baidu.com","itemId":"666","jump":2,"posterIcon":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/poster/20190528330688138240462848.png","posterType":"开屏广告","share":true,"status":"已上架","time":3,"title":"创建广告测试","uniqueVisitor":2,"visits":7}]
     * msg : success
     * success : true
     */

    public String msg;
    public boolean success;
    public List<DataBean> data;

    public static class DataBean implements Serializable{
        /**
         * beginTime : 2019-05-28 06:45:56
         * buttonPosition : 1
         * createTime : 2019-05-28 09:57:49
         * delete : false
         * endTime : 2019-06-06 09:57:46
         * id : 330688170922479616
         * illustration : 车凌开屏广告
         * interLink : www.baidu.com
         * itemId : 666
         * jump : 2
         * posterIcon : https://carlinx.oss-cn-hangzhou.aliyuncs.com/poster/20190528330688138240462848.png
         * posterType : 开屏广告
         * share : true
         * status : 已上架
         * time : 3
         * title : 创建广告测试
         * uniqueVisitor : 2
         * visits : 7
         */

        public String beginTime;
        public String buttonPosition;
        public String createTime;
        public boolean delete;
        public String endTime;
        public String id;
        public String illustration;
        public String interLink;
        public String itemId;
        public int jump;//1.不跳转 2.H5  3.首页  4.商城  5.卡券  6.我的  7.商品详情  8.卡券详情
        public String posterIcon;
        public String logoIcon;
        public String posterType;
        public boolean share;
        public String status;
        public int time;
        public String title;
        public int uniqueVisitor;
        public int visits;
    }
}
