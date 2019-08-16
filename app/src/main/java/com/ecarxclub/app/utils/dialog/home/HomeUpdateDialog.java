package com.ecarxclub.app.utils.dialog.home;

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
import com.ecarxclub.app.utils.ToastUtils;


/**
 * 作者：96213 on 2018/11/12 15:02
 * 首页更新
 */
public class HomeUpdateDialog {
    private Display display;
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private ImageView imgDelete;
    private ProgressBar progressBar;
    private RelativeLayout rl;
    private TextView tvLoading, tvCode, tvContent,tvCancel;
    private Button btnUpdate;
    private boolean isClick=true;

    private onButtonClickListener onButtonClickListener;

    public HomeUpdateDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }


    public HomeUpdateDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.popup_update, null);
        // 获取自定义Dialog布局中的控件
        lLayout_bg = view.findViewById(R.id.ll_pop_all);
        imgDelete = view.findViewById(R.id.pop_update_delete);
        rl = view.findViewById(R.id.pop_rl_progress);
        progressBar = view.findViewById(R.id.pop_pb);
        tvCode = view.findViewById(R.id.pop_tv_code);
        tvLoading = view.findViewById(R.id.pop_tv_loading);
        tvContent = view.findViewById(R.id.pop_tv_content);
        btnUpdate = view.findViewById(R.id.pop_btn_update);
        tvCancel=view.findViewById(R.id.pop_tv_cancel);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onClickListener("3");
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onClickListener("2");
            }
        });
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

    public void setData(GetVersionRes.DataBean data) {
        ToastUtils.i("","date "+data.versionPort);
        rl.setVisibility(View.GONE);
        if (data != null) {
            tvCode.setText(data.versionPort);
            tvContent.setText(data.content);
            if ("3".equals(data.grade)){
                imgDelete.setVisibility(View.GONE);
                tvCancel.setVisibility(View.GONE);
            }else if("2".equals(data.grade)){
                imgDelete.setVisibility(View.VISIBLE);
                tvCancel.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setProgress(int progress) {
        rl.setVisibility(View.VISIBLE);
        tvLoading.setText(progress + "%");
        progressBar.setProgress(progress);
        if (isClick){
            btnUpdate.setEnabled(false);
            tvCancel.setEnabled(false);
            imgDelete.setEnabled(false);
            isClick=false;
        }
    }


    public interface onButtonClickListener {
        void onClickListener(String grade);
    }

    public void setOnBtnClickListener(onButtonClickListener listener) {
        this.onButtonClickListener = listener;
        dialog.dismiss();
    }

    public HomeUpdateDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public void dissmiss() {
        isClick=true;
        dialog.dismiss();
    }

    public void show() {
//        setLayout();
        dialog.show();
    }


}
