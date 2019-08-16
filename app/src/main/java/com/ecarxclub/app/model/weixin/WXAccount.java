package com.ecarxclub.app.model.weixin;

import java.io.Serializable;

/**
 * Created by ibestry on 2017/8/22.
 */

public class WXAccount implements Serializable {

    public String loginCode, openId, accessToken, refreshToken, nickName, porTrait, sex;

    @Override
    public String toString() {
        return "WXAccount{" +
                "loginCode='" + loginCode + '\'' +
                ", openId='" + openId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", nickName='" + nickName + '\'' +
                ", porTrait='" + porTrait + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPorTrait() {
        return porTrait;
    }

    public void setPorTrait(String porTrait) {
        this.porTrait = porTrait;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
