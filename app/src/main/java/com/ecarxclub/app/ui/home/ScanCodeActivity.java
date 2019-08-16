package com.ecarxclub.app.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.presenter.home.scan.ScanCodePresenter;
import com.ecarxclub.app.presenter.home.scan.ScanCodeView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.ui.mine.EditUserInfoActivity;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/7/24 11:30.
 */
public class ScanCodeActivity extends BaseActivityP<ScanCodePresenter> implements ScanCodeView, QRCodeView.Delegate {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    //    @BindView(R.id.zbar_scan)
    QRCodeView mQRCodeView;

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarDarkMode(this);
        tvToolbarTitle.setText("扫码登录");
    }

    @Override
    public void initData() {
        mQRCodeView = (QRCodeView) findViewById(R.id.zbar_scan);
        mQRCodeView.setDelegate(this);
    }

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_code;
    }

    @Override
    protected ScanCodePresenter createPresenter() {
        return new ScanCodePresenter(this);
    }


    private void vibrateHint() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    public void onGetCameraPer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //权限不够
            if (ContextCompat.checkSelfPermission(ScanCodeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ScanCodeActivity.this, new String[]{Manifest.permission.CAMERA}, 3);//打开相机
            } else {
                startCameras();
            }
        } else {
            //调用相机
            startCameras();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 3:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权
                    onGetCameraPer();
                } else {
                    // 未授权
                    ToastUtils.showTextShort("未授权");
                }
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        onGetCameraPer();
    }

    private void startCameras() {
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
        mQRCodeView.startSpotDelay(200);
//        mQRCodeView.startSpot();//开始识别二维码
        //mQRCodeView.openFlashlight();//开灯
        //mQRCodeView.closeFlashlight();//关灯
    }

    @Override
    protected void onResume() {
        super.onResume();
        mQRCodeView.startSpotDelay(1000);
    }

    @Override
    public void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrateHint();
        ToastUtils.i("onScanQRCodeSuccess", "_______" + result);
        showtoast("请扫码正确的二维码");
//        if (result.contains("carlinx")){
//            Bundle bundle=new Bundle();
//            bundle.putString("scancode",result.substring(7,result.length()));
//            startIntent(ScanCodeActivity.this,ConfirmCarLoginActivity.class,bundle);
//        }else {
//            showtoast("请扫码正确的二维码");
//        }
        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        ToastUtils.i("相机出错llllllll", "");
        showtoast("相机出错");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ToastUtils.i("requestCode__" + requestCode, " ++ " + resultCode);
    }

    @Override
    public void onCarLogin(BaseMsgRes baseMsgRes) {

    }
}
