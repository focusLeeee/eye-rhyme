package com.example.chenzhe.eyerhyme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.fragment.ProductFragment;
import com.example.chenzhe.eyerhyme.model.TheaterItem;
import com.flyco.tablayout.SlidingTabLayout;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChoosingProductActivity extends AppCompatActivity {

    @Bind(R.id.tb_title)
    TextView tbTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.indicator)
    SlidingTabLayout indicator;
    @Bind(R.id.product_pager)
    ViewPager productPager;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_loc)
    TextView tvLoc;
    @Bind(R.id.tv_grade)
    TextView tvGrade;
    @Bind(R.id.rl_theater)
    RelativeLayout rlTheater;

    private ArrayList<Fragment> fragments;
    private TheaterItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_product);
        ButterKnife.bind(this);
        item = (TheaterItem) getIntent().getSerializableExtra("theater");
        init();
    }

    private void init() {
        initTheater();
        initToolbar();
        initIndicator();

        rlTheater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ChoosingProductActivity.this, TheaterDetailActivity.class);
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

    private void initToolbar() {

        toolbar.setTitle("");
        tbTitle.setText("场次");
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

    private void addFragment(int theater_id, String date) {
        Fragment fragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("theater_id", theater_id);
        bundle.putString("theater_name", item.getName());
        bundle.putInt("movie_id", getIntent().getIntExtra("movie_id", -1));
        bundle.putString("date", date);
        fragment.setArguments(bundle);
        fragments.add(fragment);
    }
}
