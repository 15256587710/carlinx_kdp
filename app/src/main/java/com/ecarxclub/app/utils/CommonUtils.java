package com.ecarxclub.app.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.common.YxbApplication;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

/**
 * Created by yshu_LW.
 * on 2018/07/11
 */

public class CommonUtils {

    /**
     * 获取缓存大小
     * @param contect
     * @return
     * @throws Exception
     */
    public static String getCacheSize(Context contect) throws Exception {
        return getFormatSize(getFolderSize(contect.getCacheDir())+
                             getFolderSize(contect.getExternalCacheDir())+
                                +getFolderSize(new File("/data/data/"+ contect.getPackageName() + "/shared_prefs"))+
                +getFolderSize(new File( Environment.getExternalStorageDirectory().getPath() + UrlOkHttpUtils.YXB_SAVE_IMG)));
    }
    /**
     * 清楚内部缓存和外部缓存
     * @param context
     */
    public static void cleanCache(Context context){
        cleanInternalCache(context);
        cleanExternalCache(context);
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容
     *
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
     *
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * *
     *
     * @param context
     */
    public static void cleanDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }


    /**
     * * 按名字清除本应用数据库 * *
     *
     * @param context
     * @param dbName
     */
    public static void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }



    /**
     * * 清除内部缓存(/data/data/com.xxx.xxx/cache) * *
     *
     * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * * 清除外部缓存(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFilesByDirectory(context.getExternalCacheDir());
        }
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
     *
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }


    /**
     * 获取文件  大小
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化文件大小单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
//    public static void clearAllCache(Context context) {
//        deleteDir(context.getCacheDir());
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            deleteDir(context.getExternalCacheDir());
//        }
//    }
//
//    private static boolean deleteDir(File dir) {
//        if (dir != null && dir.isDirectory()) {
//            String[] children = dir.list();
//            for (int i = 0; i < children.length; i++) {
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    return false;
//                }
//            }
//        }
//        return dir.delete();
//    }
//
//    public static double getTotalCacheSize(Context context) {
//        long cacheSize = getFolderSize(context.getCacheDir());
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            cacheSize += getFolderSize(context.getExternalCacheDir());
//        }
//        return getFormatSize(cacheSize);
//    }
//
//    public static long getFolderSize(File file) {
//        long size = 0;
//        try {
//            File[] fileList = file.listFiles();
//            for (int i = 0; i < fileList.length; i++) {
//                // 如果下面还有文件
//                if (fileList[i].isDirectory()) {
//                    size = size + getFolderSize(fileList[i]);
//                } else {
//                    size = size + fileList[i].length();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return size;
//    }
//
//    public static double getFormatSize(double size) {
//
//        if ((size / 1024 / 1024) > 1) {
//            BigDecimal result2 = new BigDecimal(Double.toString(size / 1024 / 1024));
//            return Double.parseDouble(result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "");
//        } else {
//            return 0;
//        }
//    }
//
//    public static double getFormatSize(String string) {
//        if (string.equals("Byte")) {
//            return Double.parseDouble(string.replace("Byte", ""));
//        }
//        if (string.equals("KB")) {
//            return Double.parseDouble(string.replace("KB", ""));
//        }
//        if (string.equals("MB")) {
//            return Double.parseDouble(string.replace("MB", ""));
//        }
//        if (string.equals("GB")) {
//            return Double.parseDouble(string.replace("GB", ""));
//        }
//        if (string.equals("TB")) {
//            return Double.parseDouble(string.replace("TB", ""));
//        }
//        return 0;
//    }


    //获取友盟打包渠道名称
    public static String getMetaData (Context context,String name){
        String metaData="";
        try {
            metaData=context.getPackageManager().
                    getApplicationInfo(context.getPackageName(),PackageManager.GET_META_DATA).metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return metaData;
    }


    /**
     * 10.00 > 10  10.50 > 10.5
     *
     * @param doubleString
     * @return
     */
    public static String formatDoubleString(String doubleString) {
        if (doubleString.contains(".")) {
            if (doubleString.endsWith("0") || doubleString.endsWith(".")) {
                return formatDoubleString(doubleString.substring(0, doubleString.length() - 1));
            } else {
                return doubleString;
            }
        } else {
            return doubleString;
        }
    }
//获取设备号
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return "0";
        }
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    //获取MAC地址来作为唯一的设备码
    public static String getMacAddress() {
        String macAddress =null;
        @SuppressLint("WifiManagerLeak")
        WifiManager wifiManager =(WifiManager) YxbApplication.getContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = (null== wifiManager ?null: wifiManager.getConnectionInfo());

        if(!wifiManager.isWifiEnabled())
        {
            //必须先打开，才能获取到MAC地址
            wifiManager.setWifiEnabled(true);
            wifiManager.setWifiEnabled(false);
        }
        if(null!= info) {
            macAddress = info.getMacAddress();
        }
        return macAddress;
    }


    /**
     * 收集App运行设备信息
     *
     * @return
     */
    public static String collectDeviceInfo() {
        StringBuilder deviceInfo = new StringBuilder("Product: " + Build.PRODUCT);
        deviceInfo.append(", DEVICE: " + Build.DEVICE);
        deviceInfo.append(", MODEL: " + Build.MODEL);
        deviceInfo.append(", CPU_ABI: " + Build.CPU_ABI);
        deviceInfo.append(", VERSION.RELEASE: " + Build.VERSION.RELEASE);
        deviceInfo.append(", SDK: " + Build.VERSION.SDK_INT);
        deviceInfo.append(", DISPLAY: " + Build.DISPLAY);
        deviceInfo.append(", VERSION_CODES.BASE: " + Build.VERSION_CODES.BASE);
        deviceInfo.append(", ID: " + Build.ID);
        deviceInfo.append(", TAGS: " + Build.TAGS);

        Log.i("cwp","Product: " + Build.PRODUCT + "\n"
                + "DEVICE: " + Build.DEVICE + "\n"
                + "MODEL: " + Build.MODEL + "\n"
                + "CPU_ABI: " + Build.CPU_ABI + "\n"
                + "VERSION.RELEASE: " + Build.VERSION.RELEASE + "\n"
                + "SDK: " + Build.VERSION.SDK_INT + "\n"
                + "DISPLAY: " + Build.DISPLAY + "\n"
                + "VERSION_CODES.BASE: " + Build.VERSION_CODES.BASE + "\n"
                + "ID: " + Build.ID + "\n"
                + "TAGS: " + Build.TAGS);
        return deviceInfo.toString();

    }

    /**
     * 监测手机是否安装了微信
     *
     * @param context
     * @return
     */
