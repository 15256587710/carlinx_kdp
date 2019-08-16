package com.ecarxclub.app.utils;


import android.graphics.Bitmap;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.YxbApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wecent on 2017/10/18
 * 使用单例ToastUtil时注意一点：比如使用了带位置的方法后，下次再使用不带位置的单例方法时，会显示成上次方法的Toast的位置
 * 所以强烈建议：全局统一的Toast使用该类中的单例方法，一旦使用了一种以上的方法，需要在不常使用的方法调用后调用resetToast()方法，重置Toast位置等（不适重置Toast对象）
 * 举例：
 * 全局一般使用的Toast是底部弹出一行简单的文字，调用：
 * ToastUtils.toast("常规的Toast方法)；
 * 然后我们有特殊需要，要居中显示一个Toast提示用户，调用：
 * ToastUtils.showSingletonText("居中显示",Toast.LENGTH_SHORT,Gravity.CENTER);
 * 这个方法调用完，其实相当于是更改了Toast的对象。不再是第一个我们常规使用的方法中所创建的，所以，
 * 我们需要重置Toast对象，其实就是创建一个新的常规对象
 */

public class ToastUtils {
    private static final String TAG = ToastUtils.class.getSimpleName();
    private static Toast toast;
    private static List<Toast> toastList = new ArrayList<>();

    public ToastUtils() {
        throw new UnsupportedOperationException("not init ToastUtils");
    }

    /**
     * 吐司文本内容
     * 单例模式,吐司时间长
     *
     * @param content 吐司内容
     */
    public static void showSingletonLong(String content) {
        if (toast == null) {
            toast = Toast.makeText(YxbApplication.getContext(), "", Toast.LENGTH_LONG);
        }
        toast.setText(content);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 吐司文本内容
     * 单例模式，吐司时间短
     *
     * @param content 吐司内容
     */
    public static void showSingletonShort(String content) {
        if (toast == null) {
            toast = Toast.makeText(YxbApplication.getContext(), "", Toast.LENGTH_SHORT);
        }
        toast.setText(content);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 吐司文本内容
     * 非单例模式,吐司时间短
     *
     * @param content 吐司内容
     */
    public static void showTextShort(String content) {
        toast = Toast.makeText(YxbApplication.getContext(), "", Toast.LENGTH_LONG);
        toastList.add(toast);
        toast.setText(content);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 吐司文本内容
     * 非单例模式,吐司时间短
     *
     * @param content 吐司内容
     */
    public static void showTextLong(String content) {
        toast = Toast.makeText(YxbApplication.getContext(), "", Toast.LENGTH_LONG);
        toastList.add(toast);
        toast.setText(content);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }


    /**
     * 吐司文本内容 自定义位置、时间
     * 单例模式
     *
     * @param content  吐司内容
     * @param duration 显示的时间
     * @param position 显示的位置
     */
    public static void showSingletonText(String content, int duration, int position) {
        if (toast == null) {
            toast = Toast.makeText(YxbApplication.getContext(), "", duration);
        }
        toast.setText(content);
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.show();
    }

    /**
     * Toast 无背景透明的文本
     *
     * @param content  内容
     * @param duration 时长
     * @param position 位置
     */
    public static void showSNBacText(String content, int duration, int position) {
        if (toast == null) {
            toast = Toast.makeText(YxbApplication.getContext(), "", duration);
        }
        LinearLayout linearLayout = new LinearLayout(YxbApplication.getContext());//创建线性布局
        linearLayout.setOrientation(LinearLayout.VERTICAL);//设置布局垂直
        TextView textView = new TextView(YxbApplication.getContext());
        textView.setText(content);
        linearLayout.addView(textView);
        toast.setView(linearLayout);
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.show();

    }


    /**
     * Toast图片
     * 单例模式，自定义时间
     *
     * @param resId    图片资源ID
     * @param duration Toast.LENGTH_LONG/Toast.LENGTH_LONG
     */
    public static void showSingletonImageCenter(int resId, int duration) {
        if (toast == null) {
            toast = Toast.makeText(YxbApplication.getContext(), "", duration);
        }
        ImageView imageView = new ImageView(YxbApplication.getContext());
        imageView.setImageResource(resId);
        toast.setView(imageView);
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * Toast图片
     * 非单例模式，自定义时间
     *
     * @param resId    图片资源ID
     * @param duration Toast.LENGTH_LONG/Toast.LENGTH_LONG
     */
    public static void showImageCenter(int resId, int duration) {
        toast = Toast.makeText(YxbApplication.getContext(), "", duration);
        toastList.add(toast);
        ImageView imageView = new ImageView(YxbApplication.getContext());//创建图片控件
        imageView.setImageResource(resId);//给控件设置图片
        toast.setView(imageView);//把图片绑定到Toast上
        toast.setDuration(duration);//Toast显示的时间;
        //设置图片显示的位置：三个参数
        //第一个：位置，可以用|添加并列位置，第二个：相对于X的偏移量，第三个：相对于Y轴的偏移量
        //注意一点：第二和第三个参数是相对于第一个参数设定的位置偏移的
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();//显示Toast
    }

    /**
     * Toast图片
     * 单例模式，自定义时间,自定义位置
     *
     * @param resId    图片资源ID
     * @param duration Toast.LENGTH_LONG/Toast.LENGTH_LONG
     * @param position Gravity.LEFT,Gravity.BOTTOM | Gravity.RIGHT...多个位置用竖线分割
     */
    public static void showSingletonImage(int resId, int duration, int position) {
        if (toast == null) {
            toast = Toast.makeText(YxbApplication.getContext(), "", duration);
        }
        ImageView imageView = new ImageView(YxbApplication.getContext());//创建图片控件
        imageView.setImageResource(resId);
        toast.setView(imageView);
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.show();
    }

    /**
     * Toast图片
     * 非单例模式，自定义时间,自定义位置
     *
     * @param resId    图片资源ID
     * @param duration Toast.LENGTH_LONG/Toast.LENGTH_LONG
     * @param position Gravity.LEFT,Gravity.BOTTOM | Gravity.RIGHT...多个位置用竖线分割
     */
    public static void showImage(int resId, int duration, int position) {
        toast = Toast.makeText(YxbApplication.getContext(), "", duration);
        toastList.add(toast);
        ImageView imageView = new ImageView(YxbApplication.getContext());//创建图片控件
        imageView.setImageResource(resId);
        toast.setView(imageView);
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.show();
    }

    /**
     * Toast图片
     * 非单例模式，自定义时间,自定义位置
     *
     * @param bitmap   图片资源ID
     * @param duration Toast.LENGTH_LONG/Toast.LENGTH_LONG
     * @param position Gravity.LEFT,Gravity.BOTTOM | Gravity.RIGHT...多个位置用竖线分割
     */
    public static void showImage(Bitmap bitmap, int duration, int position) {
        toast = Toast.makeText(YxbApplication.getContext(), "", duration);
        toastList.add(toast);
        ImageView imageView = new ImageView(YxbApplication.getContext());//创建图片控件
        imageView.setImageBitmap(bitmap);
        toast.setView(imageView);
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.show();
    }

    /**
     * 自定义显示图文结合的Toast
     * 非单例
     *
     * @param resId    图片id
     * @param content  文本内容
     * @param duration toast时长
     * @param position toast位置
     */
    public static void showIT(int resId, String content, int duration, int position) {
        toast = Toast.makeText(YxbApplication.getContext(), "", duration);
        toastList.add(toast);
        LinearLayout linearLayout = new LinearLayout(YxbApplication.getContext());//创建线性布局
        linearLayout.setOrientation(LinearLayout.VERTICAL);//设置布局垂直
        ImageView imageView = new ImageView(YxbApplication.getContext());//创建图片控件
        imageView.setImageResource(resId);//给控件设置图片
        TextView textView = new TextView(YxbApplication.getContext());//创建文本控件
        textView.setGravity(Gravity.CENTER);
        textView.setText(content);//设置文本内容
        linearLayout.addView(imageView);//添加图片控件到布局中
        linearLayout.addView(textView);//添加文本控件到布局中。注意添加顺序会影响图片在前还是为本在前
        toast.setView(linearLayout);//把布局绑定到Toast上
        toast.setDuration(duration);//Toast显示的时间;
        /**
         * position：显示位置
         * 第二个参数：相对X的偏移量
         * 第三个参数：相对Y的偏移量
         * 第二和第三个参数是相对于第一个参数设定的位置偏移的
         */
        toast.setGravity(position, 0, 0);
        toast.show();//显示Toast
    }

    /**
     * 自定义显示图文结合的Toast
     * 单例
     *
     * @param resId    图片id
     * @param content  文本内容
     * @param duration toast时长
     * @param position toast位置
     */
    public static void showITSingleton(int resId, String content, int duration, int position) {
        if (toast == null) {
            toast = Toast.makeText(YxbApplication.getContext(), "", duration);
        }
        toastList.add(toast);
        LinearLayout linearLayout = new LinearLayout(YxbApplication.getContext());//创建线性布局
        linearLayout.setOrientation(LinearLayout.VERTICAL);//设置布局垂直
        ImageView imageView = new ImageView(YxbApplication.getContext());//创建图片控件
        imageView.setImageResource(resId);//给控件设置图片
        TextView textView = new TextView(YxbApplication.getContext());//创建文本控件
        textView.setText(content);//设置文本内容
        linearLayout.addView(imageView);//添加图片控件到布局中
        linearLayout.addView(textView);//添加文本控件到布局中。注意添加顺序会影响图片在前还是为本在前
        toast.setView(linearLayout);//把布局绑定到Toast上
        toast.setDuration(duration);//Toast显示的时间;
        /**
         * position：显示位置
         * 第二个参数：相对X的偏移量
         * 第三个参数：相对Y的偏移量
         * 第二和第三个参数是相对于第一个参数设定的位置偏移的
         */
        toast.setGravity(position, 0, 0);
        toast.show();//显示Toast
    }

    /**
     * Toast 多行文本 非单例
     *
     * @param size     字体大小
     * @param contents list 形式的文本内容
     */
    public static void showLines(List<String> contents, int size) {
        toast = Toast.makeText(YxbApplication.getContext(), "", Toast.LENGTH_LONG);
        toastList.add(toast);
        LinearLayout linearLayoutTop = new LinearLayout(YxbApplication.getContext());//创建线性布局
        linearLayoutTop.setOrientation(LinearLayout.VERTICAL);//设置布局垂直
        for (int i = 0; i < contents.size(); i++) {
            TextView textView = new TextView(YxbApplication.getContext());
            textView.setText(contents.get(i));
            textView.setTextSize(size);
            linearLayoutTop.addView(textView);
        }
        toast.setView(linearLayoutTop);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }


    /**
     * Toast 多行文本 单例
     *
     * @param size     字体大小
     * @param contents list 形式的文本内容
     */
    public static void showSingletonLines(List<String> contents, int size) {
        if (toast == null) {
            toast = Toast.makeText(YxbApplication.getContext(), "", Toast.LENGTH_LONG);
        }
        toastList.add(toast);
        LinearLayout linearLayoutTop = new LinearLayout(YxbApplication.getContext());//创建线性布局
        linearLayoutTop.setOrientation(LinearLayout.VERTICAL);//设置布局垂直
        for (int i = 0; i < contents.size(); i++) {
            TextView textView = new TextView(YxbApplication.getContext());
            textView.setText(contents.get(i));
            textView.setTextSize(size);
            linearLayoutTop.addView(textView);
        }
        toast.setView(linearLayoutTop);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Toast 自定义布局 非单例
     *
     * @param view     布局
     * @param duration 时长
     * @param position 位置
     */
    public static void showLayout(View view, int duration, int position) {
        toast = Toast.makeText(YxbApplication.getContext(), "", Toast.LENGTH_LONG);
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.setView(view);
        toast.show();

    }

    /**
     * Toast 自定义布局 单例
     *
     * @param view     布局
     * @param duration 时长
     * @param position 位置
     */
    public static void showSingletonLayout(View view, int duration, int position) {
        if (toast == null) {
            toast = Toast.makeText(YxbApplication.getContext(), "", Toast.LENGTH_LONG);
        }
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.setView(view);
        toast.show();

    }

    /**
     * 重置Toast对象
     */
    public static void resetToast() {
        toast = Toast.makeText(YxbApplication.getContext(), "", Toast.LENGTH_LONG);
    }

    /**
     * log 日志
     */
    public static void i(String s, Object o) {
        if(Constant.LOGGER_IS_SHOWLOG){
            Log.i("cwp",s+" "+o);
//            showLog(s);
        }
    }

    public static void showLogI(String tag, String msg) {
        if(Constant.LOGGER_IS_SHOWLOG){
            if (tag == null || tag.length() == 0
                    || msg == null || msg.length() == 0)
                return;
            int segmentSize = 3 * 1024;
            long length = msg.length();
            if (length <= segmentSize ) {// 长度小于等于限制直接打印
                Log.i(tag, msg);
            }else {
                while (msg.length() > segmentSize ) {// 循环分段打印日志
                    String logContent = msg.substring(0, segmentSize );
                    msg = msg.replace(logContent, "");
                    Log.i(tag, logContent);
                }
                Log.i(tag, msg);// 打印剩余日志
            }
        }
    }

        /**
         * log 日志
         */
    public static void w(String s, Object o) {
        if(Constant.LOGGER_IS_SHOWLOG){
            Log.w("cwp",s+" "+o);
        }
    }

    /**
     * log 日志
     */
    public static void e(String s, Object o) {
        if(Constant.LOGGER_IS_SHOWLOG){
            Log.e("cwp",s+" "+o);
        }
    }
    /**
     * 取消最近创建的一个Toast
     */
    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 取消所有的Toast任务
     */
    public static void cancelAll() {
        for (int i = 0; i < toastList.size(); i++) {
            if (toastList.get(i) != null) {
                toastList.get(i).cancel();
            }
        }
    }

}
