package com.ecarxclub.app.model.home;

import java.util.List;

/**
 * Created by cwp
 * on 2019/6/18 17:02.
 * 行驶记录
 */
public class MyDrivingListRes {

    /**
     * data : {"list":[{"averageSpeed":0,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:37","distance":0,"endAddress":"山东省青岛市李沧区宾川路","endDate":"2019-03-05","endDistrict":"李沧区","endLat":36.17,"endLocation":"青岛日田工艺装饰有限公司","endLongitude":120.48,"endMileage":8361,"endTime":"12:25","id":"336839904665407488","oilConsumption":0,"sid":"10000010","startAddress":"山东省青岛市李沧区宾川路","startDate":"2019-03-05","startDistrict":"李沧区","startLat":36.17,"startLocation":"米罗湾","startLongitude":120.48,"startMileage":8361,"startTime":"12:21"},{"averageSpeed":0,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:37","distance":0,"endAddress":"山东省青岛市李沧区宾川路","endDate":"2019-03-05","endDistrict":"李沧区","endLat":36.17,"endLocation":"米罗湾","endLongitude":120.48,"endMileage":8361,"endTime":"12:09","id":"336839905323913216","oilConsumption":2.4,"sid":"10000011","startAddress":"山东省青岛市李沧区宾川路","startDate":"2019-03-05","startDistrict":"李沧区","startLat":36.17,"startLocation":"虹美医院","startLongitude":120.48,"startMileage":8361,"startTime":"12:06"},{"averageSpeed":31,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:37","distance":5,"endAddress":"山东省青岛市李沧区宾川路","endDate":"2019-03-05","endDistrict":"李沧区","endLat":36.17,"endLocation":"阳光育才幼儿园","endLongitude":120.48,"endMileage":8361,"endTime":"07:36","id":"336839905982418944","oilConsumption":0.2,"sid":"10000012","startAddress":"山东省青岛市李沧区金水路","startDate":"2019-03-05","startDistrict":"李沧区","startLat":36.19,"startLocation":"青岛鲁强模具青岛理工大学产学研基地","startLongitude":120.47,"startMileage":8356,"startTime":"07:26"},{"averageSpeed":13,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:38","distance":2,"endAddress":"山东省青岛市李沧区金水路","endDate":"2019-03-04","endDistrict":"李沧区","endLat":36.19,"endLocation":"李沧区人民检察院派驻九水路检察室","endLongitude":120.47,"endMileage":8356,"endTime":"22:27","id":"336839906699644928","oilConsumption":0.6,"sid":"10000013","startAddress":"山东省青岛市李沧区九水东路","startDate":"2019-03-04","startDistrict":"李沧区","startLat":36.17,"startLocation":"青岛格瑞特兄弟汽车服务有限公司","startLongitude":120.47,"startMileage":8354,"startTime":"22:18"},{"averageSpeed":17,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:38","distance":2,"endAddress":"山东省青岛市李沧区九水东路","endDate":"2019-03-04","endDistrict":"李沧区","endLat":36.17,"endLocation":"李沧工业园","endLongitude":120.47,"endMileage":8354,"endTime":"21:05","id":"336839907387510784","oilConsumption":0.2,"sid":"10000014","startAddress":"山东省青岛市李沧区金水路","startDate":"2019-03-04","startDistrict":"李沧区","startLat":36.19,"startLocation":"李沧区人民检察院派驻九水路检察室","startLongitude":120.47,"startMileage":8352,"startTime":"20:58"},{"averageSpeed":15,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:38","distance":4,"endAddress":"山东省青岛市李沧区金水路","endDate":"2019-03-04","endDistrict":"李沧区","endLat":36.19,"endLocation":"青岛鲁强模具青岛理工大学产学研基地","endLongitude":120.47,"endMileage":8352,"endTime":"18:18","id":"336839908071182336","oilConsumption":1.2,"sid":"10000015","startAddress":"山东省青岛市李沧区中崂路","startDate":"2019-03-04","startDistrict":"李沧区","startLat":36.17,"startLocation":"东山社区爱民治安巡逻大队","startLongitude":120.44,"startMileage":8348,"startTime":"18:03"},{"averageSpeed":22,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:38","distance":9,"endAddress":"山东省青岛市李沧区中崂路","endDate":"2019-03-04","endDistrict":"李沧区","endLat":36.17,"endLocation":"青岛市李沧区第五医院-西门","endLongitude":120.44,"endMileage":8348,"endTime":"16:52","id":"336839908771631104","oilConsumption":0.8,"sid":"10000016","startAddress":"山东省青岛市李沧区宾川路","startDate":"2019-03-04","startDistrict":"李沧区","startLat":36.17,"startLocation":"青岛日田工艺装饰有限公司","startLongitude":120.48,"startMileage":8339,"startTime":"16:28"},{"averageSpeed":32,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:38","distance":5,"endAddress":"山东省青岛市李沧区宾川路","endDate":"2019-03-04","endDistrict":"李沧区","endLat":36.17,"endLocation":"阳光育才幼儿园","endLongitude":120.48,"endMileage":8339,"endTime":"13:41","id":"336839909430136832","oilConsumption":0.2,"sid":"10000017","startAddress":"山东省青岛市李沧区金水路","startDate":"2019-03-04","startDistrict":"李沧区","startLat":36.19,"startLocation":"青岛鲁强模具青岛理工大学产学研基地","startLongitude":120.47,"startMileage":8334,"startTime":"13:32"},{"averageSpeed":0,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:38","distance":0,"endAddress":"山东省青岛市李沧区金水路","endDate":"2019-03-04","endDistrict":"李沧区","endLat":36.19,"endLocation":"鲁强工业园","endLongitude":120.47,"endMileage":8334,"endTime":"11:28","id":"336839910113808384","oilConsumption":0.2,"sid":"10000018","startAddress":"山东省青岛市李沧区金水路","startDate":"2019-03-04","startDistrict":"李沧区","startLat":36.19,"startLocation":"鲁强工业园","startLongitude":120.47,"startMileage":8334,"startTime":"11:26"},{"averageSpeed":0,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:39","distance":0,"endAddress":"山东省青岛市李沧区金水路","endDate":"2019-03-04","endDistrict":"李沧区","endLat":36.19,"endLocation":"军创园","endLongitude":120.47,"endMileage":8334,"endTime":"11:23","id":"336839910768119808","oilConsumption":1.4,"sid":"10000019","startAddress":"山东省青岛市李沧区金水路","startDate":"2019-03-04","startDistrict":"李沧区","startLat":36.19,"startLocation":"青岛鲁强模具有限公司","startLongitude":120.47,"startMileage":8334,"startTime":"11:22"}],"pageNum":2,"pageSize":10,"pages":0,"total":-1}
     * msg : success
     * success : true
     */

