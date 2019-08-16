package com.ecarxclub.app.presenter.mine.userinfo;

import com.ecarxclub.app.base.BaseObserver;
import com.ecarxclub.app.base.BasePresenter;
import com.ecarxclub.app.model.BaseMsgRes;
import com.ecarxclub.app.model.UserInfoRes;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by cwp
 * on 2019/5/17 16:13.
 */
public class EditUserPresenter extends BasePresenter<EditUserView> {
    public EditUserPresenter(EditUserView baseView) {
        super(baseView);
    }

    //获取用户信息
    public void onGetUserInfo(){
        addDisposable(apiServer.getUserInfo(), new BaseObserver<UserInfoRes>(baseView) {
            @Override
            public void onSuccess(UserInfoRes o) {
                baseView.onUserInfo(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }


    //设置头像
    public void uploadFile(RequestBody body){
        addDisposable(apiServer.uploadFile(body), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onGetResult(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    //设置头像
    public void uploadFiles(String path){
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        addDisposable(apiServer.uploadFiles(filePart), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onGetResult(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }


    //修改用户信息
    public void changeUserInfo(Map<String,String> map){
        addDisposable(apiServer.editUserInfo(map), new BaseObserver<BaseMsgRes>(baseView) {
            @Override
            public void onSuccess(BaseMsgRes o) {
                baseView.onEditUser(o);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

}
