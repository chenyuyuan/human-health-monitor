package com.humanhealthmonitor.pojo;

public class Admin {

    private String adminId;//管理员ID
    private String pwd;//密码
    private String adminGroup;//管理员群组，root管理员只有一个
    private boolean loginState;//管理员登录状态

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getAdminGroup() {
        return adminGroup;
    }

    public void setAdminGroup(String adminGroup) {
        this.adminGroup = adminGroup;
    }

    public boolean getLoginState() {
        return loginState;
    }

    public void setLoginState(boolean loginState) {
        this.loginState = loginState;
    }
}
