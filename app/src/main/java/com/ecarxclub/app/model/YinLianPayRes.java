package com.ecarxclub.app.model;

/**
 * Created by cwp
 * on 2019/5/13 17:05.
 */
public class YinLianPayRes {

    /**
     * data : {"accessType":"0","bizType":"000201","certId":"69026276696","encoding":"UTF-8","merId":"777290058169408","orderId":"20190513567814456","respCode":"00","respMsg":"成功[0000000]","signMethod":"01","signature":"KRIGCH2GH+Agg1eq69nKOhbQq9W1i8MZYxQDuw8dOXmPdB3ZFz9qVKnceeR/nVZ1I3UzYNYd6RO6VeK/ImhWBadK1vEQzFCEWR7Wi2Sw8yeKM9cSmorePkHl/zC8KL8F97soh1ssQDeS+sTP0LZTzRYc4OebdXopFmIEj6sNr3tj86QFX3oNb6HI7DT5eNzrYTs3dan6FVPulQE/dh+Iucly0J+HT8Eb6lFWbIJYQqiVjwcMjzdR/Q64EQPc0FthHtQUOL/KOTm++Oru5em9dtkO2qSj2kAG+5doY93V61dzGb/k9j7M5rJ546RGrkKzmQ3sjn9pMv5KK1hUWaDabg==","tn":"803166592950855992201","txnSubType":"01","txnTime":"20190513170152","txnType":"01","version":"5.0.0"}
     * msg : success
     * success : true
     */

    public DataBean data;
    public String msg;
    public boolean success;

    public static class DataBean {
        /**
         * accessType : 0
         * bizType : 000201
         * certId : 69026276696
         * encoding : UTF-8
         * merId : 777290058169408
         * orderId : 20190513567814456
         * respCode : 00
         * respMsg : 成功[0000000]
         * signMethod : 01
         * signature : KRIGCH2GH+Agg1eq69nKOhbQq9W1i8MZYxQDuw8dOXmPdB3ZFz9qVKnceeR/nVZ1I3UzYNYd6RO6VeK/ImhWBadK1vEQzFCEWR7Wi2Sw8yeKM9cSmorePkHl/zC8KL8F97soh1ssQDeS+sTP0LZTzRYc4OebdXopFmIEj6sNr3tj86QFX3oNb6HI7DT5eNzrYTs3dan6FVPulQE/dh+Iucly0J+HT8Eb6lFWbIJYQqiVjwcMjzdR/Q64EQPc0FthHtQUOL/KOTm++Oru5em9dtkO2qSj2kAG+5doY93V61dzGb/k9j7M5rJ546RGrkKzmQ3sjn9pMv5KK1hUWaDabg==
         * tn : 803166592950855992201
         * txnSubType : 01
         * txnTime : 20190513170152
         * txnType : 01
         * version : 5.0.0
         */

        public String accessType;
        public String bizType;
        public String certId;
        public String encoding;
        public String merId;
        public String orderId;
        public String respCode;
        public String respMsg;
        public String signMethod;
        public String signature;
        public String tn;
        public String txnSubType;
        public String txnTime;
        public String txnType;
        public String version;
    }
}
