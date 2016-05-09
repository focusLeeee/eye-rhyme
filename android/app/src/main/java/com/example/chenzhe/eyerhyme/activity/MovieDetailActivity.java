package com.example.chenzhe.eyerhyme.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.adapter.CommentListAdapter;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.model.MovieCommentItem;
import com.example.chenzhe.eyerhyme.model.MovieCommentResponse;
import com.example.chenzhe.eyerhyme.model.MovieDetailItem;
import com.example.chenzhe.eyerhyme.model.MovieDetailResponse;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.chenzhe.eyerhyme.util.ToastUtil;
import com.example.chenzhe.eyerhyme.view.MyListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.techery.properratingbar.ProperRatingBar;

public class MovieDetailActivity extends AppCompatActivity implements viewController {

    @Bind(R.id.tb_title)
    TextView tbTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_post)
    ImageView ivPost;
    @Bind(R.id.rating_bar)
    ProperRatingBar ratingBar;
    @Bind(R.id.tv_rating)
    TextView tvRating;
    @Bind(R.id.tv_date_and_period)
    TextView tvDateAndPeriod;
    @Bind(R.id.tv_director)
    TextView tvDirector;
    @Bind(R.id.tv_actor)
    TextView tvActor;
    @Bind(R.id.tv_discription)
    TextView tvDiscription;
    @Bind(R.id.listview)
    MyListView listview;
    @Bind(R.id.tv_more_comment)
    TextView tvMoreComment;
    @Bind(R.id.bn_buy)
    Button bnBuy;

    private String MovieDetailURL = "/movie/get_movie_detail";
    private String MovieCommentURL = "/movie/get_grades";
    private ArrayList<MovieCommentItem> commentItems;
    private CommentListAdapter commentListAdapter;
    private MovieDetailItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initViews();
        initToolbar();
    }

    private void initToolbar() {

        toolbar.setTitle("");
        tbTitle.setText(getIntent().getStringExtra("name"));
        setActionBar(toolbar);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        getMovieComment();
        getMovieDetail();

        bnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MovieDetailActivity.this, TheaterListActivity2.class);
                it.putExtra("movie_id", getIntent().getIntExtra("movie_id", -1));
                startActivity(it);
            }
        });
    }

    private void getMovieDetail() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("movie_id", getIntent().getIntExtra("movie_id", -1));
        PostUtil.newInstance().sendPost(this, MovieDetailURL, map);
    }

    private void getMovieComment() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("movie_id", getIntent().getIntExtra("movie_id", -1));
        PostUtil.newInstance().sendPost(this, MovieCommentURL, map);
    }

    @Override
    public void updateView(String url, String response) {
        if (response == null) {
            ToastUtil.printToast(this, "network fail");
            return;
        }
        if (url.equals(MovieCommentURL)) {
            MovieCommentResponse movieCommentResponse = new Gson().fromJson(response, MovieCommentResponse.class);
            if (movieCommentResponse.status) {
                commentItems = movieCommentResponse.result;
                commentListAdapter = new CommentListAdapter(commentItems, this);
                listview.setAdapter(commentListAdapter);
            } else {
                ToastUtil.printToast(this, "获取评论失败");
            }
        } else if (url.equals(MovieDetailURL)) {
            MovieDetailResponse detailResponse = new Gson().fromJson(response, MovieDetailResponse.class);
            if (detailResponse.status) {
                item = detailResponse.result;
                tvActor.setText("主演：" + item.getActors());
                tvDirector.setText("导演：" + item.getDirectors());
                tvDateAndPeriod.setText(item.getRelease_date() + "/" + item.getDuration() + "分钟");
                tvDiscription.setText(item.getDescription());
                tvRating.setText(item.getGrade() + "");
                ratingBar.setRating((int) (item.getGrade()));
            } else {
                ToastUtil.printToast(this, "获取电影详情失败");
            }
        }
    }

    @Override
    public Context myContext() {
        return this;
    }
}
