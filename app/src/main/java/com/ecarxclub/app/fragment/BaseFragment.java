package com.ecarxclub.app.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ecarxclub.app.base.BaseModel;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.base.BindEventBus;
import com.ecarxclub.app.common.YxbApplication;
import com.android.tu.loadingdialog.LoadingDailog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    public Context context;
    private ProgressDialog dialog;
    private LoadingDailog loadingDailog;
    protected P presenter;
    protected Unbinder unbinder;
    protected View view;
    protected abstract P createPresenter();

    protected abstract int getLayoutId();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        presenter = createPresenter();
        context = getActivity();
        unbinder = ButterKnife.bind(this,view);
        initView();
        initData();
        initClick();
        if (this.getClass().isAnnotationPresent(BindEventBus.class) && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return view;
    }


    public void initData() {
    }

    public void initView() {
    }

    public void initClick() {
    }
    @Override
    public void onDestroy() {
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
        YxbApplication.getRefWatcher(getActivity()).watch(this);
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
     *
     *
     */
    public void showtoast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
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
//
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
//        showLoadingDialog();
        showLoadingDialog(context, "");
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

}
