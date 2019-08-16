package com.ecarxclub.app.model.shop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cwp
 * on 2019/6/4 15:09.
 */
public class ShopDetailsRes implements Serializable{

    /**
     * success : true
     * msg : success
     * data : {"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":58,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":[{"createTime":"2019-05-20 09:51:52","id":"327787569737764864","productId":"323148148518293504","tagId":"316658932347179008","product":null,"tag":null}],"delete":false}
     */

    public boolean success;
    public String msg;
    public DataBean data;

    public static class DataBean implements Serializable{
        /**
         * createTime : 2019-05-07 14:36:28
         * id : 323148148518293504
         * productName : test
         * productCover : 111
         * productAmount : 100
         * productIcon : http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg
         * productStock : 58
         * productRemark : <p>test</p>
         * integral : 2.0
         * money : 0.01
         * enable : true
         * types : 0
         * sendFee : 0.0
         * tags : [{"createTime":"2019-05-20 09:51:52","id":"327787569737764864","productId":"323148148518293504","tagId":"316658932347179008","product":null,"tag":null}]
         * delete : false
         */

        public String createTime;
        public String id;
        public String productName;
        public String productCover;
        public int productAmount;
        public String productIcon;
        public int productStock;//库存
        public String productRemark;
        public double integral;
        public double money;
        public boolean enable;
        public int types;
        public double sendFee;
        public boolean delete;
        public List<TagsBean> tags;

        public static class TagsBean implements Serializable{
            /**
             * createTime : 2019-05-20 09:51:52
             * id : 327787569737764864
             * productId : 323148148518293504
             * tagId : 316658932347179008
             * product : null
             * tag : null
             */

            public String createTime;
            public String id;
            public String productId;
            public String tagId;
            public TagBean tag;

            public static class TagBean implements Serializable{
                /**
                 * createTime : 2019-04-22 14:42:08
                 * enable : true
                 * id : 317713756090142720
                 * sort : 5
                 * tagIcon :
                 * tagName : 食品保健
                 */

                public String createTime;
                public boolean enable;
                public String id;
                public int sort;
                public String tagIcon;
                public String tagName;
            }
        }
    }
}
