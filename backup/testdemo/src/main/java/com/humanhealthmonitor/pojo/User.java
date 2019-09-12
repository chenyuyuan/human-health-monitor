package com.humanhealthmonitor.pojo;

import java.sql.Date;//e.g.2000-01-01
//import java.sql.Time;//e.g.22:59:59
import java.sql.Timestamp;//e.g.2000-01-01 00:00:01

//注意，数据类型与数据库的对应容易产生错误
public class User {
    private String userId;//用户ID
    private String userName;//用户昵称
    private String pwd;//密码
    private String sex;//性别
    private Date registerDate;//注册日期
    private Date birthDate;//出生日期
    private String userTel;//手机号码
    private String userGroup;//用户群组
    //    private int msgCount;//短信条数
//    private int onlineTime;//在线时长
//    private long dataCount;//时间序列数据使用条数
    private Timestamp lastLoginTime;//最后登录时间
    private Timestamp lastLogoutTime;//最后离线时间
    private boolean loginState;//登录状态

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
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

    public boolean getLoginState() {
        return loginState;
    }

    public void setLoginState(boolean loginState) {
        this.loginState = loginState;
    }
}
