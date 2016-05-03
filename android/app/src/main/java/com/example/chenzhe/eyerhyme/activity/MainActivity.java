package com.example.chenzhe.eyerhyme.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.adapter.MyFragmentAdapter;
import com.example.chenzhe.eyerhyme.fragment.FilmFragment;
import com.example.chenzhe.eyerhyme.fragment.MeetingFragment;
import com.example.chenzhe.eyerhyme.fragment.NewsFragment;
import com.example.chenzhe.eyerhyme.fragment.UserFragment;
import com.mob.tools.gui.ViewPagerAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AMapLocationListener {

    @Bind(R.id.tb_title)
    TextView tbTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.rb_films)
    RadioButton rbFilms;
    @Bind(R.id.rb_news)
    RadioButton rbNews;
    @Bind(R.id.rb_square)
    RadioButton rbSquare;
    @Bind(R.id.rb_me)
    RadioButton rbMe;
    @Bind(R.id.radiogroup)
    RadioGroup radiogroup;

    private ArrayList<Fragment> fragments;
    private boolean scroll;
    public static double longitude;
    public static double latitude;
    private AMapLocationClient mLocationClient;
    //声明定位回调监听器

    private AMapLocationClientOption mLocationOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationClient.startLocation();
    }

    private void init() {
        scroll = false;
        initToolbar();
        initLocation();
        initPager();
        initRadioButton();
    }

    private void initLocation() {


        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(this);

        mLocationOption = new AMapLocationClientOption();
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
//设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
//设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);

//给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();

    }

    private void initRadioButton() {
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (!scroll) {
                    switch (checkedId) {
                        case R.id.rb_films:
                            pager.setCurrentItem(0, false);
                            break;
                        case R.id.rb_news:
                            pager.setCurrentItem(1, false);
                            break;
                        case R.id.rb_square:
                            pager.setCurrentItem(2, false);
                            break;
                        case R.id.rb_me:
                            pager.setCurrentItem(3, false);
                            break;
                    }
                }
            }
        });
    }

    private void initPager() {
        fragments = new ArrayList<>();
        fragments.add(new FilmFragment());
        fragments.add(new NewsFragment());
        fragments.add(new MeetingFragment());
        fragments.add(new UserFragment());
        pager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), fragments));
        pager.setCurrentItem(0);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                scroll = true;
                switch (position) {
                    case 0:
                        radiogroup.check(R.id.rb_films);
                        break;
                    case 1:
                        radiogroup.check(R.id.rb_news);
                        break;
                    case 2:
                        radiogroup.check(R.id.rb_square);
                        break;
                    case 3:
                        radiogroup.check(R.id.rb_me);
                        break;
                }
                scroll = false;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initToolbar() {

        toolbar.setTitle("");
        tbTitle.setText("Eye Rhyme");
        setActionBar(toolbar);
        getActionBar().setHomeButtonEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                longitude = aMapLocation.getLongitude();
                latitude = aMapLocation.getLatitude();
                Log.i("loc", "longitude: " + longitude + " latitude: " + latitude);
            } else {

            }
        }
    }
}
