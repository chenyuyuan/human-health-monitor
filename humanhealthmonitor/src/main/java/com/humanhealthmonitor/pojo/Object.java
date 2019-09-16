package com.humanhealthmonitor.pojo;

import java.sql.Date;//e.g.2000-01-01
import java.sql.Timestamp;//e.g.2000-01-01 00:00:01

public class Object {
    private String objectId;//监测对象ID
    private String userId;//用户ID
    private String objectName;//监测对象昵称
    private String pwd;//密码
    private String sex;//性别
    private Date birthDate;//出生日期
    private Date registerDate;//注册日期
    private String objectTel;//手机号码
    private Timestamp lastLoginTime;//最后登录时间
    private Timestamp lastLogoutTime;//最后登出时间
    private boolean loginState;//登录状态

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getObjectTel() {
        return objectTel;
    }

    public void setObjectTel(String objectTel) {
        this.objectTel = objectTel;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Timestamp getLastLogoutTime() {
        return lastLogoutTime;
    }

    public void setLastLogoutTime(Timestamp lastLogoutTime) {
        this.lastLogoutTime = lastLogoutTime;
    }

    public boolean isLoginState() {
        return loginState;
    }

    public void setLoginState(boolean loginState) {
        this.loginState = loginState;
    }
}
