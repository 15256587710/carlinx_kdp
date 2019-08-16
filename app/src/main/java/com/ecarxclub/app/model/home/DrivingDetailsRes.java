package com.ecarxclub.app.model.home;

/**
 * Created by cwp
 * on 2019/6/14 11:26.
 * 行驶记录  详情
 */
public class DrivingDetailsRes {

    /**
     * success : true
     * msg : success
     * data : {"createTime":"2019-06-14 09:22:43","id":"336839930577817600","sid":"10000049","carId":"1ca7c9d6-6930-473f-ac67-e494d737f1d0","startTime":"07:28","startDate":"2019-02-25","startMileage":8037,"startLat":36.17,"startLongitude":120.49,"oilConsumption":0,"averageSpeed":19,"distance":1,"endTime":"07:31","endDate":"2019-02-25","endMileage":8038,"endLat":36.17,"endLongitude":120.48,"originalTime":null,"startLocation":"青岛日田工艺装饰有限公司","endLocation":"虹美医院","batchId":"20190614092215","startDistrict":"李沧区","endDistrict":"李沧区","startAddress":"山东省青岛市李沧区","endAddress":"山东省青岛市李沧区宾川路","minute":3}
     */

    public boolean success;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * createTime : 2019-06-14 09:22:43
         * id : 336839930577817600
         * sid : 10000049
         * carId : 1ca7c9d6-6930-473f-ac67-e494d737f1d0
         * startTime : 07:28
         * startDate : 2019-02-25
         * startMileage : 8037.0
         * startLat : 36.17
         * startLongitude : 120.49
         * oilConsumption : 0.0
         * averageSpeed : 19.0
         * distance : 1.0
         * endTime : 07:31
         * endDate : 2019-02-25
         * endMileage : 8038.0
         * endLat : 36.17
         * endLongitude : 120.48
         * originalTime : null
         * startLocation : 青岛日田工艺装饰有限公司
         * endLocation : 虹美医院
         * batchId : 20190614092215
         * startDistrict : 李沧区
         * endDistrict : 李沧区
         * startAddress : 山东省青岛市李沧区
         * endAddress : 山东省青岛市李沧区宾川路
         * minute : 3
         */

        public String createTime;
        public String id;
        public String sid;
        public String carId;
        public String startTime;
        public String startDate;
        public double startMileage;
        public double startLat;
        public double startLongitude;
        public double oilConsumption;
        public double averageSpeed;
        public double distance;
        public String endTime;
        public String endDate;
        public double endMileage;
        public double endLat;
        public double endLongitude;
        public Object originalTime;
        public String startLocation;
        public String endLocation;
        public String batchId;
        public String startDistrict;
        public String endDistrict;
        public String startAddress;
        public String endAddress;
        public int minute;
    }
}