//    public static boolean isWebchatAvaliable(Context context) {
//        //检测手机上是否安装了微信
////        try {
////            context.getPackageManager().getPackageInfo("com.tencent.mm", PackageManager.GET_ACTIVITIES);
////            return true;
////        } catch (Exception e) {
////            return false;
////        }
//
//        if (WXAPIFactory.createWXAPI(context, YSApplication.WX_ID).isWXAppInstalled()) {
//            return true;
//        } else {
//            final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
//            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
//            if (pinfo != null) {
//                for (int i = 0; i < pinfo.size(); i++) {
//                    String pn = pinfo.get(i).packageName;
//                    if (pn.equalsIgnoreCase("com.tencent.mm")) {
//                        return true;
//                    }
//                }
//            }
//            return false;
//        }
//
//    }


    /**
     * 银行卡号,保留最后4位,其他星号替换
     *
     * @param cardId 卡号
     * @return
     */
    public static String cardIdToHide(String cardId) {
        return cardId.replaceAll("\\d{15}(\\d{3})", "**** **** **** **** $1");
    }


    /**
     * 得到从min到max之间的随机数
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandom(int min, int max) {
        if (min > max) {
            return 0;
        }
        if (min == max) {
            return min;
        }
        return min + new Random().nextInt(max - min);
    }

    /**
     * 获取当前应用程序VersionName
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName.split("-")[0];
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.0";
    }

    /**
     * 验证邮箱号是否规范
     *
     * @param strEmail
     * @return
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strPattern)) {
            return false;
        } else {
            return strEmail.matches(strPattern);
        }
    }

    /**
     * 6~16位数字与字母组合
     *
     * @param pwd
     * @return
     */
    public static boolean isPassword(String pwd) {
        String check = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        return !TextUtils.isEmpty(pwd) && pwd.matches(check);
    }

    /**
     * 验证手机号码格式是否正确
     *
     * @param mobiles
     * @return
     */
    public static boolean isPhoneNumber(String mobiles) {
        String telRegex = "[1][3-9]\\d{9}";
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            mobiles = mobiles.replaceAll("\\s*", "");
            return mobiles.matches(telRegex);
        }
    }


    public static String hideEmail(String email) {
        if (email.length() >= 12) {
            int index = email.indexOf("@");
            return email.substring(0, index - 4) + "****" + email.substring(index, email.length());
        } else {
            return email;
        }
    }

    /**
     * 手机号用****号隐藏中间数字
     *
     * @param phone
     * @return
     */
    public static String hidePhone(String phone) {
        String phone_s = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phone_s;
    }


    /**
     * 获取指定文件大小 　　
     */
    public static String getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {

            Log.e("获取文件大小", "文件不存在!");
        }
        return toFileSize(size);
    }

    /**
     * 转换文件大小
     */
    public static String toFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }


    /**
     * 检查包是否存在
     *
     * @param packname
     * @return
     */
    private boolean checkPackInfo(Context context,String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    public static boolean openPackage(Context context, String packageName) {
        Context pkgContext = getPackageContext(context, packageName);
        Intent intent = getAppOpenIntentByPackageName(context, packageName);
        if (pkgContext != null && intent != null) {
            pkgContext.startActivity(intent);
            return true;
        }
        return false;
    }

    public static Intent getAppOpenIntentByPackageName(Context context, String packageName) {
        //Activity完整名
        String mainAct = null;
        //根据包名寻找
        PackageManager pkgMag = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);

        @SuppressLint("WrongConstant")
        List<ResolveInfo> list = pkgMag.queryIntentActivities(intent, PackageManager.GET_ACTIVITIES);
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo info = list.get(i);
            if (info.activityInfo.packageName.equals(packageName)) {
                mainAct = info.activityInfo.name;
                break;
            }
        }
        if (TextUtils.isEmpty(mainAct)) {
            return null;
        }
        intent.setComponent(new ComponentName(packageName, mainAct));
        return intent;
    }

    public static Context getPackageContext(Context context, String packageName) {
        Context pkgContext = null;
        if (context.getPackageName().equals(packageName)) {
            pkgContext = context;
        } else {
            // 创建第三方应用的上下文环境
            try {
                pkgContext = context.createPackageContext(packageName,
                        Context.CONTEXT_IGNORE_SECURITY
                                | Context.CONTEXT_INCLUDE_CODE);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return pkgContext;
    }

}
