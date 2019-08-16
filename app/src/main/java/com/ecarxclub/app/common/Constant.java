package com.ecarxclub.app.common;

/**
 * Created by cwp
 * on 2019/4/17 17:52.
 */
public interface Constant {
    // 是否打印日志
    boolean LOGGER_IS_SHOWLOG = true;

    int GET_LIST_INDEX=1;
    int GET_LIST_SIZE=10;
    // 重复点击 响应时间差
    int DURATION = 1000;
    int HALF_DURATION = 500;


    //登录信息过期
    int UN_LOGIN=2000;
    String UN_LOGIN_TOKEN="un_login";

    //首页tab切换
    int MAIN_TAB_CHANGE=2001;
    String MAIN_TAB_CHANGE_MAME="tab_1";
    String MAIN_TAB_CHANGE_MAME2="tab_2";
    String MAIN_TAB_CHANGE_MAME3="tab_3";
    String MAIN_TAB_CHANGE_MAME4="tab_4";

    //添加地址
    int MINE_ADD_ADDRESS=2002;
    String MINE_ADD_ADDRESS_CONTENT="add";

    //兑换详情  地址选择
    int EXCHANGE_DETAILS_ADDRESS=2003;
    //兑换详情  地址添加
    int EXCHANGE_DETAILS_ADDRESS_ADD=2004;

    //登录成功
    int LOGIN_SUCCESS=2005;
    String LOGIN_SUCCESS_TXT="success";

    //退出登录
    int LOGIN_OUT=2017;

    // 用户积分刷新
    int USER_SCORE=2006;
    int USER_SCORE_PAY=2015;

    // 订单列表刷新
    int ORDER_LIST=2007;

    String TOKEN="token";
    String PHONENUMBER="phoneNumber";

    // 修改用户信息  1 消息条数刷新
    int USER_INFO_SUCCESS=2008;

    // 定位成功后 调用天气接口
    int USER_LOCATION_SUCCESS=2009;

    // 切换绑定车辆
    int USER_CHANGE_CAR=2010;

    // 切换mainactivity tab
    int USER_CHANGE_TAB=2011;

    // fragment 银联支付回调
    int USER_PAY_YINLIAN=2012;
    int USER_PAY_YINLIAN_STATE=2013;//银联fragment  index

    // 下载app储存权限  回调
    int USER_SAVE_PERMISS=2014;

    // 登录成功  首页获取到用户信息后   刷新积分商城
    int USER_LOGIN_SUCCESS_HOME=2016;

    // 首页刷新  从新定位
    int USER_UPDATE_LOCATION=2018;

    // 积分记录 更改总积分
    int USER_INTEGRAL=2019;
    // 签到  刷新积分中心
    int USER_SIGNIN=2020;
    // 微信登录  授权回调
    int USER_WECHAT=2021;
    // 登录成功  结束登录activity
    int USER_FINISH_LOGINACT=2022;
    // 绑定微信成功  刷新用户数据
    int USER_BIND_WECHAT=2023;

    // 完善车辆信息  保存后刷新车辆信息
    int USER_UPDATE_CAR=2024;
    // 积分中心  分享成功后 刷新积分数据
    int USER_SHARE_SUCCESS=2025;
    // 微信支付回调
    int USER_PAY_WEICHATE=2026;
}
