package com.ecarxclub.app.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.UrlOkHttpUtils;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.downfile.DownFileCallback;
import com.ecarxclub.app.downfile.DownLoadManager;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.home.AdvertisementRes;
import com.ecarxclub.app.presenter.WelcomePresenter;
import com.ecarxclub.app.presenter.WelcomeView;
import com.ecarxclub.app.utils.CommonUtils;
import com.ecarxclub.app.utils.SharedPreferencesUtils;
import com.ecarxclub.app.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;

import java.io.File;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/5/21 9:57.
 */
public class WelcomeActivity extends BaseActivityP<WelcomePresenter> implements WelcomeView {
    @BindView(R.id.tv_wc_time)
    TextView tvTime;
    @BindView(R.id.img_welcome)
    ImageView imgAdvert;

    private CountDownTimer timer;
    private String mLoadImgUrl = "";//网络获取图片路径
    private String mSaveImgUrl = "";//保存图片路径
    private int mShowTime = 0;
    private AdvertisementRes mAdvertisementRes;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private boolean isLoadImg = false;//true 保存本地失败

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }
    @Override
    protected boolean initSwipeBack() {
        return false;
    }
    @Override
    public void initData() {
        ToastUtils.i("WelcomeActivity  ", "___");
        if (SharedPreferencesUtils.getParam(YxbApplication.getContext(),
                UrlOkHttpUtils.YXB_FIRST_OPEN, "").toString().length() == 0) {//第一次打开app
            presenter.onCensus(CommonUtils.getMacAddress());//渠道下载量统计 第一次调用
            SharedPreferencesUtils.setParam(WelcomeActivity.this, UrlOkHttpUtils.YXB_FIRST_OPEN, "first");
            startIntent(WelcomeActivity.this, SplashActivity.class);
        } else {
            AdvertisementRes adms;
            mSaveImgUrl = getWelcomeImgLoad();
            File destDir = new File(mSaveImgUrl);
            if (mSaveImgUrl.length() > 0 && destDir.exists()) {
                Glide.with(WelcomeActivity.this).load(mSaveImgUrl).into(imgAdvert);
                isLoadImg = false;
                adms = getWelcomeImgModel();
                if (adms != null && adms.data != null) {
                    mShowTime = adms.data.get(0).time;
                }
            } else {
                isLoadImg = true;
            }
            if (mShowTime==0){
                tvTime.setVisibility(View.GONE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    toMainAct();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }else {
                tvTime.setVisibility(View.VISIBLE);
                onTimer(mShowTime);
            }
        }
        getAdvertisement();
    }

    @Override
    public void initView() {
        //获取h5调用Android的参数等
//        Intent intent = getIntent();
//        String action = intent.getAction();
//        String type= null;
//        String id = null;
//        if (Intent.ACTION_VIEW.equals(action)) {
//            Uri uri = intent.getData();
//            if (uri != null) {
//                type = uri.getQueryParameter("type");
//                id = uri.getQueryParameter("id ");
//            }
//        }
    }

    private void onTimer(int time) {
        /** 倒计时60秒，一次1秒 */
        timer = new CountDownTimer((time == 0 ? 1 : time) * 1000 + 200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                tvTime.setText("跳过 " + millisUntilFinished / 1000);
            }
            @Override
            public void onFinish() {
                tvTime.setText("跳过 0");
                toMainAct();
            }
        }.start();
    }

    public void toMainAct() {
        startIntent(WelcomeActivity.this, MainActivity.class);
    }

    @Override
    public void initClick() {
        //跳过
        RxView.clicks(tvTime).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (timer!=null){
                    timer.cancel();
                }
                toMainAct();
//                SharedPreferencesUtils.setParam(WelcomeActivity.this, UrlOkHttpUtils.YXB_FIRST_OPEN,"");
            }
        });

        //点击广告
        RxView.clicks(imgAdvert).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (mAdvertisementRes!=null&&mAdvertisementRes.data != null && mAdvertisementRes.data.size() > 0
                        && mAdvertisementRes.data.get(0).interLink != null && mAdvertisementRes.data.get(0).jump == 2) {
                    presenter.onClickAdvert(CommonUtils.getMacAddress(), mAdvertisementRes.data.get(0).id);
                    Bundle bundle = new Bundle();
                    bundle.putString("web_type", "advert");
                    bundle.putString("welcome", "welcome");
                    bundle.putSerializable("advert", mAdvertisementRes);
                    startIntent(WelcomeActivity.this, WebViewActivity.class, bundle);
                    if (timer!=null){
                        timer.cancel();
                    }
                }
            }
        });
    }

    private void getAdvertisement() {
        presenter.onGetShowAdvert("1");
    }

    private void downloadFile(String url) {
        DownLoadManager.getInstance().downFile(url, new DownFileCallback() {
            @Override
            public void onSuccess(String path) {
                SharedPreferencesUtils.setParam(WelcomeActivity.this, UrlOkHttpUtils.YXB_WELCOME_IMGURL, path);
                SharedPreferencesUtils.saveObject(WelcomeActivity.this, UrlOkHttpUtils.YXB_WELCOME_IMG, mAdvertisementRes);
//                showtoast("下载成功，path=" + path);
            }
            @Override
            public void onFail(String msg) {
            }
            @Override
            public void onProgress(final long totalSize, final long downSize) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        int progress = (int) (downSize * 100 / totalSize);
//                    }
//                });
            }
        });

    }


    @Override
    public void onWelcome(BaseMsgRes baseMsgRes) {

    }
    //广告
    @Override
    public void onGetPopShowType(AdvertisementRes advertisementRes) {
        if (advertisementRes.success) {
            mAdvertisementRes = advertisementRes;
//            ToastUtils.i("onGetPopShowType  ", "___" + advertisementRes.msg);
            if (advertisementRes.data != null && advertisementRes.data.size() > 0) {
                mLoadImgUrl = advertisementRes.data.get(0).posterIcon;
//                ToastUtils.i("网络图片路径——————  ", "___" + mLoadImgUrl);
                if (mLoadImgUrl != null) {
                    if (isLoadImg) {//保存图片失败  去下载图片
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1);
                            } else {
                                downloadFile(mLoadImgUrl);
                            }
                        }else {
                            downloadFile(mLoadImgUrl);
                        }
                    } else {
                        //本地url和网络请求url不同时   下载并保存
//                        String loadUrl = getWelcomeImgLoad();
                        String getUrl = "";
                        if (mSaveImgUrl.contains("/")) {
                            mSaveImgUrl = mSaveImgUrl.substring(mSaveImgUrl.lastIndexOf("/") + 1);
                        }
                        if (mLoadImgUrl.contains("/")) {
                            getUrl = mLoadImgUrl.substring(mLoadImgUrl.lastIndexOf("/") + 1);
                        }
                        if (!mSaveImgUrl.equals(getUrl)) {
                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1);
                                } else {
                                    downloadFile(mLoadImgUrl);
                                }
                            }else {
                                downloadFile(mLoadImgUrl);
                            }
                        }
                    }
                } else {//设置保存的图片路径为""
                    SharedPreferencesUtils.setParam(WelcomeActivity.this, UrlOkHttpUtils.YXB_WELCOME_IMGURL, "");
                }
            } else {//设置保存的图片路径为""
                SharedPreferencesUtils.setParam(WelcomeActivity.this, UrlOkHttpUtils.YXB_WELCOME_IMGURL, "");
            }
        }
    }

    //广告点击
    @Override
    public void onClickAdvert(BaseMsgRes baseMsgRes) {

    }

    @Override
    public void onCensus(BaseMsgRes baseMsgRes) {

    }

    //获取开屏广告图片路径
    private String getWelcomeImgLoad() {
        return SharedPreferencesUtils.getParam(YxbApplication.getContext(), UrlOkHttpUtils.YXB_WELCOME_IMGURL, "").toString();
    }

    //获取开屏广告model
    private AdvertisementRes getWelcomeImgModel() {
        return (AdvertisementRes) SharedPreferencesUtils.readObject(YxbApplication.getContext(), UrlOkHttpUtils.YXB_WELCOME_IMG);
    }


    @Override
    protected WelcomePresenter createPresenter() {
        return new WelcomePresenter(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                ToastUtils.showTextShort("未授权");
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
