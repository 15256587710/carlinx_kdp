package com.ecarxclub.app.api;

import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.UserInfoNewRes;
import com.ecarxclub.app.model.UserInfoRes;
import com.ecarxclub.app.model.YinLianPayRes;
import com.ecarxclub.app.model.card.CardBannerRes;
import com.ecarxclub.app.model.card.CardCouponTypeRes;
import com.ecarxclub.app.model.card.CardDetailsRes;
import com.ecarxclub.app.model.card.CardListRes;
import com.ecarxclub.app.model.home.AdvertisementRes;
import com.ecarxclub.app.model.home.car.BindCarRes;
import com.ecarxclub.app.model.home.car.CarInfoRes;
import com.ecarxclub.app.model.home.DrivingDetailsRes;
import com.ecarxclub.app.model.home.GetVersionRes;
import com.ecarxclub.app.model.home.HomeDrivingInfoRes;
import com.ecarxclub.app.model.home.MyDrivingListRes;
import com.ecarxclub.app.model.home.OtherServiceRes;
import com.ecarxclub.app.model.home.WeatherRes;
import com.ecarxclub.app.model.home.car.CarStyleRes;
import com.ecarxclub.app.model.login.LoginNewRes;
import com.ecarxclub.app.model.login.LoginRes;
import com.ecarxclub.app.model.login.RegisterRes;
import com.ecarxclub.app.model.mine.ConfirmRecriceRes;
import com.ecarxclub.app.model.mine.LogistiesListRes;
import com.ecarxclub.app.model.mine.MessageRes;
import com.ecarxclub.app.model.mine.MyAddressRes;
import com.ecarxclub.app.model.mine.MyCouponRes;
import com.ecarxclub.app.model.mine.MyOrderListRes;
import com.ecarxclub.app.model.pay.PayOrderRes;
import com.ecarxclub.app.model.pay.PayOrderSuccessRes;
import com.ecarxclub.app.model.shop.IntegralRecordRes;
import com.ecarxclub.app.model.shop.ShopBannerRes;
import com.ecarxclub.app.model.shop.ShopDetailsBannerRes;
import com.ecarxclub.app.model.shop.ShopDetailsRes;
import com.ecarxclub.app.model.shop.integral.SignInRes;
import com.ecarxclub.app.model.shop.integral.SignInRewordRes;
import com.ecarxclub.app.model.shop.integral.TaskListRes;
import com.ecarxclub.app.model.shop.integral.WaterRes;
import com.ecarxclub.app.model.shop.fgshop.ShopListRes;
import com.ecarxclub.app.model.shop.fgshop.ShopTagsRes;
import com.ecarxclub.app.model.shop.fgshop.TodayClockRes;
import com.ecarxclub.app.model.weixin.WXToken;
import com.ecarxclub.app.model.weixin.WXUserInfo;
import java.util.Map;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiServer {
    //获取用户信息
    @GET("user/info")
    Observable<UserInfoRes> getUserInfo();

    //获取用户个人信息
    @GET("user/integral/describe")
    Observable<UserInfoNewRes> getUserNewInfo();
    //登录
    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginRes> Login(@Field("mobile") String username, @Field("password") String password, @Field("registrationId") String pushid);

    //退出登录
    @POST("user/logout")
    Observable<BaseMsgRes> LogOut();

    //发送验证码
    @FormUrlEncoded
    @POST("sms/register")
    Observable<BaseMsgRes> getCode(@Field("mobile") String mobile);

    //注册
    @FormUrlEncoded
    @POST("user/register")
    Observable<RegisterRes> reGiisterNumber(@Field("mobile") String mobile, @Field("password") String pwd, @Field("smsCode") String code);


    //忘记密码获取验证码
    @FormUrlEncoded
    @POST("sms/password/reset")
    Observable<BaseMsgRes> getForgetCode(@Field("mobile") String mobile);

    //指定手机号领取卡券获取验证码
    @FormUrlEncoded
    @POST("sms/receiveCoupon")
    Observable<BaseMsgRes> getZdPhoneCode(@Field("mobile") String mobile,@Field("couponId") String id);

    //忘记密码校验验证码是否正确
    @FormUrlEncoded
    @POST("sms/password/reset/valid")
    Observable<BaseMsgRes> getCodeIsRight(@Field("mobile") String mobile, @Field("smsCode") String code);

    //重置密码
    @FormUrlEncoded
    @POST("user/password/reset")
    Observable<BaseMsgRes> resetPwd(@Field("password") String pwd, @Field("token") String token);

//    @GET("?cityid=101120201")
//    Observable<city> test();

    //获取天气
    @GET("thirdpart/weater")
    Observable<WeatherRes> getWeather(@Query("city") String city);

    //获取第三方服务
    @GET("services")
    Observable<OtherServiceRes> getServices();

    //广告  广告类型
    //1.	开屏广告
    //2.	弹窗广告
    //3.	悬浮按钮
    @GET("poster")
    Observable<AdvertisementRes> getAdvertise(@Query("posterType") String type);

    //获取卡券banner
    @GET("seller/banner")
    Observable<CardBannerRes> getBanner();

    //获取卡券分类
    @GET("seller/coupon/type")
    Observable<CardCouponTypeRes> getCouponType();
//    Observable<PageBean> getText (@Url String url);

    //获取卡券类型
//    @GET("seller/coupon/tags")
//    Observable<CardCouponTypeRes> getCouponTag();

    //获取卡券分类列表
    @GET("seller/coupon")
    Observable<CardListRes> getCardList(@QueryMap Map<String, String> map);

    //获取卡券分类列表
    @GET("user/message")
    Observable<MessageRes> getMessageList(@Query("pageIndex") int index,
                                          @Query("pageSize") int size);

    //重置密码
    @FormUrlEncoded
    @POST("user/message/receive/create")
    Observable<BaseMsgRes> readMsg(@Field("messageId") String id);

    /**
     * 添加地址
     *
     * @return
     */
    @FormUrlEncoded
    @POST("user/address/create")
    Observable<BaseMsgRes> addAddressResult(@FieldMap Map<String, String> map);

    /**
     * 修改地址
     */
    @FormUrlEncoded
    @POST("user/address/update")
    Observable<BaseMsgRes> updateAddressResult(@FieldMap Map<String, String> map);

    //设置默认地址
    @FormUrlEncoded
    @POST("user/address/setMain")
    Observable<BaseMsgRes> setDefaultAddress(@Field("addressId") String id);

    //删除地址
    @FormUrlEncoded
    @POST("user/address/remove")
    Observable<BaseMsgRes> deleteAddress(@Field("id") String id);

    //获取地址列表
    @GET("user/address")
    Observable<MyAddressRes> getAddressList(@Query("count") boolean count,
                                            @Query("pageIndex") int index,
                                            @Query("pageSize") int size);

    //获取我的卡券列表
    @GET("user/coupon")
    Observable<MyCouponRes> getCouponList(@Query("pageIndex") int index,
                                          @Query("pageSize") int size,
                                          @Query("count") boolean count,
                                          @Query("status") String status);

    //获取我的订单列表
    @GET("user/mall/order")
    Observable<MyOrderListRes> getOrderList(@QueryMap Map<String, String> map);


    //取消订单
    @FormUrlEncoded
    @POST("user/mall/order/cancel")
    Observable<BaseMsgRes> orderCancel(@Field("orderId") String id);

    //分享里程
    @GET("share/travel")
    Observable<BaseMsgRes> shareSuccess();

    //物流列表
    @GET("thirdpart/ship")
    Observable<LogistiesListRes> getLogisties(@Query("shipName") String name, @Query("shipNo") String number);

    //获取待领取积分
    @POST("user/integral/bubble")
    Observable<WaterRes> getBubble();

    //积分商城  banner
    @GET("seller/banner/mall")
    Observable<ShopBannerRes> getShopBanner();


    //领取积分
    @GET("user/integral/bubble/reward")
    Observable<BaseMsgRes> getBubbleid(@Query("bubbleId") String id);

    //获取签到积分
    @GET("user/integral/clock")
    Observable<BaseMsgRes> getCheckScore();

    //获取当天签到记录
    @GET("user/integral/clock/today")
    Observable<TodayClockRes> getTodayClock();

    //获取连续签到天数
    @GET("user/integral/clock/days")
    Observable<BaseMsgRes> getClockDays();


    //获取签到记录
    @GET("user/integral/clock/integralClock")
    Observable<SignInRes> getSigninRecode();

    //获取连续签到奖励
    @GET("clockAward/awards")
    Observable<SignInRewordRes> getSigninReword();

    //领取奖励
    @FormUrlEncoded
    @POST("clockAward/receive")
    Observable<BaseMsgRes> getReciveReword(@Field("awardId") String id);

    //获取用户积分
    @GET("user/integral/now")
    Observable<BaseMsgRes> getUserScore();

    //任务（每日任务+新人任务）
    @GET("user/task")
    Observable<TaskListRes> getVersionList();

    //获取积分商城标签
    @GET("mall/tags")
    Observable<ShopTagsRes> getShopTag();

    //获取积分商城产品
    @GET("mall/product")
    Observable<ShopListRes> getShopList(@QueryMap Map<String, String> map);


    //获取用户积分记录
    @GET("user/integral/records")
    Observable<IntegralRecordRes> getIntegralRecord(@Query("recordType") String type,//changeType
                                                    @Query("pageIndex") int index,
                                                    @Query("pageSize") int size);

    //确认签收订单
    @GET("user/mall/order/confirmRecive")
    Observable<ConfirmRecriceRes> getConfirmRecive(@Query("orderId") String id);

    //获取商品详情 banner
    @GET("files/{id}")
    Observable<ShopDetailsBannerRes> getShopDetailsBanner(@Path("id") String id);


    //领取卡券
    @FormUrlEncoded
    @POST("user/coupon/receive")
    Observable<BaseMsgRes> getCoupone(@FieldMap Map<String, String> map);

    //提交订单
    @FormUrlEncoded
    @POST("user/mall/order/exchange")
    Observable<PayOrderRes> upOrder(@FieldMap Map<String, String> map);


    //支付
    @FormUrlEncoded
    @POST("user/mall/order/payment")
    Observable<PayOrderSuccessRes> PayOrder(@FieldMap Map<String, String> map);

    //测试
    @FormUrlEncoded
    @POST("http://2482289k6j.wicp.vip:32875/test/unionpay/pay")
    Observable<YinLianPayRes> getText(@Field("txnAmt") String id, @Field("name") String name);


    //上传文件   @Query("uid") String uid,
    @Multipart
    @POST("user/reset/photo")
    Observable<BaseMsgRes> uploadFile(@Part("file\"; filename=\"image.jpg") RequestBody file);

    //上传文件
    @POST("user/reset/photo")
    @Multipart
    Observable<BaseMsgRes> uploadFiles(@Part MultipartBody.Part parts);

    /**
     * 大文件官方建议用 @Streaming 来进行注解，不然会出现IO异常，小文件可以忽略不注入
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);


    // 广告点击次数统计
    @FormUrlEncoded
    @POST("poster/hit")
    Observable<BaseMsgRes> onClickAdv(@Field("phoneId") String id, @Field("posterId") String advid);

    //获取版本升级内容
    @GET("version")
    Observable<GetVersionRes> getVersion(@Query("versionPort") String version);

    // 渠道下载量统计
    @FormUrlEncoded
    @POST("platform/census")
    Observable<BaseMsgRes> onCensus(@Field("phoneId") String id);

    // 获取卡券详情
    @FormUrlEncoded
    @POST("seller/coupon/detail")
    Observable<CardDetailsRes> onCouponDetails(@Field("couponId") String id);


    // 获取商品详情
    @FormUrlEncoded
    @POST("mall/product/detail")
    Observable<ShopDetailsRes> onShopDetails(@Field("productId") String id);



    //修改用户信息
    @FormUrlEncoded
    @POST("user/reset/information")
    Observable<BaseMsgRes> editUserInfo(@FieldMap Map<String, String> map);



    // 修改密码
    @FormUrlEncoded
    @POST("user/reset/password")
    Observable<BaseMsgRes> changePwd(@Field("oldPassword") String oldpwd, @Field("newPassword") String newpwd);


    //第一次 绑定车辆
    @GET("usercar")
    Observable<BaseMsgRes> getonBindCar(@Query("mobile") String mobile, @Query("smsCode") String code, @Query("idCard") String idcard);

    // 绑定车辆 获取验证码
    @FormUrlEncoded
    @POST("sms/bindCar")
    Observable<BaseMsgRes> postBindCarCode(@Field("mobile") String moble);

    //获取所有绑定车辆
    @GET("usercar/allCars")
    Observable<BindCarRes> getonAllCar();


    //选择绑定车辆
    @GET("usercar/setmain")
    Observable<BaseMsgRes> postBindCar(@Query("carId") String id);

    //首页车辆行驶信息记录
    @GET("usercar/headDetail")
    Observable<HomeDrivingInfoRes> getDrivingRecode(@Query("carId") String id);

    //车辆信息
    @GET("usercar/car")
    Observable<CarInfoRes> getCarInfo(@Query("id") String id);

    //车辆车型
    @GET("car/style/name")
    Observable<CarStyleRes> getCarStyle();

    //车辆颜色
    @GET("car/style/color")
    Observable<CarStyleRes> getCarColor(@Query("styleId") String id);

    //完善车辆信息
    @FormUrlEncoded
    @POST("usercar/carInfo")
    Observable<BaseMsgRes> updateCarDetails(@FieldMap Map<String, String> map);

    //首页车辆行驶详细信息
    @GET("carTravel/detail")
    Observable<DrivingDetailsRes> getDrivingDetails(@Query("travelId") String id);

    //首页车辆行驶详细信息
    @GET("carTravel")
    Observable<MyDrivingListRes> getDrivingList(@Query("carId") String id,
                                                @Query("pageIndex") int index,
                                                @Query("pageSize") int size);


    //微信授权登录
    @GET("userinfo")
    Call<WXUserInfo> getWXUserInfo(@Query("access_token") String token,
                                   @Query("openid") String id);

    @GET("access_token")
    Call<WXToken> getWXEntry(@Query("appid") String appid,
                                   @Query("secret") String secret,
                                   @Query("code") String code,
                                   @Query("grant_type") String grant_type);


    //微信登录输入手机号码
    @FormUrlEncoded
    @POST("user/weixinRegist")
    Observable<LoginRes> WecheatRegist(@Field("mobile") String username, @Field("smsCode") String code, @Field("key") String key, @Field("registrationId") String pushid);

    //登录  手机号码
    @FormUrlEncoded
    @POST("user/newLogin")
    Observable<LoginRes> PhoneLogin(@Field("mobile") String username, @Field("smsCode") String code, @Field("registrationId") String pushid);

    //微信登录
    @GET("user/weixinLogin")
    Observable<LoginNewRes> WechatLogin(@Query("code") String code, @Query("registrationId") String rgid);

    //获取验证码
    @GET("sms/newLogin")
    Observable<BaseMsgRes> getLoginNewCode(@Query("mobile") String phone);

    //绑定微信
    @FormUrlEncoded
    @POST("user/bindWeixin")
    Observable<BaseMsgRes> bindWechat(@Field("code") String code);

    //车机扫码登录
    @FormUrlEncoded
    @POST("code/codeLogin")
    Observable<BaseMsgRes> carLogin(@Field("codeKey") String code);


    //是否绑定车辆
    @GET("user/isBindCar")
    Observable<BaseMsgRes> isBindCar();

    //是否绑定微信
    @GET("user/isBindWeixin")
    Observable<BaseMsgRes> isBindWechat();
}
