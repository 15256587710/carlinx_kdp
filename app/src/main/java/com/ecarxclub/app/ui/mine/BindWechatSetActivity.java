package com.ecarxclub.app.ui.mine;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.base.BindEventBus;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.common.YxbApplication;
import com.ecarxclub.app.common.YxbContent;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.event.EventMessage;
import com.ecarxclub.app.presenter.mine.BindWechatPresenter;
import com.ecarxclub.app.presenter.mine.BindWechatView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

import static com.ecarxclub.app.common.YxbApplication.WX_API;

/**
 * Created by cwp
 * on 2019/7/19 9:31.
 * 账号与绑定设置
 */
@BindEventBus
public class BindWechatSetActivity extends BaseActivityP<BindWechatPresenter> implements BindWechatView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_bindwechat_phone)
    TextView tvPhone;
    @BindView(R.id.btn_bindwechat_call)
    Button btnCallPhone;
    @BindView(R.id.btn_bindwechat_bind)
    Button btnBindWechat;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_wechatset;
    }

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarTitle.setText("账号与绑定设置");
    }

    @Override
    public void initData() {
        if (YxbApplication.userInfoRes!=null&&YxbApplication.userInfoRes.data!=null){
            if (YxbApplication.userInfoRes.data.weiUnionId!=null){
                btnBindWechat.setText("已绑定");
                btnBindWechat.setBackgroundResource(R.drawable.lay_bggry_radius4);
                btnBindWechat.setTextColor(getResources().getColor(R.color.tab_n));
                btnBindWechat.setEnabled(false);
            }else {
                btnBindWechat.setText("绑定");
                btnBindWechat.setBackgroundResource(R.drawable.lay_bgyellow_radius4);
                btnBindWechat.setTextColor(getResources().getColor(R.color.white));
                btnBindWechat.setEnabled(true);
            }
            if (YxbApplication.userInfoRes.data.mobile!=null){
                tvPhone.setText(YxbApplication.userInfoRes.data.mobile);
            }
        }
    }

    @Override
    public void initClick() {
        RxView.clicks(imgToolbarLeft).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });

        //联系客服
        RxView.clicks(btnCallPhone).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                YxbContent.getCallPhone(BindWechatSetActivity.this);
            }
        });

        //联系客服
        RxView.clicks(btnBindWechat).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                // 是否安装微信
                if (WX_API.isWXAppInstalled()) {// 已安装
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wchat_login";//signIn
                    WX_API.sendReq(req);
                } else { // 未安装
                    ToastUtils.showTextShort("无法使用该功能,请安装微信后重试");
                }
            }
        });

    }

    @Override
    public void onBindWechat(BaseMsgRes baseMsgRes) {
        if (baseMsgRes.isSuccess()) {
            showtoast("绑定成功");
            btnBindWechat.setText("已绑定");
            btnBindWechat.setBackgroundResource(R.drawable.lay_bggry_radius4);
            btnBindWechat.setTextColor(getResources().getColor(R.color.tab_n));
            btnBindWechat.setEnabled(false);
            EventBus.getDefault().post(new EventMessage(Constant.USER_BIND_WECHAT,""));
        }else {
            showtoast(baseMsgRes.getMsg());
        }
    }
        @Override
    protected BindWechatPresenter createPresenter() {
        return new BindWechatPresenter(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusLogin(EventMessage eventMessage) {
        ToastUtils.e("BindWechatSetActivity " + eventMessage.action, "  obj" + eventMessage.object);
        switch (eventMessage.action) {
            case Constant.USER_WECHAT:
                String mCode= (String) eventMessage.object;
                if (mCode.length()>0){
                    presenter.bindWechat(mCode);
                }
                break;
            case Constant.USER_FINISH_LOGINACT://登录成功  结束当前activity
                finish();
                break;
        }
    }


}
