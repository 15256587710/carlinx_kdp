package com.ecarxclub.app.common;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.ecarxclub.app.R;
import com.ecarxclub.app.ui.home.kdp.BindCarScanActivity;
import com.ecarxclub.app.ui.login.LoginActivity;
import com.ecarxclub.app.utils.GlideCircleTransform;
import com.ecarxclub.app.utils.SharedPreferencesUtils;
import com.ecarxclub.app.utils.ToastUtils;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by cwp
 * on 2019/4/17 13:37.
 */
public class YxbContent {
    public static RequestOptions options=new RequestOptions().
            placeholder(R.mipmap.ic_app_default_shop).error(R.mipmap.ic_app_default_shop);
    public static RequestOptions options_pop=new RequestOptions().
            placeholder(R.mipmap.ic_app_default_pop).error(R.mipmap.ic_app_default_pop);
    public static RequestOptions options_pop_tags=new RequestOptions().
            placeholder(R.mipmap.ic_app_default_card_banner).error(R.mipmap.ic_app_default_card_banner);
    public static RequestOptions options_car=new RequestOptions().
            placeholder(R.mipmap.pic_default_car).error(R.mipmap.pic_default_car);
    private static Context context;
    public static RequestOptions optionsRound=new RequestOptions().transform(new GlideCircleTransform(context))
            .placeholder(R.mipmap.person_pic_default).error(R.mipmap.person_pic_default);

    //是否登录
    public static boolean getLoginToken(){
        if (SharedPreferencesUtils.getParam(YxbApplication.getContext(),
                UrlOkHttpUtils.YXB_USER_TOKEN, "").toString().length()>0){
            return true;
        }
        return false;
    }

    //是否登录
    public static boolean getIsBindCar(){
        if (YxbApplication.userInfoRes!=null&&YxbApplication.userInfoRes.data!=null&&YxbApplication.userInfoRes.data.mainCarId!=null) {//是否绑定车辆
            return true;
        }
        return false;
    }

    //去登陆
    public static void goLoginAct(Context context){
        Intent intent=new Intent(context, LoginActivity.class);//LoginNewActivity
        context.startActivity(intent);
    }

    //绑定车辆
    public static void goBindCarAct(Context context){
        Intent intent=new Intent(context, BindCarScanActivity.class);//BindCarActivity
        context.startActivity(intent);
    }

    //拨打公司电话
    public static void getCallPhone(Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + context.getResources().getString(R.string.str_callphone));
        intent.setData(data);
        context.startActivity(intent);
    }

    //拨打用户电话
    public static void getCallPeople(Context context, String buyerPhone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + buyerPhone);
        intent.setData(data);
        context.startActivity(intent);
    }

    public static void copyContent(Context context, String strCopy) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(strCopy);
        ToastUtils.showTextShort("复制成功");
    }

}