    public DataBean data;
    public String msg;
    public boolean success;

    public static class DataBean {
        /**
         * list : [{"averageSpeed":0,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:37","distance":0,"endAddress":"山东省青岛市李沧区宾川路","endDate":"2019-03-05","endDistrict":"李沧区","endLat":36.17,"endLocation":"青岛日田工艺装饰有限公司","endLongitude":120.48,"endMileage":8361,"endTime":"12:25","id":"336839904665407488","oilConsumption":0,"sid":"10000010","startAddress":"山东省青岛市李沧区宾川路","startDate":"2019-03-05","startDistrict":"李沧区","startLat":36.17,"startLocation":"米罗湾","startLongitude":120.48,"startMileage":8361,"startTime":"12:21"},{"averageSpeed":0,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:37","distance":0,"endAddress":"山东省青岛市李沧区宾川路","endDate":"2019-03-05","endDistrict":"李沧区","endLat":36.17,"endLocation":"米罗湾","endLongitude":120.48,"endMileage":8361,"endTime":"12:09","id":"336839905323913216","oilConsumption":2.4,"sid":"10000011","startAddress":"山东省青岛市李沧区宾川路","startDate":"2019-03-05","startDistrict":"李沧区","startLat":36.17,"startLocation":"虹美医院","startLongitude":120.48,"startMileage":8361,"startTime":"12:06"},{"averageSpeed":31,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:37","distance":5,"endAddress":"山东省青岛市李沧区宾川路","endDate":"2019-03-05","endDistrict":"李沧区","endLat":36.17,"endLocation":"阳光育才幼儿园","endLongitude":120.48,"endMileage":8361,"endTime":"07:36","id":"336839905982418944","oilConsumption":0.2,"sid":"10000012","startAddress":"山东省青岛市李沧区金水路","startDate":"2019-03-05","startDistrict":"李沧区","startLat":36.19,"startLocation":"青岛鲁强模具青岛理工大学产学研基地","startLongitude":120.47,"startMileage":8356,"startTime":"07:26"},{"averageSpeed":13,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:38","distance":2,"endAddress":"山东省青岛市李沧区金水路","endDate":"2019-03-04","endDistrict":"李沧区","endLat":36.19,"endLocation":"李沧区人民检察院派驻九水路检察室","endLongitude":120.47,"endMileage":8356,"endTime":"22:27","id":"336839906699644928","oilConsumption":0.6,"sid":"10000013","startAddress":"山东省青岛市李沧区九水东路","startDate":"2019-03-04","startDistrict":"李沧区","startLat":36.17,"startLocation":"青岛格瑞特兄弟汽车服务有限公司","startLongitude":120.47,"startMileage":8354,"startTime":"22:18"},{"averageSpeed":17,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:38","distance":2,"endAddress":"山东省青岛市李沧区九水东路","endDate":"2019-03-04","endDistrict":"李沧区","endLat":36.17,"endLocation":"李沧工业园","endLongitude":120.47,"endMileage":8354,"endTime":"21:05","id":"336839907387510784","oilConsumption":0.2,"sid":"10000014","startAddress":"山东省青岛市李沧区金水路","startDate":"2019-03-04","startDistrict":"李沧区","startLat":36.19,"startLocation":"李沧区人民检察院派驻九水路检察室","startLongitude":120.47,"startMileage":8352,"startTime":"20:58"},{"averageSpeed":15,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:38","distance":4,"endAddress":"山东省青岛市李沧区金水路","endDate":"2019-03-04","endDistrict":"李沧区","endLat":36.19,"endLocation":"青岛鲁强模具青岛理工大学产学研基地","endLongitude":120.47,"endMileage":8352,"endTime":"18:18","id":"336839908071182336","oilConsumption":1.2,"sid":"10000015","startAddress":"山东省青岛市李沧区中崂路","startDate":"2019-03-04","startDistrict":"李沧区","startLat":36.17,"startLocation":"东山社区爱民治安巡逻大队","startLongitude":120.44,"startMileage":8348,"startTime":"18:03"},{"averageSpeed":22,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:38","distance":9,"endAddress":"山东省青岛市李沧区中崂路","endDate":"2019-03-04","endDistrict":"李沧区","endLat":36.17,"endLocation":"青岛市李沧区第五医院-西门","endLongitude":120.44,"endMileage":8348,"endTime":"16:52","id":"336839908771631104","oilConsumption":0.8,"sid":"10000016","startAddress":"山东省青岛市李沧区宾川路","startDate":"2019-03-04","startDistrict":"李沧区","startLat":36.17,"startLocation":"青岛日田工艺装饰有限公司","startLongitude":120.48,"startMileage":8339,"startTime":"16:28"},{"averageSpeed":32,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:38","distance":5,"endAddress":"山东省青岛市李沧区宾川路","endDate":"2019-03-04","endDistrict":"李沧区","endLat":36.17,"endLocation":"阳光育才幼儿园","endLongitude":120.48,"endMileage":8339,"endTime":"13:41","id":"336839909430136832","oilConsumption":0.2,"sid":"10000017","startAddress":"山东省青岛市李沧区金水路","startDate":"2019-03-04","startDistrict":"李沧区","startLat":36.19,"startLocation":"青岛鲁强模具青岛理工大学产学研基地","startLongitude":120.47,"startMileage":8334,"startTime":"13:32"},{"averageSpeed":0,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:38","distance":0,"endAddress":"山东省青岛市李沧区金水路","endDate":"2019-03-04","endDistrict":"李沧区","endLat":36.19,"endLocation":"鲁强工业园","endLongitude":120.47,"endMileage":8334,"endTime":"11:28","id":"336839910113808384","oilConsumption":0.2,"sid":"10000018","startAddress":"山东省青岛市李沧区金水路","startDate":"2019-03-04","startDistrict":"李沧区","startLat":36.19,"startLocation":"鲁强工业园","startLongitude":120.47,"startMileage":8334,"startTime":"11:26"},{"averageSpeed":0,"batchId":"20190614092215","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","city":"杭州市","createTime":"2019-06-14 09:22:39","distance":0,"endAddress":"山东省青岛市李沧区金水路","endDate":"2019-03-04","endDistrict":"李沧区","endLat":36.19,"endLocation":"军创园","endLongitude":120.47,"endMileage":8334,"endTime":"11:23","id":"336839910768119808","oilConsumption":1.4,"sid":"10000019","startAddress":"山东省青岛市李沧区金水路","startDate":"2019-03-04","startDistrict":"李沧区","startLat":36.19,"startLocation":"青岛鲁强模具有限公司","startLongitude":120.47,"startMileage":8334,"startTime":"11:22"}]
         * pageNum : 2
         * pageSize : 10
         * pages : 0
         * total : -1
         */

