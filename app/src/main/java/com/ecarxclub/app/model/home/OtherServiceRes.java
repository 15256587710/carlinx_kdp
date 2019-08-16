package com.ecarxclub.app.model.home;

import java.util.List;

/**
 * Created by cwp
 * on 2019/6/10 15:15.
 */
public class OtherServiceRes {

    /**
     * success : true
     * msg : success
     * data : [{"createTime":"2019-05-24 16:12:30","id":"329332908177362944","title":"违章查询","illustration":"第三方服务添加测试","logoIcon":"www.baidu.com","interLink":"www.mi.com","sort":100,"share":false,"show":true},{"createTime":"2019-05-24 16:11:24","id":"329332632787750912","title":"征信查询","illustration":"第三方服务修改测试","logoIcon":"www.baiduupdate.com","interLink":"www.miupdate.com","sort":60,"share":true,"show":true}]
     */

    public boolean success;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * createTime : 2019-05-24 16:12:30
         * id : 329332908177362944
         * title : 违章查询
         * illustration : 第三方服务添加测试
         * logoIcon : www.baidu.com
         * interLink : www.mi.com
         * sort : 100
         * share : false
         * show : true
         */

        public String createTime;
        public String id;
        public String title;
        public String illustration;
        public String logoIcon;
        public String interLink;
        public int sort;
        public boolean share;
        public boolean show;
    }
}
