package com.auth0.sample;

import java.io.Serializable;

public class Bid  {

    private String name;
    private String phoneNo;
    private String money;
    private String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Bid(String name, String phoneNo, String money, String time) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.money = money;
        this.time = time;
    }
}
