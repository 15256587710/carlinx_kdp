package com.ecarxclub.app.utils.dialog.home;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.YxbContent;
import com.bumptech.glide.Glide;


/**
 * 作者：96213 on 2018/11/12 15:02
 * 首页广告弹窗
 */
public class HomeAdvertisementDialog {
    private Display display;
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private ImageView imgDelete,imgAdv;

    private onButtonClickListener onButtonClickListener;

    public HomeAdvertisementDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }


    public HomeAdvertisementDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.popup_home_advertisment, null);
        // 获取自定义Dialog布局中的控件
        lLayout_bg = view.findViewById(R.id.ll_pop_all);
        imgDelete=view.findViewById(R.id.pop_home_delete);
        imgAdv=view.findViewById(R.id.pop_img_home_adv);
        imgAdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onClickListener();
            }
        });

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    public void setData(String imgurl){
        Glide.with(context).load(imgurl).apply(YxbContent.options).into(imgAdv);
    }

    public interface onButtonClickListener {
        void onClickListener();
    }

    public void setOnBtnClickListener(onButtonClickListener listener) {
        this.onButtonClickListener = listener;
    }

    public HomeAdvertisementDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public void dissmiss() {
        dialog.dismiss();
    }

    public void show() {
//        setLayout();
        dialog.show();
    }


}
