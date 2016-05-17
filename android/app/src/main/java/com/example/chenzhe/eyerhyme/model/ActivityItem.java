package com.example.chenzhe.eyerhyme.model;

import java.io.Serializable;

/**
 * Created by chenzhe on 2016/5/10.
 */
public class ActivityItem implements Serializable{
//    “activity_id”: integer, 该活动的唯一标识
//    “movie_name”: string, 电影的名称
//    “user_id”: integer, 发起人的标识
//    “name”: string, 发起人的用户名
//    “date_time”: string, 活动开始时间
//    “place”: string, 地点
//    “join_num”: integer, 已参与的人数
//    “join_bound”: integer, 参与人数上限
    private int activity_id;
    private String contact;
    private String content;
    private String movie_name;
    private int user_id;
    private String name;
    private String date_time;
    private String place;
    private int join_num;
    private int join_bound;
    private double longitude;
    private double latitude;

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getJoin_num() {
        return join_num;
    }

    public void setJoin_num(int join_num) {
        this.join_num = join_num;
    }

    public int getJoin_bound() {
        return join_bound;
    }

    public void setJoin_bound(int join_bound) {
        this.join_bound = join_bound;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
