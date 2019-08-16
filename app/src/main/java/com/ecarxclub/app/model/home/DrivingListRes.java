package com.ecarxclub.app.model.home;

import java.util.List;

/**
 * Created by cwp
 * on 2019/6/14 15:00.
 * 行驶记录
 *  不用
 */
public class DrivingListRes {

    /**
     * success : true
     * msg : success
     * data : [{"date":1550678400,"travel":[{"createTime":"2019-06-14 09:22:46","id":"336839940249882624","sid":"10000064","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","startTime":"15:45","startDate":"2019-02-21","startMileage":7938,"startLat":36.19,"startLongitude":120.47,"oilConsumption":0.2,"averageSpeed":0,"distance":0,"endTime":"15:47","endDate":"2019-02-21","endMileage":7938,"endLat":36.19,"endLongitude":120.47,"originalTime":null,"startLocation":"鲁强工业园","endLocation":"青岛市动物疫病预防控制中心","batchId":"20190614092215","startDistrict":"李沧区","endDistrict":"李沧区","startAddress":"山东省青岛市李沧区金水路","endAddress":"山东省青岛市李沧区金水路","minute":1},{"createTime":"2019-06-14 09:22:45","id":"336839939633319936","sid":"10000063","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","startTime":"18:03","startDate":"2019-02-21","startMileage":7938,"startLat":36.19,"startLongitude":120.47,"oilConsumption":1.2,"averageSpeed":27,"distance":7,"endTime":"18:18","endDate":"2019-02-21","endMileage":7945,"endLat":36.19,"endLongitude":120.47,"originalTime":null,"startLocation":"青岛海珍食品冷藏厂","endLocation":"青岛光明应用技术研究所","batchId":"20190614092215","startDistrict":"李沧区","endDistrict":"李沧区","startAddress":"山东省青岛市李沧区金水路","endAddress":"山东省青岛市李沧区合川路","minute":15},{"createTime":"2019-06-14 09:22:45","id":"336839939050311680","sid":"10000062","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","startTime":"18:23","startDate":"2019-02-21","startMileage":7945,"startLat":36.19,"startLongitude":120.47,"oilConsumption":0.2,"averageSpeed":0,"distance":0,"endTime":"18:25","endDate":"2019-02-21","endMileage":7945,"endLat":36.19,"endLongitude":120.47,"originalTime":null,"startLocation":"青岛鲁强模具有限公司","endLocation":"鲁强工业园","batchId":"20190614092215","startDistrict":"李沧区","endDistrict":"李沧区","startAddress":"山东省青岛市李沧区合川路","endAddress":"山东省青岛市李沧区合川路","minute":1}]}]
     */

    public boolean success;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * date : 1550678400
         * travel : [{"createTime":"2019-06-14 09:22:46","id":"336839940249882624","sid":"10000064","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","startTime":"15:45","startDate":"2019-02-21","startMileage":7938,"startLat":36.19,"startLongitude":120.47,"oilConsumption":0.2,"averageSpeed":0,"distance":0,"endTime":"15:47","endDate":"2019-02-21","endMileage":7938,"endLat":36.19,"endLongitude":120.47,"originalTime":null,"startLocation":"鲁强工业园","endLocation":"青岛市动物疫病预防控制中心","batchId":"20190614092215","startDistrict":"李沧区","endDistrict":"李沧区","startAddress":"山东省青岛市李沧区金水路","endAddress":"山东省青岛市李沧区金水路","minute":1},{"createTime":"2019-06-14 09:22:45","id":"336839939633319936","sid":"10000063","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","startTime":"18:03","startDate":"2019-02-21","startMileage":7938,"startLat":36.19,"startLongitude":120.47,"oilConsumption":1.2,"averageSpeed":27,"distance":7,"endTime":"18:18","endDate":"2019-02-21","endMileage":7945,"endLat":36.19,"endLongitude":120.47,"originalTime":null,"startLocation":"青岛海珍食品冷藏厂","endLocation":"青岛光明应用技术研究所","batchId":"20190614092215","startDistrict":"李沧区","endDistrict":"李沧区","startAddress":"山东省青岛市李沧区金水路","endAddress":"山东省青岛市李沧区合川路","minute":15},{"createTime":"2019-06-14 09:22:45","id":"336839939050311680","sid":"10000062","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","startTime":"18:23","startDate":"2019-02-21","startMileage":7945,"startLat":36.19,"startLongitude":120.47,"oilConsumption":0.2,"averageSpeed":0,"distance":0,"endTime":"18:25","endDate":"2019-02-21","endMileage":7945,"endLat":36.19,"endLongitude":120.47,"originalTime":null,"startLocation":"青岛鲁强模具有限公司","endLocation":"鲁强工业园","batchId":"20190614092215","startDistrict":"李沧区","endDistrict":"李沧区","startAddress":"山东省青岛市李沧区合川路","endAddress":"山东省青岛市李沧区合川路","minute":1}]
         */

        public int date;
        public List<TravelBean> travel;

        public static class TravelBean {
            /**
             * createTime : 2019-06-14 09:22:46
             * id : 336839940249882624
             * sid : 10000064
             * carId : e2a0229a-943f-4316-acc4-15fdc235941a
             * startTime : 15:45
             * startDate : 2019-02-21
             * startMileage : 7938.0
             * startLat : 36.19
             * startLongitude : 120.47
             * oilConsumption : 0.2
             * averageSpeed : 0.0
             * distance : 0.0
             * endTime : 15:47
             * endDate : 2019-02-21
             * endMileage : 7938.0
             * endLat : 36.19
             * endLongitude : 120.47
             * originalTime : null
             * startLocation : 鲁强工业园
             * endLocation : 青岛市动物疫病预防控制中心
             * batchId : 20190614092215
             * startDistrict : 李沧区
             * endDistrict : 李沧区
             * startAddress : 山东省青岛市李沧区金水路
             * endAddress : 山东省青岛市李沧区金水路
             * minute : 1
             */

            public int date;
            public boolean isShow;

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
            public String city;
            public int minute;
        }
    }
}
