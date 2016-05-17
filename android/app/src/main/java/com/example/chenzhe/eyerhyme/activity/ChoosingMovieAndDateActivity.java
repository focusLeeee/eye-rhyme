package com.example.chenzhe.eyerhyme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.adapter.ImagePagerAdapter;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.fragment.ProductFragment;
import com.example.chenzhe.eyerhyme.model.MovieItem;
import com.example.chenzhe.eyerhyme.model.TheaterItem;
import com.example.chenzhe.eyerhyme.model.getMoviesResponse;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.chenzhe.eyerhyme.util.ToastUtil;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChoosingMovieAndDateActivity extends AppCompatActivity implements viewController {

    @Bind(R.id.tb_title)
    TextView tbTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_loc)
    TextView tvLoc;
    @Bind(R.id.tv_grade)
    TextView tvGrade;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.container)
    LinearLayout container;
    @Bind(R.id.tv_movie_name)
    TextView tvMovieName;
    @Bind(R.id.tv_movie_grade)
    TextView tvMovieGrade;

    @Bind(R.id.product_pager)
    ViewPager productPager;
    @Bind(R.id.indicator)
    SlidingTabLayout indicator;
    @Bind(R.id.rl_theater)
    RelativeLayout rlTheater;
    @Bind(R.id.tv_release_date)
    TextView tvReleaseDate;
    @Bind(R.id.rl_film)
    RelativeLayout rlFilm;

    private ArrayList<View> views;
    private ArrayList<MovieItem> movieItems;
    private ArrayList<Fragment> fragments;
    private String getMovies = "/movie/get_movies";
    private TheaterItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_movie_and_date);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initTheater();
        initViewPager();
        initToolbar();
        initIndicator();
        getMovies();

        rlTheater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ChoosingMovieAndDateActivity.this, TheaterDetailActivity.class);
                it.putExtra("theater", item);
                startActivity(it);
            }
        });
    }

    private void initTheater() {
        item = (TheaterItem) getIntent().getSerializableExtra("theater");
        DecimalFormat df = new DecimalFormat("#######0.0");
        tvGrade.setText(df.format(item.getGrade()) + "");
        tvLoc.setText(item.getLocation());
        tvName.setText(item.getName());
    }

    private void initViewPager() {
        viewpager.setOffscreenPageLimit(3);
        viewpager.setPageMargin(20);
        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewpager.dispatchTouchEvent(event);
            }
        });
    }

    private void initIndicator() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str1 = formatter.format(curDate);
        String date1 = formatter2.format(curDate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(curDate);
        ca.add(Calendar.DAY_OF_MONTH, 1);
        String str2 = formatter.format(ca.getTime());
        String date2 = formatter2.format(ca.getTime());

        String[] strings = new String[2];
        strings[0] = str1;
        strings[1] = str2;

        fragments = new ArrayList<>();
        addFragment(item.getTheater_id(), date1);
        addFragment(item.getTheater_id(), date2);

        indicator.setViewPager(productPager, strings, this, fragments);

    }

    private void addFragment(int theater_id, String date) {
        Fragment fragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("theater_id", theater_id);
        bundle.putString("theater_name", item.getName());
        bundle.putString("date", date);
        fragment.setArguments(bundle);
        fragments.add(fragment);
    }

    private void initToolbar() {

        toolbar.setTitle("");
        tbTitle.setText("影院");
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

    private void getMovies() {
        HashMap<String, Object> map = new HashMap<>();
        PostUtil.newInstance().sendPost(ChoosingMovieAndDateActivity.this, getMovies, map);

    }

    @Override
    public void updateView(String url, String response) {
        if (response == null) {
            ToastUtil.printToast(this, "network fail");
            return;
        }
        if (url.equals(getMovies)) {
            getMoviesResponse moviesResponse = new Gson().fromJson(response, getMoviesResponse.class);
            if (moviesResponse.status) {
                movieItems = moviesResponse.movies;
                views = new ArrayList<>();
                for (int i = 0; i < movieItems.size(); i++) {
                    View temp = getLayoutInflater().inflate(R.layout.movie_image, null);
                    views.add(temp);
                }

                ImagePagerAdapter adapter = new ImagePagerAdapter(views, movieItems);

                viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        if (viewpager != null) {
                            viewpager.invalidate();
                        }
                    }

                    @Override
                    public void onPageSelected(int position) {
                        updateMovieInf(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

                viewpager.setAdapter(adapter);
                updateMovieInf(0);

            } else {
                ToastUtil.printToast(this, "获取电影失败");
                return;
            }
        }
    }

    private void updateMovieInf(int position) {
        final MovieItem item = movieItems.get(position);
        tvMovieName.setText(item.getName());
        tvMovieGrade.setText(item.getGrade() + "");
        tvReleaseDate.setText(item.getRelease_date()+"上映");
        rlFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ChoosingMovieAndDateActivity.this, MovieDetailActivity.class);
                it.putExtra("movie_id", item.getMovie_id());
                it.putExtra("name", item.getName());
                startActivity(it);
            }
        });
        for (int i = 0; i < fragments.size(); i++) {
            ProductFragment fragment = (ProductFragment) fragments.get(i);
            fragment.getProducts(item.getMovie_id());
        }
    }

}
