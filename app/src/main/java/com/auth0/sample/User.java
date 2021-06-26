package com.auth0.sample;

public class User {
    private String full_name,phone_number,address,pincode,image_url;

    public User(String full_name, String phone_number, String address, String pincode, String image_url) {
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.address = address;
        this.pincode = pincode;
        this.image_url = image_url;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
