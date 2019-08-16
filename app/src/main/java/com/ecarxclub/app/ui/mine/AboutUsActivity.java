package com.ecarxclub.app.ui.mine;

import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.presenter.mine.AboutUsPresenter;
import com.ecarxclub.app.presenter.mine.AboutUsView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/5/5 15:20.
 * 关于我们
 */
public class AboutUsActivity extends BaseActivityP<AboutUsPresenter> implements AboutUsView{
    @BindView(R.id.tv_toolbar_left)
    ImageView tvToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarTitle.setText("关于我们");
//        tvToolbarLeft.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.nav_icon_back), null, null, null);
    }

    @Override
    public void initClick() {
        RxView.clicks(tvToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_about;
    }
    @Override
    public void onAboutUs(BaseMsgRes baseMsgRes) {

    }

    @Override
    protected AboutUsPresenter createPresenter() {
        return new AboutUsPresenter(this);
    }


}
