package com.ecarxclub.app.model.home.car;

/**
 * Created by cwp
 * on 2019/7/22 17:02.
 * 车辆基本信息
 */
public class CarInfoRes {

    /**
     * success : true
     * msg : success
     * data : {"createTime":"2019-06-14 11:01:46","id":"336864858047909888","carId":"e2a0229a-943f-4316-acc4-15fdc235941a","vin":"L6T7722Z0JZ*","vinOrigin":null,"license":null,"licenseOrigin":null,"averageSpeed":null,"averageEconomy":null,"fuelType":null,"innage":null,"buyTime":"2019-06-28 16:59:55","buyTimeOrigin":null,"lastServerTime":null,"lastServerMileage":null,"totalMileage":null,"journeyMileage":null,"originalTime":null,"batchId":"20190614110135","carStyle":null,"carStyleOrigin":null,"carImage":"https://carlinx.oss-cn-hangzhou.aliyuncs.com/userCar/20190626341205972439666688.png","endLat":null,"endLongitude":null,"endLocation":null,"totalTankOil":50,"fullMileage":700,"engineSerialNo":null,"engineSerialNoOrigin":null,"carColor":null,"carColorOrigin":null,"bind":true}
     */

    public boolean success;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * createTime : 2019-06-14 11:01:46
         * id : 336864858047909888
         * carId : e2a0229a-943f-4316-acc4-15fdc235941a
         * vin : L6T7722Z0JZ*
         * vinOrigin : null
         * license : null
         * licenseOrigin : null
         * averageSpeed : null
         * averageEconomy : null
         * fuelType : null
         * innage : null
         * buyTime : 2019-06-28 16:59:55
         * buyTimeOrigin : null
         * lastServerTime : null
         * lastServerMileage : null
         * totalMileage : null
         * journeyMileage : null
         * originalTime : null
         * batchId : 20190614110135
         * carStyle : null
         * carStyleOrigin : null
         * carImage : https://carlinx.oss-cn-hangzhou.aliyuncs.com/userCar/20190626341205972439666688.png
         * endLat : null
         * endLongitude : null
         * endLocation : null
         * totalTankOil : 50.0
         * fullMileage : 700.0
         * engineSerialNo : null
         * engineSerialNoOrigin : null
         * carColor : null
         * carColorOrigin : null
         * bind : true
         *
         *
         * id:车辆信息id
         carStyle:车型
         carColor：车辆颜色                 属性名+Origin   为1时不让修改 ，其它可以修改
         engineSerialNo：发动机编号
         license：车牌
         vin：车架号
         buyTime：购买日期
         */

        public String createTime;
        public String id;
        public String carId;
        public String vin;
        public int vinOrigin;
        public String license;
        public int licenseOrigin;
        public Object averageSpeed;
        public Object averageEconomy;
        public Object fuelType;
        public Object innage;
        public String buyTime;
        public int buyTimeOrigin;
        public Object lastServerTime;
        public Object lastServerMileage;
        public Object totalMileage;
        public Object journeyMileage;
        public Object originalTime;
        public String batchId;
        public String carStyle;
        public int carStyleOrigin;//1不可改  其他可改
        public String carImage;
        public Object endLat;
        public Object endLongitude;
        public Object endLocation;
        public double totalTankOil;
        public double fullMileage;
        public String engineSerialNo;
        public int engineSerialNoOrigin;
        public String carColor;
        public int carColorOrigin;
        public boolean bind;
        public String carStyleCode;//车型id
    }
}
