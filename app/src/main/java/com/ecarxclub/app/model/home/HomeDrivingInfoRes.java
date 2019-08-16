package com.ecarxclub.app.model.home;

/**
 * Created by cwp
 * on 2019/6/13 15:07.
 */
public class HomeDrivingInfoRes {

    /**
     * success : true
     * msg : success
     * data : {"car":{"createTime":"2019-06-12 11:27:58","id":"336146675376721920","carId":"1ca7c9d6-6930-473f-ac67-e494d737f1d0","vin":"L6T7722Z0JZ*","license":null,"averageSpeed":null,"averageEconomy":null,"fuelType":null,"innage":null,"buyTime":null,"lastServerTime":null,"lastServerMileage":null,"totalMileage":null,"journeyMileage":null,"originalTime":null,"batchId":"20190612112735"},"travel":{"createTime":"2019-06-13 14:29:39","id":"336554783915773952","sid":"10000096","carId":"1ca7c9d6-6930-473f-ac67-e494d737f1d0","startTime":"10:39","startDate":"2019-02-18","startMileage":7482,"startLat":36.51,"startLongitude":117.86,"oilConsumption":0.6,"averageSpeed":14,"distance":2,"endTime":"10:47","endDate":"2019-02-18","endMileage":7484,"endLat":36.5,"endLongitude":117.85,"originalTime":null,"startLocation":"博山分局西冶街派出所","endLocation":"博山分局大街派出所","batchId":"20190613142633","district":"博山区","startAddress":"山东省淄博市博山区叠羊路","endAddress":"山东省淄博市博山区西关街","minute":8}}
     */

    public boolean success;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * car : {"createTime":"2019-06-12 11:27:58","id":"336146675376721920","carId":"1ca7c9d6-6930-473f-ac67-e494d737f1d0","vin":"L6T7722Z0JZ*","license":null,"averageSpeed":null,"averageEconomy":null,"fuelType":null,"innage":null,"buyTime":null,"lastServerTime":null,"lastServerMileage":null,"totalMileage":null,"journeyMileage":null,"originalTime":null,"batchId":"20190612112735"}
         * travel : {"createTime":"2019-06-13 14:29:39","id":"336554783915773952","sid":"10000096","carId":"1ca7c9d6-6930-473f-ac67-e494d737f1d0","startTime":"10:39","startDate":"2019-02-18","startMileage":7482,"startLat":36.51,"startLongitude":117.86,"oilConsumption":0.6,"averageSpeed":14,"distance":2,"endTime":"10:47","endDate":"2019-02-18","endMileage":7484,"endLat":36.5,"endLongitude":117.85,"originalTime":null,"startLocation":"博山分局西冶街派出所","endLocation":"博山分局大街派出所","batchId":"20190613142633","district":"博山区","startAddress":"山东省淄博市博山区叠羊路","endAddress":"山东省淄博市博山区西关街","minute":8}
         */

        public CarBean car;
        public TravelBean travel;

        public static class CarBean {
            /**
             * createTime : 2019-06-12 11:27:58
             * id : 336146675376721920
             * carId : 1ca7c9d6-6930-473f-ac67-e494d737f1d0
             * vin : L6T7722Z0JZ*
             * license : null
             * averageSpeed : null
             * averageEconomy : null
             * fuelType : null
             * innage : null
             * buyTime : null
             * lastServerTime : null
             * lastServerMileage : null
             * totalMileage : null
             * journeyMileage : null
             * originalTime : null
             * batchId : 20190612112735
             */

            public String createTime;
            public String id;
            public String carId;
            public String vin;
            public String license;//车牌
            public Object averageSpeed;
            public Object averageEconomy;
            public Object fuelType;
            public Object buyTime;
            public Object lastServerTime;
            public Object lastServerMileage;
            public double totalMileage;
            public double journeyMileage;//可续航里程
            public Object originalTime;
            public String batchId;
            public double fullMileage;
            public double totalTankOil;
            public double innage;//剩余油量
        }

        public static class TravelBean {
            /**
             * createTime : 2019-06-13 14:29:39
             * id : 336554783915773952
             * sid : 10000096
             * carId : 1ca7c9d6-6930-473f-ac67-e494d737f1d0
             * startTime : 10:39
             * startDate : 2019-02-18
             * startMileage : 7482.0
             * startLat : 36.51
             * startLongitude : 117.86
             * oilConsumption : 0.6
             * averageSpeed : 14.0
             * distance : 2.0
             * endTime : 10:47
             * endDate : 2019-02-18
             * endMileage : 7484.0
             * endLat : 36.5
             * endLongitude : 117.85
             * originalTime : null
             * startLocation : 博山分局西冶街派出所
             * endLocation : 博山分局大街派出所
             * batchId : 20190613142633
             * district : 博山区
             * startAddress : 山东省淄博市博山区叠羊路
             * endAddress : 山东省淄博市博山区西关街
             * minute : 8
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
            public String district;
            public String startAddress;
            public String endAddress;
            public int minute;
            public String startDistrict;
            public String endDistrict;

        }
    }
}
