package com.ecarxclub.app.presenter.login;

import com.ecarxclub.app.base.BaseView;
import com.ecarxclub.app.model.login.LoginRes;

public interface LoginView extends BaseView {
    void onLoginResult(LoginRes loginRes);
}
