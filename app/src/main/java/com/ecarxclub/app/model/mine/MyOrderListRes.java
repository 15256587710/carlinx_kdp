package com.ecarxclub.app.model.mine;

import com.ecarxclub.app.model.shop.ShopDetailsRes;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cwp
 * on 2019/5/7 17:37.
 */
public class MyOrderListRes implements Serializable {

    /**
     * success : true
     * msg : success
     * data : {"pageNum":1,"pageSize":8,"total":-1,"pages":0,"list":[{"createTime":"2019-05-07 16:54:51","id":"323182973950234624","userId":"316268752222162944","expendIntegral":2,"expendMoney":0.01,"expendSendMoney":0,"exchangeTime":"2019-05-07 16:54","sendTime":null,"receiveTime":null,"orderStatus":0,"payType":null,"payTime":null,"shipNo":null,"shipCrop":null,"userAddressId":"323155162690621440","items":[{"createTime":"2019-05-07 16:54:51","id":"323182973967011840","orderId":"323182973950234624","productId":"323148148518293504","amount":1,"product":{"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}}]},{"createTime":"2019-05-07 16:52:50","id":"323182465512509440","userId":"316268752222162944","expendIntegral":2,"expendMoney":0.01,"expendSendMoney":0,"exchangeTime":"2019-05-07 16:52","sendTime":null,"receiveTime":null,"orderStatus":0,"payType":null,"payTime":null,"shipNo":null,"shipCrop":null,"userAddressId":"323155162690621440","items":[{"createTime":"2019-05-07 16:52:50","id":"323182465533480960","orderId":"323182465512509440","productId":"323148148518293504","amount":1,"product":{"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}}]},{"createTime":"2019-05-07 16:50:52","id":"323181970609803264","userId":"316268752222162944","expendIntegral":2,"expendMoney":0.01,"expendSendMoney":0,"exchangeTime":"2019-05-07 16:50","sendTime":null,"receiveTime":null,"orderStatus":0,"payType":null,"payTime":null,"shipNo":null,"shipCrop":null,"userAddressId":"323155162690621440","items":[{"createTime":"2019-05-07 16:50:52","id":"323181970630774784","orderId":"323181970609803264","productId":"323148148518293504","amount":1,"product":{"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}}]},{"createTime":"2019-05-07 16:50:34","id":"323181897482113024","userId":"316268752222162944","expendIntegral":2,"expendMoney":0.01,"expendSendMoney":0,"exchangeTime":"2019-05-07 16:50","sendTime":null,"receiveTime":null,"orderStatus":0,"payType":null,"payTime":null,"shipNo":null,"shipCrop":null,"userAddressId":"323155162690621440","items":[{"createTime":"2019-05-07 16:50:34","id":"323181897498890240","orderId":"323181897482113024","productId":"323148148518293504","amount":1,"product":{"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}}]},{"createTime":"2019-05-07 16:46:06","id":"323180770619428864","userId":"316268752222162944","expendIntegral":2,"expendMoney":0.01,"expendSendMoney":0,"exchangeTime":"2019-05-07 16:46","sendTime":null,"receiveTime":null,"orderStatus":1,"payType":2,"payTime":"2019-05-07 16:46","shipNo":null,"shipCrop":null,"userAddressId":"323155162690621440","items":[{"createTime":"2019-05-07 16:46:06","id":"323180770644594688","orderId":"323180770619428864","productId":"323148148518293504","amount":1,"product":{"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}}]},{"createTime":"2019-05-07 16:38:29","id":"323178853432430592","userId":"316268752222162944","expendIntegral":2,"expendMoney":0.01,"expendSendMoney":0,"exchangeTime":"2019-05-07 16:38","sendTime":null,"receiveTime":null,"orderStatus":0,"payType":null,"payTime":null,"shipNo":null,"shipCrop":null,"userAddressId":"323155162690621440","items":[{"createTime":"2019-05-07 16:38:29","id":"323178853461790720","orderId":"323178853432430592","productId":"323148148518293504","amount":1,"product":{"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}}]},{"createTime":"2019-05-07 16:36:45","id":"323178419422629888","userId":"316268752222162944","expendIntegral":2,"expendMoney":0.01,"expendSendMoney":0,"exchangeTime":"2019-05-07 16:36","sendTime":null,"receiveTime":null,"orderStatus":0,"payType":null,"payTime":null,"shipNo":null,"shipCrop":null,"userAddressId":"323155162690621440","items":[{"createTime":"2019-05-07 16:36:45","id":"323178419447795712","orderId":"323178419422629888","productId":"323148148518293504","amount":1,"product":{"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}}]},{"createTime":"2019-05-07 16:31:04","id":"323176988548403200","userId":"316268752222162944","expendIntegral":2,"expendMoney":0.01,"expendSendMoney":0,"exchangeTime":"2019-05-07 16:31","sendTime":null,"receiveTime":null,"orderStatus":0,"payType":null,"payTime":null,"shipNo":null,"shipCrop":null,"userAddressId":"323155162690621440","items":[{"createTime":"2019-05-07 16:31:04","id":"323176988573569024","orderId":"323176988548403200","productId":"323148148518293504","amount":1,"product":{"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}}]}]}
     */

