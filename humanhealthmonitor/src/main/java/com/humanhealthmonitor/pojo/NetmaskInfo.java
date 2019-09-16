package com.humanhealthmonitor.pojo;

public class NetmaskInfo {

    private int netmaskId;//网关ID
    private String netmaskName;//网关名
    private String ipAddress;//IP地址
    private String comProtocol;//通信协议
    private String state;//网关状态，如连通或断开

    public int getNetmaskId() {
        return netmaskId;
    }

    public void setNetmaskId(int netmaskId) {
        this.netmaskId = netmaskId;
    }

    public String getNetmaskName() {
        return netmaskName;
    }

    public void setNetmaskName(String netmaskName) {
        this.netmaskName = netmaskName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getComProtocol() {
        return comProtocol;
    }

    public void setComProtocol(String comProtocol) {
        this.comProtocol = comProtocol;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
