package com.ecarxclub.app.common;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.ecarxclub.app.model.UserInfoRes;
import com.ecarxclub.app.model.weixin.WXAccount;
import com.ecarxclub.app.utils.ToastUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.socialize.PlatformConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by cwp
 * on 2019/4/18 17:50.
 */
public class YxbApplication extends Application {

    public static Context yxbContext;
    public static UserInfoRes userInfoRes;
    public static String WX_ID = "wx4d6617bbdda011df";//wx7c0e5c74a07e5d7d
    public static IWXAPI WX_API;
    public static WXAccount wxAccount;
    public static String WX_SECRET = "34e1f1929090c0da23f44f1859eb3000";//3baf1193c85774b3fd9d18447d76cab0   7e6df97be838f75d0c46e7fd4810e619
    /**
     * 当前Acitity个数
     */
    private int activityAount = 0;
    private boolean isForeground = false;//false  后台

    public static Context getContext() {
        return yxbContext;
    }

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        yxbContext = getApplicationContext();
        userInfoRes = new UserInfoRes();
//        wxAccount = new WXAccount();
        initUmeng();//友盟
        refWatcher = setupLeakCanary();//内存泄漏工具 leakCanary
        registerInit();
    }

    //延迟注册
    private void registerInit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.i("延迟注册", "");
                initPushSdk();//极光推送
                setShareForm();//分享
//        getTestDeviceInfo(this);
                initWchatApi();
                initCrashReport();//腾讯bugly
                registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
                handleSSLHandshake();
            }
        }).start();
    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            ToastUtils.i("", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        YxbApplication yxbApplication = (YxbApplication) context.getApplicationContext();
        return yxbApplication.refWatcher;
    }

    //友盟注册
    private void initUmeng() {
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    private void setShareForm() {
        PlatformConfig.setWeixin(WX_ID, WX_SECRET);//fa2f7d1c7c33c2d16314c43c8106afe1
        //豆瓣RENREN平台目前只能在服务器端配置  新浪微博(第三个参数为回调地址)
//        PlatformConfig.setSinaWeibo("2340591720", "23cd6d7ea8e9378a62839caf32478c6f"
//                , "http://sns.whalecloud.com");
//        PlatformConfig.setQQZone("1108388370", "7QQBFO9M6KhtasWe");
    }

    /**
     * 注册极光推送
     */
    private void initPushSdk() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    /**
     * 腾讯bug
     * 测试阶段建议设置成true，发布时设置为false。
     */
    public void initCrashReport() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, "42d5bef686", false, strategy);//注册时申请的APPID
//        CrashReport.initCrashReport(getApplicationContext(), "42d5bef686", true);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 注册微信API
     */
    private void initWchatApi() {
        WX_API = WXAPIFactory.createWXAPI(this, WX_ID, true);
        WX_API.registerApp(WX_ID);
    }


    //友盟测试id  友盟上填写测试账号id
    public static String[] getTestDeviceInfo(Context context) {
        String[] deviceInfo = new String[2];
        try {
            if (context != null) {
                deviceInfo[0] = DeviceConfig.getDeviceIdForGeneral(context);
                deviceInfo[1] = DeviceConfig.getMac(context);
            }
        } catch (Exception e) {
        }
//        {"device_id":"868349025429822","mac":"68:3e:34:39:ae:20"}
        ToastUtils.i("友盟id  ", deviceInfo[0] + "  +++++  " + deviceInfo[1]);
        return deviceInfo;
    }


    /**
     * Activity 生命周期监听，用于监控app前后台状态切换
     */
    ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (activityAount == 0) {
                //app回到前台
                isForeground = true;
                JPushInterface.clearAllNotifications(yxbContext);//清空推送消息
            }
            activityAount++;
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityAount--;
            if (activityAount == 0) {
                isForeground = false;
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    };

    //h5 证书  glide加载图片
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};
            SSLContext sc = SSLContext.getInstance("TLS");
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }

}
