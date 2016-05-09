package com.example.chenzhe.eyerhyme.model;

/**
 * Created by Jay on 2016/5/6.
 */
public class TheaterCommentItem {
    // user_id: 评论者id
    // user_name: 评论者昵称
    // grade: 评分
    // content: 评论内容
    // time: 评论时间
    private int user_id;
    private String user_name;
    private double grade;
    private String content;
    private String time;
    public TheaterCommentItem(int id, String name, double t_grade, String cont, String t) {
        user_id = id;
        user_name = name;
        grade = t_grade;
        content = cont;
        time = t;
    }

    public int getUser_id() { return user_id; }

    public String getUser_name() { return user_name; }

    public double getGrade() { return grade; }

    public String getContent() { return content; }

    public String getTime() { return time; }
}
