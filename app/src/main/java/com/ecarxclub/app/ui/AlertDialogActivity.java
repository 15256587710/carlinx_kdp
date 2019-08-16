package com.ecarxclub.app.ui;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.ui.mine.message.MyMessageActivity;
import com.jakewharton.rxbinding.view.RxView;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/6/20 11:38.
 */
public class AlertDialogActivity extends BaseActivity {
    @BindView(R.id.txt_title)
    TextView tvTitle;
    @BindView(R.id.txt_msg)
    TextView tvContent;
    @BindView(R.id.btn_neg)
    Button btnCancel;
    @BindView(R.id.btn_pos)
    Button btnSure;

    private String mTitle="11",mContent="22";
    @Override
    public int initContenttView() {
        return R.layout.dialog_alert;
    }

    @Override
    public void initView() {
        if (getIntent().getExtras()!=null){
            mTitle=getIntent().getExtras().getString("title");
            mContent=getIntent().getExtras().getString("content");
        }

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)

        //设置弹框大小，此处宽度为屏幕宽度减去160像素
        getWindow().setLayout((int)(width*0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
        btnCancel.setText("取消");
        btnSure.setText("确定");
        tvTitle.setText(mTitle);
        tvContent.setText(mContent);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initClick() {
        //我的消息
        RxView.clicks(btnSure).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startIntent(AlertDialogActivity.this, MyMessageActivity.class);//MyMessageActivity
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        RxView.clicks(btnCancel).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
    }
}
