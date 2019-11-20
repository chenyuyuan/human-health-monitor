package com.humanhealthmonitor.pojo;

public class health_data {
    private int id;
    private int eqp_id;
    private int netmask_id;
    private int object_id;
    private String time;
    private String sendtime;
    private int e_temp;
    private int b_temp;
    private int heart_rate;
    private int s_pressure;
    private int d_pressure;
    private int blood_oxygen;
    private int breath;
    private int act;
    private int data_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEqp_id() {
        return eqp_id;
    }

    public void setEqp_id(int eqp_id) {
        this.eqp_id = eqp_id;
    }

    public int getNetmask_id() {
        return netmask_id;
    }

    public void setNetmask_id(int netmask_id) {
        this.netmask_id = netmask_id;
    }

    public int getObject_id() {
        return object_id;
    }

    public void setObject_id(int object_id) {
        this.object_id = object_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public int getE_temp() {
        return e_temp;
    }

    public void setE_temp(int e_temp) {
        this.e_temp = e_temp;
    }

    public int getB_temp() {
        return b_temp;
    }

    public void setB_temp(int b_temp) {
        this.b_temp = b_temp;
    }

    public int getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(int heart_rate) {
        this.heart_rate = heart_rate;
    }

    public int getS_pressure() {
        return s_pressure;
    }

    public void setS_pressure(int s_pressure) {
        this.s_pressure = s_pressure;
    }

    public int getD_pressure() {
        return d_pressure;
    }

    public void setD_pressure(int d_pressure) {
        this.d_pressure = d_pressure;
    }

    public int getBlood_oxygen() {
        return blood_oxygen;
    }

    public void setBlood_oxygen(int blood_oxygen) {
        this.blood_oxygen = blood_oxygen;
    }

    public int getBreath() {
        return breath;
    }

    public void setBreath(int breath) {
        this.breath = breath;
    }

    public int getAct() {
        return act;
    }

    public void setAct(int act) {
        this.act = act;
    }

    public int getData_type() {
        return data_type;
    }

    public void setData_type(int data_type) {
        this.data_type = data_type;
    }
}
