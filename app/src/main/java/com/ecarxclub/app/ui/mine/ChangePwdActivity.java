package com.ecarxclub.app.ui.mine;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.presenter.mine.userinfo.ChangePwdPresenter;
import com.ecarxclub.app.presenter.mine.userinfo.ChangePwdView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.ecarxclub.app.utils.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/5/20 13:35.
 */
public class ChangePwdActivity extends BaseActivityP<ChangePwdPresenter> implements ChangePwdView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgToolbarLeft;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.et_change_oldpwd)
    EditText etOldPwd;
    @BindView(R.id.et_change_newpwd)
    EditText etNewPwd;
    @BindView(R.id.et_change_newpwd2)
    EditText etNewPwd2;
    @BindView(R.id.btn_change_pwd)
    Button btnSure;

    String mOldPwd,mNewPwd,mNewPwd2;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarTitle.setText("设置密码");
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
               mOldPwd=etOldPwd.getText().toString();
               mNewPwd=etNewPwd.getText().toString();
               mNewPwd2=etNewPwd2.getText().toString();
                if(mOldPwd!=null&&mOldPwd.length()>5){
                    if(mNewPwd!=null&&mNewPwd.length()>5){
                        if(mNewPwd2.equals(mNewPwd)){
                            presenter.changeUserInfo(mOldPwd,mNewPwd);
                        }else {
                            ToastUtils.showTextShort("两次输入密码不一致");
                        }
                    }else {
                        ToastUtils.showTextShort("请输入正确密码格式");
                    }
                }else {
                    ToastUtils.showTextShort("请输入正确密码格式");
                }
            }
        });
    }
    @Override
    public void onChangePwd(BaseMsgRes baseMsgRes) {
        if(baseMsgRes.isSuccess()){
            ToastUtils.showTextShort("修改成功");
            finish();
        }
    }

    @Override
    protected ChangePwdPresenter createPresenter() {
        return new ChangePwdPresenter(this);
    }


}
