package com.example.chenzhe.eyerhyme.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by chenzhe on 2016/5/12.
 */
public class ActivityDetailResponse {
    public boolean status;
    public ActivityDetail result;


    public class ActivityDetail {
        public LauncherContainer launcher_detail;
        public MovieContainer movie_detail;
        public ActivityItem activity_detail;
        public ArrayList<UserIdName> join_detail;

        public boolean IdisJoin(int user_id) {
            for (int i = 0; i < join_detail.size(); i++) {
                UserIdName temp = join_detail.get(i);
                if (temp.getUser_id() == user_id) return true;
            }
            return false;
        }

        public class LauncherContainer {
            private int user_id;
            private String name;
            private int gender;
            private double rank;

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

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public double getRank() {
                return rank;
            }

            public void setRank(double rank) {
                this.rank = rank;
            }
        }

        public class MovieContainer implements Serializable{
            private int movie_id;
            private String movie_name;
            private int type;

            public int getMovie_id() {
                return movie_id;
            }

            public void setMovie_id(int movie_id) {
                this.movie_id = movie_id;
            }

            public String getMovie_name() {
                return movie_name;
            }

            public void setMovie_name(String movie_name) {
                this.movie_name = movie_name;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
