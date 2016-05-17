package com.example.chenzhe.eyerhyme.model;

/**
 * Created by chenzhe on 2016/5/15.
 */
public class TheaterDetail {
//    “theater_id”: integer, 该影院的标识
//    “name”: string, 影院的名称
//    “location”: string, 影院的文字地址
//    “contact”: string, 影院的联系电话
//    “description”: string, 影院的特色描述信息
//    “grade”: float, 影院的平均评分
//    “grade_num”: float, 评分的人数
//    “longitude”: float, 该影院的经度
//    “latitude”: float, 该影院的纬度
    private int theater_id;
    private String name;
    private String location;
    private String contact;
    private String description;
    private double grade;
    private double grade_num;
    private double longitude;
    private double latitude;

    public int getTheater_id() {
        return theater_id;
    }

    public void setTheater_id(int theater_id) {
        this.theater_id = theater_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getGrade_num() {
        return grade_num;
    }

    public void setGrade_num(double grade_num) {
        this.grade_num = grade_num;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