        public int pageNum;
        public int pageSize;
        public int pages;
        public int total;
        public List<ListBean> list;

        public static class ListBean {
            /**
             * averageSpeed : 0.0
             * batchId : 20190614092215
             * carId : e2a0229a-943f-4316-acc4-15fdc235941a
             * city : 杭州市
             * createTime : 2019-06-14 09:22:37
             * distance : 0.0
             * endAddress : 山东省青岛市李沧区宾川路
             * endDate : 2019-03-05
             * endDistrict : 李沧区
             * endLat : 36.17
             * endLocation : 青岛日田工艺装饰有限公司
             * endLongitude : 120.48
             * endMileage : 8361.0
             * endTime : 12:25
             * id : 336839904665407488
             * oilConsumption : 0.0
             * sid : 10000010
             * startAddress : 山东省青岛市李沧区宾川路
             * startDate : 2019-03-05
             * startDistrict : 李沧区
             * startLat : 36.17
             * startLocation : 米罗湾
             * startLongitude : 120.48
             * startMileage : 8361.0
             * startTime : 12:21
             */

            public double averageSpeed;
            public String batchId;
            public String carId;
            public String city;
            public String createTime;
            public double distance;
            public String endAddress;
            public String endDate;
            public String endDistrict;
            public double endLat;
            public String endLocation;
            public double endLongitude;
            public double endMileage;
            public String endTime;
            public String id;
            public double oilConsumption;
            public String sid;
            public String startAddress;
            public String startDate;
            public String startDistrict;
            public double startLat;
            public String startLocation;
            public double startLongitude;
            public double startMileage;
            public String startTime;

            public boolean isShow;//是否显示时间
        }
    }
}
