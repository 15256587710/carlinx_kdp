package com.ecarxclub.app.utils.popup.base;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by 大灯泡 on 2016/1/14.
 * 显示键盘d工具类
 */

public class InputMethodUtils {

    /** 显示软键盘 */
    public static void showInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                                                          .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /** 显示软键盘 */
    public static void showInputMethod(Context context) {
        InputMethodManager imm = (InputMethodManager) context
            .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /** 多少时间后显示软键盘 */
    public static void showInputMethod(final View view, long delayMillis) {
        // 显示输入法
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                InputMethodUtils.showInputMethod(view);
            }
        }, delayMillis);
    }
}
