package com.ecarxclub.app.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ecarxclub.app.R;

/**
 * Created by yshu_LW.
 * on 2018/07/26
 */

public class BottomAlertDialog extends Dialog {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable;//控制返回键是否dismiss
    private View view;
    private Context context;

    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public BottomAlertDialog(Context context, View view, boolean isCancelable, boolean isBackCancelable) {
        super(context, R.style.BottomAlertDialog);

        this.context = context;
        this.view = view;
        this.iscancelable = isCancelable;
        this.isBackCancelable = isBackCancelable;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(view);//这行一定要写在前面
        setCancelable(iscancelable);//点击外部不可dismiss
        setCanceledOnTouchOutside(isBackCancelable);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

}
