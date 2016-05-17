package com.example.chenzhe.eyerhyme.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.adapter.CommentListAdapter;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.model.MovieCommentItem;
import com.example.chenzhe.eyerhyme.model.MovieCommentResponse;
import com.example.chenzhe.eyerhyme.model.MovieDetailItem;
import com.example.chenzhe.eyerhyme.model.MovieDetailResponse;
import com.example.chenzhe.eyerhyme.model.MovieItem;
import com.example.chenzhe.eyerhyme.model.getMoviesResponse;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.chenzhe.eyerhyme.util.ToastUtil;
import com.example.chenzhe.eyerhyme.util.TransferUtil;
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
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_period)
    TextView tvPeriod;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.iv_collect)
    ImageView ivCollect;
    @Bind(R.id.tv_collect)
    TextView tvCollect;
    @Bind(R.id.rl_collect)
    RelativeLayout rlCollect;
    @Bind(R.id.iv_comment)
    ImageView ivComment;
    @Bind(R.id.tv_comment)
    TextView tvComment;
    @Bind(R.id.rl_comment)
    RelativeLayout rlComment;

    private String MovieDetailURL = "/movie/get_movie_detail";
    private String MovieCommentURL = "/movie/get_grades";

    private String userCollectURL = "";
    private ArrayList<MovieCommentItem> commentItems;
    private CommentListAdapter commentListAdapter;
    private MovieDetailItem item;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        init();
    }

    private void init() {
//        initViews();
        initToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    private void initToolbar() {

        toolbar.setTitle("");
        tbTitle.setText(getIntent().getStringExtra("name"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        rlComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MovieDetailActivity.this, WritingMovieCommentActivity.class);
                it.putExtra("movie_id", getIntent().getIntExtra("movie_id",-1));
                startActivity(it);
            }
        });
    }



    private void getMovieDetail() {
        HashMap<String, Object> map = new HashMap<>();
        int movie_id = getIntent().getIntExtra("movie_id", -1);
        map.put("movie_id", movie_id);
        PostUtil.newInstance().imageGET(ivPost, "movie", movie_id);
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
                tvActor.setText("主演: " + item.getActors());
                tvDirector.setText("导演: " + item.getDirectors());
                tvDate.setText("上映时间: "+ item.getRelease_date());
                tvPeriod.setText("时长: "+ item.getDuration()+"分钟");
                tvType.setText("类型: "+ TransferUtil.transferFilmType(item.getType()));
                tvDiscription.setText(item.getDescription());
                tvRating.setText(item.getGrade() + "");

                ratingBar.setRating((int) (item.getGrade()));
            } else {
                ToastUtil.printToast(this, "获取电影详情失败");
            }
        }
    }

}
