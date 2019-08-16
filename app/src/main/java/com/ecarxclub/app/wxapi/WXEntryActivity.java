package com.ecarxclub.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.ecarxclub.app.api.ApiRetrofit;
import com.ecarxclub.app.api.ApiServer;
import com.ecarxclub.app.base.gson.BaseConverterFactory;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.listener.JsonCallback;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.model.weixin.WXAccount;
import com.ecarxclub.app.model.weixin.WXToken;
import com.ecarxclub.app.model.weixin.WXUserInfo;
import com.ecarxclub.app.utils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import static com.ecarxclub.app.common.YxbApplication.WX_ID;
import static com.ecarxclub.app.common.YxbApplication.WX_API;
import static com.ecarxclub.app.common.YxbApplication.WX_SECRET;
import static com.ecarxclub.app.common.YxbApplication.wxAccount;

/**
 * Created by cwp
 * on 2019/6/6 9:58.
 * 分享
 *
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private String openId, accessToken, refreshToken, nickName, porTrait, userSex;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        WX_API = WXAPIFactory.createWXAPI(this, WX_ID, true);
        WX_API.registerApp(WX_ID);
        try {
            WX_API.handleIntent(this.getIntent(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WX_API.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.i("cwp", "onReq  " + baseReq.getType() + "");
    }

    @Override
    public void onResp(BaseResp baseResp) {
        ToastUtils.i("onResp","____"+baseResp.errCode);
//        是用baseResp.getType()== ConstantsAPI.COMMAND_SENDAUTH
        //和baseResp.getType()==ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX
        try {
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    ToastUtils.i("type ",""+baseResp.getType());
                    if (baseResp.getType()==ConstantsAPI.COMMAND_SENDAUTH){//登录
                        SendAuth.Resp resp = null;
                        try {
                            resp = (SendAuth.Resp) baseResp;
                        } catch (ClassCastException e) {
                            ToastUtils.i("","finish");
                            finish();
                        }
                        if ("wchat_login".equals(resp.state)) {//wchat_login
                            //用code去获取用户的openId
                            ToastUtils.i("getWXEntry","______________"+resp.code);
//                        getWXEntry(resp);
                            EventBus.getDefault().post(new EventMessage(Constant.USER_WECHAT,resp.code));
                            finish();
                            return;
                        }
                    }else if (baseResp.getType()==ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX){//分享
                        EventBus.getDefault().post(new EventMessage(Constant.USER_SHARE_SUCCESS,""));
                        ToastUtils.i("","微信分享成功");
                        finish();
                    }
//                    if (baseResp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {//微信小程序回调
//                        WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) baseResp;
//                        String extraData =launchMiniProResp.extMsg; //对应小程序组件 <button open-type="launchApp"> 中的 app-parameter 属性
//                    }
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://发送取消
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED://发送被拒绝
                    finish();
                    break;
                default://发送返回
                    finish();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ToastUtils.i("requestCode "+requestCode,"   ___resultCode"+resultCode);
        super.onActivityResult(requestCode, resultCode, data);
    }


    protected void getWXEntry(SendAuth.Resp WX_RESP) {
//        ToastUtils.i("getWXEntry","______________"+WX_RESP.code);
//        OkHttpClient client=new OkHttpClient.Builder()
////                .addInterceptor(new AppendHeaderParamIntercepter())
////                .addInterceptor(new ApiRetrofit.LogInterceptor())
////                .addInterceptor(new CommonInterceptor())
//                .connectTimeout(20, TimeUnit.SECONDS)
//                .readTimeout(20,TimeUnit.SECONDS)
//                .build();
//        //1.创建Retrofit对象
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://api.weixin.qq.com/sns/oauth2/")
//                .addConverterFactory(BaseConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(client)
//                .build();
//        //2.获取ApiService 接口服务对象
////        UrlOkHttpUtils.YXB_SET_MYURL="https://api.weixin.qq.com/sns/oauth2/";
//        ApiServer apiService = retrofit.create(ApiServer.class);
////        ApiServer apiService = ApiRetrofit.getInstance().getApiService();
//        //3.获取Call 对象
//        Call<WXToken> call =  apiService.getWXEntry(WX_ID,WX_SECRET,WX_RESP.code,"authorization_code");
//        call.enqueue(new Callback<WXToken>() {
//            @Override
//            public void onResponse(Call<WXToken> call, Response<WXToken> response) {
//                UrlOkHttpUtils.YXB_SET_MYURL="";
//                ToastUtils.i("onResponse",""+call.toString());
//                WXToken wxToken = response.body();
//                openId = wxToken.getUnionid() != null ? wxToken.getUnionid() : wxToken.getOpenid();
//                accessToken = wxToken.getAccess_token();
//                refreshToken = wxToken.getRefresh_token();
//                ToastUtils.i(openId+"<<<<",accessToken+"_____"+refreshToken);
//                getWXUserInfo(accessToken, openId);
//            }
//
//            @Override
//            public void onFailure(Call<WXToken> call, Throwable t) {
//                UrlOkHttpUtils.YXB_SET_MYURL="";
//                ToastUtils.i("onFailure",""+call.toString());
//            }
//        });
        OkGo.<WXToken>get("https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + WX_ID + "&secret=" + WX_SECRET + "&code=" + WX_RESP.code + "&grant_type=authorization_code")
                .execute(new JsonCallback<WXToken>() {
                    @Override
                    public void onSuccess(Response<WXToken> response) {
                        WXToken wxToken = response.body();
                        openId = wxToken.getUnionid() != null ? wxToken.getUnionid() : wxToken.getOpenid();
                        accessToken = wxToken.getAccess_token();
                        refreshToken = wxToken.getRefresh_token();
                        ToastUtils.i(openId+"<<<<",accessToken+"_____"+refreshToken);
//                        getWXUserInfo(accessToken, openId);
                    }
                    @Override
                    public void onError(Response<WXToken> response) {
                    }
                });
    }

    protected void getWXUserInfo(final String accessToken, final String openId) {
//        UrlOkHttpUtils.YXB_SET_MYURL="https://api.weixin.qq.com/sns/";
//        //2.获取ApiService 接口服务对象
//        ApiServer apiService = ApiRetrofit.getInstance().getApiService();
//        //3.获取Call 对象
//        final Call<WXUserInfo> call = apiService.getWXUserInfo(accessToken,openId);
//        new Thread(new Runnable() { //子线程执行
//            @Override
//            public void run() {
//                //4.Call对象执行请求
//                try {
//                    Response<WXUserInfo> response = call.execute();
//                    final WXUserInfo wxUserInfo = response.body();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            userSex = "保密";
//                            if (wxUserInfo.getSex() == 1) {
//                                userSex = "男";
//                            } else if (wxUserInfo.getSex() == 2) {
//                                userSex = "女";
//                            }
//                            nickName = wxUserInfo.getNickname();
//                            porTrait = wxUserInfo.getHeadimgurl();
//                            //返回数据
//                            wxAccount = new WXAccount();
//                            ToastUtils.i(""+nickName,"___"+porTrait);
//                            wxAccount.setLoginCode("signIn_weixin");
//                            wxAccount.setOpenId(openId);
//                            wxAccount.setAccessToken(accessToken);
//                            wxAccount.setRefreshToken(refreshToken);
//                            wxAccount.setNickName(nickName);
//                            wxAccount.setSex(userSex);
//                            wxAccount.setPorTrait(porTrait);
////                            RxBus.getDefault().post(new EventMessage(Constant.SIGNIN_TYPE_WEIXIN, ""));
//                            finish();
//                        }
//                    });
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();

        OkGo.<WXUserInfo>get("https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId)
                .execute(new JsonCallback<WXUserInfo>() {
                    @Override
                    public void onSuccess(Response<WXUserInfo> response) {
                        WXUserInfo wxUserInfo = response.body();
                        userSex = "保密";
                        if (wxUserInfo.getSex() == 1) {
                            userSex = "男";
                        } else if (wxUserInfo.getSex() == 2) {
                            userSex = "女";
                        }
                        nickName = wxUserInfo.getNickname();
                        porTrait = wxUserInfo.getHeadimgurl();
                        //返回数据
                        wxAccount = new WXAccount();
                        wxAccount.setLoginCode("signIn_weixin");
                        ToastUtils.i("那么————"+nickName,"___"+porTrait);
                        wxAccount.setOpenId(openId);
                        wxAccount.setAccessToken(accessToken);
                        wxAccount.setRefreshToken(refreshToken);
                        wxAccount.setNickName(nickName);
                        wxAccount.setSex(userSex);
                        wxAccount.setPorTrait(porTrait);
//                        RxBus.getDefault().post(new EventMessage(Constant.SIGNIN_TYPE_WEIXIN, ""));
                        finish();
                    }

                    @Override
                    public void onError(Response<WXUserInfo> response) {
                        ToastUtils.showTextShort("微信授权登录失败，请选择其它登录方式");
                    }
                });
    }

}
