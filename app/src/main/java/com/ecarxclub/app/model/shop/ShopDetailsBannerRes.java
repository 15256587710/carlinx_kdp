package com.ecarxclub.app.model.shop;

import java.util.List;

/**
 * Created by cwp
 * on 2019/4/29 14:55.
 */
public class ShopDetailsBannerRes {

    /**
     * data : [{"createTime":1556162991000,"fileName":"1","filePath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/sys-files/20190425318752523009789952.png","groupId":"318752488297730048","id":"318752528533688320","sort":1},{"createTime":1556163004000,"fileName":"2","filePath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/sys-files/20190425318752560456536064.png","groupId":"318752488297730048","id":"318752586670936064","sort":2},{"createTime":1556163014000,"fileName":"3","filePath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/sys-files/20190425318752619130654720.png","groupId":"318752488297730048","id":"318752627489902592","sort":3},{"createTime":1556163021000,"fileName":"4","filePath":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/sys-files/20190425318752652810915840.png","groupId":"318752488297730048","id":"318752657625976832","sort":4}]
     * msg : success
     * success : true
     */

    public String msg;
    public boolean success;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * createTime : 1556162991000
         * fileName : 1
         * filePath : https://carlinx.oss-cn-hangzhou.aliyuncs.com/sys-files/20190425318752523009789952.png
         * groupId : 318752488297730048
         * id : 318752528533688320
         * sort : 1
         */

        public long createTime;
        public String fileName;
        public String filePath;
        public String groupId;
        public String id;
        public int sort;
    }
}