    public boolean success;
    public String msg;
    public DataBean data;

    public static class DataBean implements Serializable{
        /**
         * pageNum : 1
         * pageSize : 8
         * total : -1
         * pages : 0
         * list : [{"createTime":"2019-05-07 16:54:51","id":"323182973950234624","userId":"316268752222162944","expendIntegral":2,"expendMoney":0.01,"expendSendMoney":0,"exchangeTime":"2019-05-07 16:54","sendTime":null,"receiveTime":null,"orderStatus":0,"payType":null,"payTime":null,"shipNo":null,"shipCrop":null,"userAddressId":"323155162690621440","items":[{"createTime":"2019-05-07 16:54:51","id":"323182973967011840","orderId":"323182973950234624","productId":"323148148518293504","amount":1,"product":{"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}}]},{"createTime":"2019-05-07 16:52:50","id":"323182465512509440","userId":"316268752222162944","expendIntegral":2,"expendMoney":0.01,"expendSendMoney":0,"exchangeTime":"2019-05-07 16:52","sendTime":null,"receiveTime":null,"orderStatus":0,"payType":null,"payTime":null,"shipNo":null,"shipCrop":null,"userAddressId":"323155162690621440","items":[{"createTime":"2019-05-07 16:52:50","id":"323182465533480960","orderId":"323182465512509440","productId":"323148148518293504","amount":1,"product":{"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}}]},{"createTime":"2019-05-07 16:50:52","id":"323181970609803264","userId":"316268752222162944","expendIntegral":2,"expendMoney":0.01,"expendSendMoney":0,"exchangeTime":"2019-05-07 16:50","sendTime":null,"receiveTime":null,"orderStatus":0,"payType":null,"payTime":null,"shipNo":null,"shipCrop":null,"userAddressId":"323155162690621440","items":[{"createTime":"2019-05-07 16:50:52","id":"323181970630774784","orderId":"323181970609803264","productId":"323148148518293504","amount":1,"product":{"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}}]},{"createTime":"2019-05-07 16:50:34","id":"323181897482113024","userId":"316268752222162944","expendIntegral":2,"expendMoney":0.01,"expendSendMoney":0,"exchangeTime":"2019-05-07 16:50","sendTime":null,"receiveTime":null,"orderStatus":0,"payType":null,"payTime":null,"shipNo":null,"shipCrop":null,"userAddressId":"323155162690621440","items":[{"createTime":"2019-05-07 16:50:34","id":"323181897498890240","orderId":"323181897482113024","productId":"323148148518293504","amount":1,"product":{"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}}]},{"createTime":"2019-05-07 16:46:06","id":"323180770619428864","userId":"316268752222162944","expendIntegral":2,"expendMoney":0.01,"expendSendMoney":0,"exchangeTime":"2019-05-07 16:46","sendTime":null,"receiveTime":null,"orderStatus":1,"payType":2,"payTime":"2019-05-07 16:46","shipNo":null,"shipCrop":null,"userAddressId":"323155162690621440","items":[{"createTime":"2019-05-07 16:46:06","id":"323180770644594688","orderId":"323180770619428864","productId":"323148148518293504","amount":1,"product":{"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}}]},{"createTime":"2019-05-07 16:38:29","id":"323178853432430592","userId":"316268752222162944","expendIntegral":2,"expendMoney":0.01,"expendSendMoney":0,"exchangeTime":"2019-05-07 16:38","sendTime":null,"receiveTime":null,"orderStatus":0,"payType":null,"payTime":null,"shipNo":null,"shipCrop":null,"userAddressId":"323155162690621440","items":[{"createTime":"2019-05-07 16:38:29","id":"323178853461790720","orderId":"323178853432430592","productId":"323148148518293504","amount":1,"product":{"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}}]},{"createTime":"2019-05-07 16:36:45","id":"323178419422629888","userId":"316268752222162944","expendIntegral":2,"expendMoney":0.01,"expendSendMoney":0,"exchangeTime":"2019-05-07 16:36","sendTime":null,"receiveTime":null,"orderStatus":0,"payType":null,"payTime":null,"shipNo":null,"shipCrop":null,"userAddressId":"323155162690621440","items":[{"createTime":"2019-05-07 16:36:45","id":"323178419447795712","orderId":"323178419422629888","productId":"323148148518293504","amount":1,"product":{"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}}]},{"createTime":"2019-05-07 16:31:04","id":"323176988548403200","userId":"316268752222162944","expendIntegral":2,"expendMoney":0.01,"expendSendMoney":0,"exchangeTime":"2019-05-07 16:31","sendTime":null,"receiveTime":null,"orderStatus":0,"payType":null,"payTime":null,"shipNo":null,"shipCrop":null,"userAddressId":"323155162690621440","items":[{"createTime":"2019-05-07 16:31:04","id":"323176988573569024","orderId":"323176988548403200","productId":"323148148518293504","amount":1,"product":{"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}}]}]
         */

