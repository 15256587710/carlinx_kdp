package com.ecarxclub.app.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.model.home.GetVersionRes;
import com.ecarxclub.app.model.shop.integral.SignInRewordRes;
import com.ecarxclub.app.utils.ToastUtils;


/**
 * 作者：96213 on 2018/11/12 15:02
 * 首页更新
 */
public class SignInGetDialog {
    private Display display;
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView tvIntegral,tvInfo,tvContent;
    private Button btnKnow;


    private onButtonClickListener onButtonClickListener;

    public SignInGetDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }


    public SignInGetDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.popup_signin, null);
        // 获取自定义Dialog布局中的控件
        lLayout_bg = view.findViewById(R.id.ll_pop_all);
        tvIntegral = view.findViewById(R.id.pop_tv_signin_integral);
        tvContent = view.findViewById(R.id.pop_tv_signin_content);
        tvInfo = view.findViewById(R.id.pop_tv_signin_info);
        btnKnow = view.findViewById(R.id.pop_btn_signin_know);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        btnKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dissmiss();
            }
        });
        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    public void setData(SignInRewordRes.DataBean data) {
        if (data.awardCover!=null){
            tvIntegral.setVisibility(View.VISIBLE);
            tvIntegral.setText(data.awardCover);
        }else {
            tvIntegral.setVisibility(View.GONE);
        }
        if (data.reserveCover!=null){
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(data.reserveCover);
        }else {
            tvContent.setVisibility(View.GONE);
        }
        if (data.tip!=null){
            tvInfo.setText(data.tip);
        }

    }

    public interface onButtonClickListener {
        void onClickListener(String grade);
    }

    public void setOnBtnClickListener(onButtonClickListener listener) {
        this.onButtonClickListener = listener;
        dialog.dismiss();
    }

    public SignInGetDialog setCancelable(boolean cancel) {
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
