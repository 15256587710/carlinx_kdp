package com.ecarxclub.app.common;

/**
 * Created by cwp
 * on 2019/4/17 14:09.
 * 网络请求接口url
 */
public class UrlOkHttpUtils {
//    public static String YXB_URL = "https://app.carlinx.cn/yxb/api/";//正式域名
    public static String YXB_URL = "http://test.carlinx.cn/yxb/api/";//测试域名
//    public static String YXB_URL="http://192.168.0.176:8080/advert_war_exploded/yxb/api/";//测试域名

    public static String YXB_USER_TOKEN = "USER_TOKEN";//用户token
    public static String YXB_PUSH_ID = "PUSH_ID";//极光id
    public static String YXB_WELCOME_IMGURL = "YXB_WELCOME_IMGURL";//开屏页广告路径
    public static String YXB_WELCOME_IMG = "YXB_WELCOME_IMG";//开屏页广告 model

    public static String YXB_FIRST_OPEN = "YXB_FIRST_OPEN";//第一次打开app
    public static String YXB_UPDATE_APP = "YXB_UPDATE_APP";//暂不更新
    public static String YXB_USER_MOBILE = "YXB_USER_MOBILE";//用户手机号

    public static String YXB_SET_MYURL = "";//自定义路径
    public static boolean isUploadImg = false;//是否上传图片
    public static String YXB_SAVE_IMG = "/Download/kadoupai/";//保存sd卡路径

}
