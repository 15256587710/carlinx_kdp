package com.ecarxclub.app.model.home;

/**
 * Created by cwp
 * on 2019/6/3 18:28.
 * 版本更新
 */
public class GetVersionRes {

    /**
     * success : true
     * msg : success
     * data : {"createTime":"2019-06-19 18:25:27","operId":"317711015599017984","id":"329239233602654288","platformId":"329007203296088088","versionPort":"1.0","content":"huawei","interLink":"www.baidu.com","grade":"1","delay":5,"operator":"tanlixu","delete":false}
     */

    public boolean success;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * createTime : 2019-06-19 18:25:27
         * operId : 317711015599017984
         * id : 329239233602654288
         * platformId : 329007203296088088
         * versionPort : 1.0
         * content : huawei
         * interLink : www.baidu.com
         * grade : 1
         * delay : 5
         * operator : tanlixu
         * delete : false
         */

        public String createTime;
        public String operId;
        public String id;
        public String platformId;
        public String versionPort;
        public String content;
        public String interLink;
        public String grade;//（1.最新版本   2.提示更新   3.强制更新）
        public int delay;
        public String operator;
        public boolean delete;
    }
}
