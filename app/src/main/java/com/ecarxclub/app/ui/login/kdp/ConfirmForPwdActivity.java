package com.ecarxclub.app.ui.login.kdp;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecarxclub.app.R;
import com.ecarxclub.app.common.Constant;
import com.ecarxclub.app.presenter.kdp.forgetpwd.ForgetPwdPresenter;
import com.ecarxclub.app.presenter.kdp.forgetpwd.ForgetPwdView;
import com.ecarxclub.app.ui.BaseActivityP;
import com.ecarxclub.app.utils.QMUIStatusUtil.QMUIStatusBarUtil;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by cwp
 * on 2019/8/14 10:12.
 */
public class ConfirmForPwdActivity extends BaseActivityP<ForgetPwdPresenter> implements ForgetPwdView {
    @BindView(R.id.tv_toolbar_left)
    ImageView imgBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.et_cfp_pwd)
    EditText etPwd;
    @BindView(R.id.et_cfp_pwd2)
    EditText etPwd2;
    @BindView(R.id.img_cfp_lookpwd)
    ImageView imgLookPwd;
    @BindView(R.id.img_cfp_lookpwd2)
    ImageView imgLookPwd2;
    @BindView(R.id.btn_cfp_confirm)
    Button btnConfirm;

    private String mStrPwd, mStrPwd2;
    private boolean isPwdExist, isPwdExist2,isPwdShow,isPwdShow2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_forgetpwd;
    }

    @Override
    public void initView() {
        QMUIStatusBarUtil.setStatusBarLightMode(this);
        tvToolbarTitle.setText("忘记密码");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initClick() {
        //返回
        RxView.clicks(imgBack).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });

        //密码查看
        RxView.clicks(imgLookPwd).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (isPwdShow){//显示
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imgLookPwd.setBackgroundResource(R.mipmap.pic_login_lookpwd_black);
                    isPwdShow=false;
                }else {
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imgLookPwd.setBackgroundResource(R.mipmap.pic_login_pwd_look);
                    isPwdShow=true;
                }
                etPwd.setSelection(etPwd.getText().toString().length());
            }
        });
        //密码查看
        RxView.clicks(imgLookPwd2).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (isPwdShow2){//显示
                    etPwd2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imgLookPwd2.setBackgroundResource(R.mipmap.pic_login_lookpwd_black);
                    isPwdShow2=false;
                }else {
                    etPwd2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imgLookPwd2.setBackgroundResource(R.mipmap.pic_login_pwd_look);
                    isPwdShow2=true;
                }
                etPwd2.setSelection(etPwd2.getText().toString().length());
            }
        });
        //确认修改
        RxView.clicks(btnConfirm).throttleFirst(Constant.DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mStrPwd = etPwd.getText().toString();
                mStrPwd2 = etPwd2.getText().toString();
                if (mStrPwd.length() > 7 && mStrPwd.length() <= 12) {
                    if (mStrPwd.equals(mStrPwd2)) {

                    } else {
                        showtoast("密码输入不一致");
                    }
                } else {
                    showtoast("请输入正确的密码格式");
                }
            }
        });
        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    isPwdExist = false;
                } else {
                    isPwdExist = true;
                }
                isBtnShow();
            }
        });
        etPwd2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    isPwdExist2 = false;
                } else {
                    isPwdExist2 = true;
                }
                isBtnShow();
            }
        });
    }

    private void isBtnShow(){
        if (isPwdExist&&isPwdExist2){
            btnConfirm.setBackgroundResource(R.mipmap.pic_login_btn_t);
        }else {
            btnConfirm.setBackgroundResource(R.mipmap.pic_login_btn);
        }
    }

    @Override
    protected ForgetPwdPresenter createPresenter() {
        return new ForgetPwdPresenter(this);
    }


}
