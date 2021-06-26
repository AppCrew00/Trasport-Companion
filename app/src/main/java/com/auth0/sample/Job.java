package com.auth0.sample;

public class Job {
    private String name,email,phone_number,address,landmark,weight,payment,time,job_title,latLong,pincode;

    public Job(String name, String email, String phone_number, String address, String landmark, String weight, String payment, String time, String job_title, String latLong, String pincode) {
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.address = address;
        this.landmark = landmark;
        this.weight = weight;
        this.payment = payment;
        this.time = time;
        this.job_title = job_title;
        this.latLong = latLong;
        this.pincode = pincode;
    }

    private Job ()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}