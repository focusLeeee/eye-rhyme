package com.example.chenzhe.eyerhyme.model;

import java.io.Serializable;

/**
 * Created by chenzhe on 2016/5/12.
 */
public class UserDetail implements Serializable{
//    “status”: Boolean, 结果状态
//    “email”: string 邮箱
//    “birthday”: string 生日，字符串格式为"yyyy-MM-dd"
//            “gender”: integer 0代表男，1代表女
//    “hobby”: string 爱好
//    “name”: string 用户名
//    “rank”: float, 用户的等级
//    “signature”: string 个人签名
    private Boolean status;
    private String email;
    private String birthday;
    private String hobby;
    private String name;
    private int gender;
    private double rank;
    private String signature;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
