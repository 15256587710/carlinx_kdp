package com.ecarxclub.app.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ecarxclub.app.base.BaseInterface;
import com.ecarxclub.app.common.ActivityManager;
import com.ecarxclub.app.common.AppManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by cwp
 * on 2019/4/17 14:28.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseInterface{
    protected Context context = this;
    protected Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus(this);
        setContentView(initContenttView());
        orientation();
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
        initClick();
        AppManager.getAppManager().addActivity(this);
//        ActivityManager.addActivity(this, getClass());
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

    protected View inflate(@LayoutRes int id) {
        return inflate(id, null);
    }

    protected View inflate(@LayoutRes int id, @Nullable ViewGroup root) {
        return LayoutInflater.from(context).inflate(id, root);
    }

    /*******************************************布局绑定********************************************/

    protected <T extends View> T findView(int resId) {
        return (T) findViewById(resId);
    }

    protected void setOnClickListener(@IdRes int id, View.OnClickListener onClicklistener) {
        findViewById(id).setOnClickListener(onClicklistener);
    }

    protected <T extends View> T findView(View v, int resId) {
        return (T) v.findViewById(resId);
    }

    protected void setOnClickListener(View v, @IdRes int id, View.OnClickListener onClicklistener) {
        v.findViewById(id).setOnClickListener(onClicklistener);
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
    @LayoutRes
    public abstract int initContenttView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        AppManager.getAppManager().finishActivity(this);
//        ActivityManager.removeActivity(this);
    }

    /** 处理edittext点击隐藏
     * (non-Javadoc)
     * @see Activity#dispatchTouchEvent(MotionEvent)*/

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


}
