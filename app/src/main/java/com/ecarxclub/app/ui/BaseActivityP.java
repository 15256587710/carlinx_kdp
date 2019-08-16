package com.ecarxclub.app.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ecarxclub.app.base.BaseModel;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.base.BindEventBus;
import com.ecarxclub.app.common.AppManager;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.utils.IsNetWork;
import com.ecarxclub.app.utils.ToastUtils;
import com.android.tu.loadingdialog.LoadingDailog;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public abstract class BaseActivityP<P extends BasePresenter> extends SwipeBackActivity implements BaseView {//SwipeBackActivity
    public Context context;
    private ProgressDialog dialog;
    private LoadingDailog loadingDailog;
    protected P presenter;
    protected Unbinder unbinder;

    protected abstract P createPresenter();

    protected abstract int getLayoutId();

    protected SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setTranslucentStatus(this);
        setContentView(getLayoutId());
        orientation();
        presenter = createPresenter();
        unbinder = ButterKnife.bind(this);
        if (this.getClass().isAnnotationPresent(BindEventBus.class) && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        setSwipeBackView();
        initView();
        initView(savedInstanceState);
        initData();
        initClick();
        AppManager.getAppManager().addActivity(this);
    }

    public void setSwipeBackView() {
        // 可以调用该方法，设置是否允许滑动退出
        setSwipeBackEnable(initSwipeBack());
        mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
        mSwipeBackLayout.setEdgeSize(200);
    }

    //是否允许侧滑返回
    protected boolean initSwipeBack() {
        return true;
    }

    public void initData() {
    }

    public void initView() {
    }

    public void initView(Bundle savedInstanceState) {
    }

    public void initClick() {
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == ev.ACTION_DOWN && !IsNetWork.isNetworkAvalible(context)) {
            ToastUtils.showTextShort("网络未连接");
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 设置状态栏背景状态
     */
    public static void setTranslucentStatus(Activity activity) {
        Window window = activity.getWindow();
        // 设置状态栏全透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        return;
    }

    /**
     * 手机屏幕设置为竖屏
     */
    private void orientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();//接触绑定
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (this.getClass().isAnnotationPresent(BindEventBus.class) && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        AppManager.getAppManager().finishActivity(this);
        YxbApplication.getRefWatcher(this).watch(this);
    }

    protected void startIntent(Context context, Class<?> paramClass) {
        startActivity(new Intent(context, paramClass));
    }

    protected void startIntent(Context context, Class<?> paramClass, Bundle paramBundle) {
        Intent localIntent = new Intent();
        localIntent.putExtras(paramBundle);
        localIntent.setClass(context, paramClass);
        startActivity(localIntent);
    }

    /**
     * @param s
     */
    public void showtoast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public void showFileDialog() {
        dialog = new ProgressDialog(context);
        dialog.setMessage("正在下载中,请稍后");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMax(100);
        dialog.show();
    }

    public void hideFileDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


//    private void closeLoadingDialog() {
//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//        }
//    }
//
//
//    private void showLoadingDialog() {
//        if (dialog == null) {
//            dialog = new ProgressDialog(context);
//        }
//        dialog.setCancelable(false);
//        dialog.show();
//    }


    public void showLoadingDialog(Context mContext, String text) {
        if (loadingDailog == null) {
            loadingDailog = new LoadingDailog.Builder(mContext)
                    .setMessage(text.equals("") ? "加载中..." : text)
                    .setCancelable(true)
                    .setCancelOutside(false)
                    .create();
        }
        loadingDailog.show();
    }

    public void dismissLoadingDialog() {
        if (loadingDailog != null && loadingDailog.isShowing()) {
            loadingDailog.dismiss();
        }
    }


    @Override
    public void showLoading() {
        showLoadingDialog(context, "");
//        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
//        closeLoadingDialog();
        dismissLoadingDialog();
    }

    @Override
    public void showLoadingFileDialog() {
        showFileDialog();
    }

    @Override
    public void hideLoadingFileDialog() {
        hideFileDialog();
    }

    @Override
    public void onProgress(long totalSize, long downSize) {
        if (dialog != null) {
            dialog.setProgress((int) (downSize * 100 / totalSize));
        }
    }

    @Override
    public void showError(String msg) {
        showtoast(msg);
    }

    @Override
    public void onErrorCode(BaseModel model) {

    }


    public String createDir() {
        String url = "";
        if (existSDCard()) {
            url = Environment.getExternalStorageDirectory().getPath() + UrlOkHttpUtils.YXB_SAVE_IMG;
            File destDir = new File(url);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
        } else {
            ToastUtils.showTextShort("sd卡不存在");
        }
        return url;
    }

    //SD是否存在
    private boolean existSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

}
