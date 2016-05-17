package com.example.chenzhe.eyerhyme.model;

/**
 * Created by chenzhe on 2016/5/3.
 */
public class MovieItem {
//    “movie_id”: integer, 该电影的标识
//    “name”: string, 电影的名称
//    “grade”: float, 电影的平均评分
//    “duration”: integer, 电影的时长
//    “release_date”: string, 该电影的上映时间
//    “type”: integer, 该电影的类型
//    “actors”: string, 该电影的主演
    private int movie_id;
    private String name;
    private float grade;
    private int duration;
    private String release_date;
    private int type;
    private String actors;
    private String directors;

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }
}
