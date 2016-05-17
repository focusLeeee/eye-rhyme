package com.example.chenzhe.eyerhyme.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.model.ActivityDetailResponse;
import com.example.chenzhe.eyerhyme.model.ActivityItem;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.util.Date;
import java.util.HashMap;

public class EditEventActivity extends LaunchEventActivity {

    private int activity_id;

    @Override
    protected void init() {
        super.init();
        commitActivityURL = "/movie_activity/update_activity";
        ActivityItem item = (ActivityItem) getIntent().getSerializableExtra("activity_detail");
        ActivityDetailResponse.ActivityDetail.MovieContainer movieDetail = (ActivityDetailResponse.ActivityDetail.MovieContainer) getIntent().getSerializableExtra("movie_detail");

        movie_id = movieDetail.getMovie_id();
        date_time = item.getDate_time();
        content = item.getContent();
        join_num = item.getJoin_bound();
        location = item.getPlace();
        longitude = item.getLongitude();
        latitude = item.getLatitude();
        movie_name = movieDetail.getMovie_name();
        activity_id = item.getActivity_id();
        refreshView();
    }

    @Override
    protected void initToolbar() {

        toolbar.setTitle("");
        tbTitle.setText("编辑活动");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initListener() {


        llLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EditEventActivity.this, SetLocationActivity.class);
                it.putExtra("edit",1);
                startActivity(it);
            }
        });

        llTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        .setIs24HourTime(true)
                        .build()
                        .show();
            }
        });

        llNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] temp = new String[]{"1","2","3","4","5","6","7","8"};
                View mToolbar = LayoutInflater.from(EditEventActivity.this).inflate(R.layout.toolbar_support,null);
                TextView title = (TextView)mToolbar.findViewById(R.id.tb_title);
                title.setText("请选择人数");
                AlertDialog.Builder builder = new AlertDialog.Builder(EditEventActivity.this)
                        .setItems(temp, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                etNum.setText((which+1)+"");
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setCustomTitle(mToolbar);
                builder.show();
            }
        });
    }

    @Override
    protected void commitActivity() {
        HashMap<String , Object> map = new HashMap<>();
//        “user_id”: integer, 发起者的唯一标识
//        “movie_id”: integer, 活动对应的电影的唯一标识
//        “date_time”: string, 活动开始时间格式为：yyyy-MM-dd HH:mm:ss
//        “place”: string, 活动的地点
//        “contact”: string, 发起者的联系方式
//        “content”: string, 活动的说明内容
//        “join_bound”: integer, 活动人数上限
//        “longitude”: float, 集合地点的经度
//        “latitude”: float, 集合地点的纬度

        map.put("user_id", user_id);
        map.put("date_time", date_time);
        map.put("place", location);
        map.put("contact", phone);
        map.put("content", content);
        map.put("join_bound", join_num);
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        map.put("activity_id", activity_id);
        PostUtil.newInstance().sendPost(this, commitActivityURL, map);
    }
}
