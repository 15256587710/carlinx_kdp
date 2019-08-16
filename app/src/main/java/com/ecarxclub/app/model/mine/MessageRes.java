package com.ecarxclub.app.model.mine;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cwp
 * on 2019/4/26 13:52.
 * 消息通知
 */
public class MessageRes implements Serializable{


    /**
     * success : true
     * msg : success
     * data : {"pageNum":1,"pageSize":10,"total":-1,"pages":0,"list":[{"createTime":"2019-05-31 10:25:02","id":"318746085478764545","userId":"293088549539024906","messageType":1,"title":"测试1","content":"哈哈哈哈","enable":true,"jump":null,"interLink":null,"sendId":null,"itemId":null,"sendType":1,"read":false},{"createTime":"2019-02-13 00:00:00","id":"293088549065068589","userId":"-1","messageType":1,"title":"消息标题","content":"这是消息内容","enable":true,"jump":null,"interLink":null,"sendId":null,"itemId":null,"sendType":1,"read":false}]}
     */

    public boolean success;
    public String msg;
    public DataBean data;

    public static class DataBean implements Serializable{
        /**
         * pageNum : 1
         * pageSize : 10
         * total : -1
         * pages : 0
         * list : [{"createTime":"2019-05-31 10:25:02","id":"318746085478764545","userId":"293088549539024906","messageType":1,"title":"测试1","content":"哈哈哈哈","enable":true,"jump":null,"interLink":null,"sendId":null,"itemId":null,"sendType":1,"read":false},{"createTime":"2019-02-13 00:00:00","id":"293088549065068589","userId":"-1","messageType":1,"title":"消息标题","content":"这是消息内容","enable":true,"jump":null,"interLink":null,"sendId":null,"itemId":null,"sendType":1,"read":false}]
         */

        public int pageNum;
        public int pageSize;
        public int total;
        public int pages;
        public List<ListBean> list;

        public static class ListBean implements Serializable{
            /**
             * createTime : 2019-05-31 10:25:02
             * id : 318746085478764545
             * userId : 293088549539024906
             * messageType : 1
             * title : 测试1
             * content : 哈哈哈哈
             * enable : true
             * jump : null
             * interLink : null
             * sendId : null
             * itemId : null
             * sendType : 1
             * read : false
             */

            public String createTime;
            public String id;
            public String userId;
            public int messageType;//（1.全局消息  2.用户消息）
            public String title;
            public String content;
            public boolean enable;
            public Object jump;
            public Object interLink;
            public Object sendId;
            public Object itemId;
            public int sendType;
            public boolean read;
        }
    }
}
