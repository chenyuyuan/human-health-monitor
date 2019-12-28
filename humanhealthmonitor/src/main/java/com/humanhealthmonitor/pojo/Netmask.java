package com.humanhealthmonitor.pojo;

public class Netmask {
    private int id;
    private String netmask_name;
    private String ip;
    private int port;
    private String related_user_id;

    public int getId() { return id;}
    public void setId(int id) {this.id = id;}

    public String getNetmask_name() {
        return netmask_name;
    }

    public void setNetmask_name(String netmask_name) {
        this.netmask_name = netmask_name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRelated_user_id() {
        return related_user_id;
    }

    public void setRelated_user_id(String related_user_id) {
        this.related_user_id = related_user_id;
    }
}
