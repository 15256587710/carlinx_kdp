package com.ecarxclub.app.ui.home.kdp;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.presenter.DefaultPresenter;
import com.ecarxclub.app.presenter.DefaultView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.ui.home.ScanCodeActivity;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/8/14 14:51.
 * 绑定车辆   点击扫码
 */
public class BindCarScanActivity extends BaseActivityP<DefaultPresenter> implements DefaultView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.btn_bs_scan)
    Button btnScan;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bindcar_scan;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarTitle.setText("绑定车辆");
    }

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
        RxView.clicks(btnScan).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                startIntent(BindCarScanActivity.this, ScanCodeActivity.class);
            }
        });
    }

    @Override
    protected DefaultPresenter createPresenter() {
        return new DefaultPresenter(this);
    }
}
