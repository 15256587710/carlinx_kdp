package com.ecarxclub.app.ui.forgetpwd;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.presenter.RetrievePwd.ResetPwdPresenter;
import com.ecarxclub.app.presenter.RetrievePwd.ResetPwdVIew;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.ui.login.LoginActivity;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/4/18 16:23.
 * 重置密码
 */
public class ReSetPwdActivity extends BaseActivityP<ResetPwdPresenter> implements ResetPwdVIew {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;

    @BindView(R.id.et_reset_pwd)
    EditText etPwd;
    @BindView(R.id.et_reset_newpwd)
    EditText etNewPwd;
    @BindView(R.id.btn_reset_sure)
    Button btnSure;
    @BindView(R.id.tv_reset_phone)
    TextView tvPhone;

    private String mStrPwd,mStrNewPwd;
    private String mToken,mPhone;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_pwd;
    }
    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
//        tvToolbarLeft.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.nav_icon_back), null, null, null);
        tvToolbarTitle.setText("重置密码");
    }

    @Override
    public void initData() {
        mToken=getIntent().getExtras().getString(Constant.TOKEN);
        mPhone=getIntent().getExtras().getString(Constant.PHONENUMBER);
        if(mPhone!=null&&mPhone.length()>0){
            tvPhone.setText(mPhone);
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
        //确定
        RxView.clicks(btnSure).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mStrPwd=etPwd.getText().toString();
                mStrNewPwd=etNewPwd.getText().toString();
                if(mStrPwd.length()>5&&mStrPwd.length()<16&&mStrNewPwd.length()>5&&mStrNewPwd.length()<16){
                    if(mStrPwd.equals(mStrNewPwd)){
                        presenter.resetPwd(mStrPwd,mToken);
                    }else {
                        showtoast("密码输入不一致");
                    }
                }else {
                    showtoast("请输入正确的密码");
                }
            }
        });
    }



    @Override
    protected ResetPwdPresenter createPresenter() {
        return new ResetPwdPresenter(this);
    }

    //重置密码成功
    @Override
    public void onResetPwdResult(BaseMsgRes baseMsgRes) {
        ToastUtils.e("",""+baseMsgRes.getMsg());
        if(baseMsgRes.isSuccess()){
            showtoast("重置成功");
            startIntent(ReSetPwdActivity.this, LoginActivity.class);
        }
    }
}
