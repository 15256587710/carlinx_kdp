package com.ecarxclub.app.model.home.car;

import java.util.List;

/**
 * Created by cwp
 * on 2019/6/12 16:53.
 * 所有车辆信息  列表接口
 */
public class BindCarRes {


    /**
     * success : true
     * msg : success
     * data : [{"createTime":"2019-06-14 09:22:31","id":"336839877570203648","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","vin":"L6T7722Z0JZ*","license":null,"averageSpeed":null,"averageEconomy":null,"fuelType":null,"innage":null,"buyTime":null,"lastServerTime":null,"lastServerMileage":null,"totalMileage":null,"journeyMileage":null,"originalTime":null,"batchId":"20190614092215","carStyle":null,"carImage":null,"endLat":36.19,"endLongitude":120.47,"endLocation":"青岛市动物疫病预防控制中心","bind":false},{"createTime":"2019-06-14 09:22:31","id":"336839877045915648","carId":"49bd0bb6-9aa0-425b-af89-3a9124708c78","vin":"L6T7722Z0JZ*","license":null,"averageSpeed":null,"averageEconomy":null,"fuelType":null,"innage":null,"buyTime":null,"lastServerTime":null,"lastServerMileage":null,"totalMileage":null,"journeyMileage":null,"originalTime":null,"batchId":"20190614092215","carStyle":null,"carImage":null,"endLat":36.19,"endLongitude":120.47,"endLocation":"军创园","bind":false},{"createTime":"2019-06-14 09:22:31","id":"336839880019677184","carId":"1ca7c9d6-6930-473f-ac67-e494d737f1d0","vin":"L6T7722Z0JZ*","license":null,"averageSpeed":null,"averageEconomy":null,"fuelType":null,"innage":null,"buyTime":null,"lastServerTime":null,"lastServerMileage":null,"totalMileage":null,"journeyMileage":null,"originalTime":null,"batchId":"20190614092215","carStyle":null,"carImage":null,"endLat":36.17,"endLongitude":120.48,"endLocation":"虹美医院","bind":false}]
     */

    public boolean success;
    public String msg;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * createTime : 2019-06-14 09:22:31
         * id : 336839877570203648
         * carId : e2a0229a-943f-4316-acc4-15fdc235941a
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
         * batchId : 20190614092215
         * carStyle : null
         * carImage : null
         * endLat : 36.19
         * endLongitude : 120.47
         * endLocation : 青岛市动物疫病预防控制中心
         * bind : false
         */

        public String createTime;
        public String id;
        public String carId;
        public String vin;
        public String license;//车牌
        public Object averageSpeed;
        public Object averageEconomy;
        public Object fuelType;
        public Object innage;
        public String buyTime;//购车时间
        public Object lastServerTime;
        public Object lastServerMileage;
        public Object totalMileage;
        public Object journeyMileage;
        public Object originalTime;
        public String batchId;
        public String carStyle;//车型
        public String carImage;
        public double endLat;
        public double endLongitude;
        public String endLocation;
        public boolean bind;
//        public boolean isCheck;//自己定义  是否绑定
    }
}
