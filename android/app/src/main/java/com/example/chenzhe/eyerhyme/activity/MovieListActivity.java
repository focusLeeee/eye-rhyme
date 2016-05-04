package com.example.chenzhe.eyerhyme.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.adapter.MovieListAdapter;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.model.MovieItem;
import com.example.chenzhe.eyerhyme.model.getMoviesResponse;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.chenzhe.eyerhyme.util.ToastUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGAStickinessRefreshViewHolder;

public class MovieListActivity extends AppCompatActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, viewController {

    @Bind(R.id.tb_title)
    TextView tbTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.refreshlayout)
    BGARefreshLayout refreshlayout;
    private String getMovies = "/movie/get_movies";
    private getMoviesResponse moviesResponse;
    private MovieListAdapter movieListAdapter;
    private ArrayList<MovieItem> movieItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);
        init();

        movieItems = null;
        getMovies(-1);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initToolbar() {

        toolbar.setTitle("");
        tbTitle.setText("电影列表");
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

    private void init() {
        initRefreshLayout();
        initToolbar();
        initListview();
    }

    private void initListview() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(MovieListActivity.this, MovieDetailActivity.class);
                it.putExtra("movie_id", movieItems.get(position).getMovie_id());
                it.putExtra("name", movieItems.get(position).getName());
                startActivity(it);
            }
        });
    }

    private void initRefreshLayout() {
        refreshlayout.setDelegate(this);
        BGARefreshViewHolder viewHolder = new BGANormalRefreshViewHolder(this, true);
        refreshlayout.setRefreshViewHolder(viewHolder);
    }

    private void getMovies(int id) {
        HashMap<String, Object> map = new HashMap<>();
        if (id != -1)
        map.put("movie_id", id);
        PostUtil.newInstance().sendPost(MovieListActivity.this, getMovies, map);

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        movieItems = null;
        getMovies(-1);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (movieItems == null) {
            getMovies(-1);
        } else {
            getMovies(movieItems.get(movieItems.size()-1).getMovie_id());
        }
        return true;
    }

    @Override
    public void updateView(String url, String response) {
        refreshlayout.endRefreshing();
        refreshlayout.endLoadingMore();
        if (response == null) {
            ToastUtil.printToast(this, "network fail");
            return;
        }
        if (url.equals(getMovies)) {
            moviesResponse = new Gson().fromJson(response, getMoviesResponse.class);
            if (moviesResponse.status) {
                if (movieItems == null) {
                    movieItems = moviesResponse.theaters;
                    movieListAdapter = new MovieListAdapter(movieItems, MovieListActivity.this);
                    listview.setAdapter(movieListAdapter);
                } else {
                    movieItems.addAll(moviesResponse.theaters);
                    movieListAdapter.notifyDataSetChanged();
                }
            } else {
                ToastUtil.printToast(this, "获取电影失败");
                return;
            }
        }
    }

    @Override
    public Context myContext() {
        return this;
    }
}