        public int pageNum;
        public int pageSize;
        public int total;
        public int pages;
        public List<ListBean> list;

        public static class ListBean implements Serializable{
            /**
             * createTime : 2019-05-07 16:54:51
             * id : 323182973950234624
             * userId : 316268752222162944
             * expendIntegral : 2.0
             * expendMoney : 0.01
             * expendSendMoney : 0.0
             * exchangeTime : 2019-05-07 16:54
             * sendTime : null
             * receiveTime : null
             * orderStatus : 0
             * payType : null
             * payTime : null
             * shipNo : null
             * shipCrop : null
             * userAddressId : 323155162690621440
             * items : [{"createTime":"2019-05-07 16:54:51","id":"323182973967011840","orderId":"323182973950234624","productId":"323148148518293504","amount":1,"product":{"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}}]
             */

            public String createTime;
            public String id;
            public String userId;
            public double expendIntegral;
            public double expendMoney;
            public double expendSendMoney;
            public String exchangeTime;
            public Object sendTime;
            public Object receiveTime;
            public int orderStatus;// 0未支付  1已支付 2已发货 3申请取消 4已取消
            public Object payType;
            public Object payTime;
            public String shipNo;
            public String shipCrop;
            public String userAddressId;
            public List<ItemsBean> items;

            public static class ItemsBean implements Serializable{
                /**
                 * createTime : 2019-05-07 16:54:51
                 * id : 323182973967011840
                 * orderId : 323182973950234624
                 * productId : 323148148518293504
                 * amount : 1
                 * product : {"createTime":"2019-05-07 14:36:28","id":"323148148518293504","productName":"test","productCover":"111","productAmount":100,"productIcon":"http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg","productStock":85,"productRemark":"<p>test<\/p>","integral":2,"money":0.01,"enable":true,"types":0,"sendFee":0,"tags":null,"delete":false}
                 */

                public String createTime;
                public String id;
                public String orderId;
                public String productId;
                public int amount;
                public ProductBean product;

                public static class ProductBean implements Serializable{
                    /**
                     * createTime : 2019-05-07 14:36:28
                     * id : 323148148518293504
                     * productName : test
                     * productCover : 111
                     * productAmount : 100
                     * productIcon : http://test.oss.carlinx.cn/product/20190507323148047871774720.jpg
                     * productStock : 85
                     * productRemark : <p>test</p>
                     * integral : 2.0
                     * money : 0.01
                     * enable : true
                     * types : 0
                     * sendFee : 0.0
                     * tags : null
                     * delete : false
                     */

                    public String createTime;
                    public String id;
                    public String productName;
                    public String productCover;
                    public int productAmount;
                    public String productIcon;
                    public int productStock;
                    public String productRemark;
                    public double integral;
                    public double money;
                    public boolean enable;
                    public int types;
                    public double sendFee;
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
                    public boolean delete;
                }
            }
        }
    }
}
