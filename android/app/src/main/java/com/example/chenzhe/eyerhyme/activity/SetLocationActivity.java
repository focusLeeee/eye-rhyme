package com.example.chenzhe.eyerhyme.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.geocoder.RegeocodeRoad;

import com.example.chenzhe.eyerhyme.R;
import com.rengwuxian.materialedittext.MaterialEditText;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SetLocationActivity extends AppCompatActivity implements LocationSource, AMapLocationListener,
        AMap.OnCameraChangeListener, GeocodeSearch.OnGeocodeSearchListener {

    @Bind(R.id.tb_title)
    TextView tbTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.map)
    MapView mapView;
    @Bind(R.id.et_loc)
    MaterialEditText etLoc;

    private double latitude;
    private double longitude;
    private String location;
    private boolean first;
    private AMap aMap;

    private AMapLocationClient mLocationClient;
    //声明定位回调监听器
    private AMapLocationListener mLocationListener;
    private OnLocationChangedListener mListener;
    private AMapLocationClientOption mLocationOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);
        ButterKnife.bind(this);

        first = true;
        mapView.onCreate(savedInstanceState);// 必须要写
        aMap = mapView.getMap();
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
//设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
        aMap.setOnCameraChangeListener(this);

        initMap();
        initToolbar();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    private void initMap() {

        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(this);


    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // must store the new intent unless getIntent() will return the old one
        setIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.set_location_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void initToolbar() {

        toolbar.setTitle("");
        tbTitle.setText("设定地点");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.set_loc) {
            Intent it;
            if (getIntent().getIntExtra("edit", -1) == 1) {
                it = new Intent(SetLocationActivity.this, EditEventActivity.class);
            } else {
                it = new Intent(SetLocationActivity.this, LaunchEventActivity.class);
            }
            it.putExtra("type", 1);
            it.putExtra("location", etLoc.getText().toString());
            it.putExtra("longitude", longitude);
            it.putExtra("latitude", latitude);
            startActivity(it);
            finish();
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {

                mListener.onLocationChanged(amapLocation);
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                latitude = amapLocation.getLatitude();//获取纬度
                longitude = amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果
                location = "";
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                location += amapLocation.getCity();//城市信息
                location += amapLocation.getDistrict();//城区信息
                location += amapLocation.getRoad();//街道信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码
//                etLoc.setText(location);
                CameraUpdate cu = CameraUpdateFactory.zoomTo(15);
                aMap.moveCamera(cu);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setOnceLocation(true);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        LatLng latLng = cameraPosition.target;
        longitude = latLng.longitude;
        latitude = latLng.latitude;
        location = "移动图钉";
/*

        Animation alphaAnimation=new AlphaAnimation(1, (float) 0);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);
        etLoc.startAnimation(alphaAnimation);
        etLoc.setText(location);
        alphaAnimation=new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);
        etLoc.startAnimation(alphaAnimation);
*/
        if (!first) {
            GeocodeSearch geocoderSearch = new GeocodeSearch(this);
            geocoderSearch.setOnGeocodeSearchListener(this);
//latLonPoint参数表示一个Latlng，第二参数表示范围多少米，GeocodeSearch.AMAP表示是国测局坐标系还是GPS原生坐标系
            RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latitude, longitude), 200, GeocodeSearch.AMAP);
            geocoderSearch.getFromLocationAsyn(query);
        } else {
            first = false;
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                RegeocodeAddress regeocodeAddress = result.getRegeocodeAddress();
                location = "";
                location += regeocodeAddress.getCity();//城市信息
                location += regeocodeAddress.getDistrict();//城区信息
                List<RegeocodeRoad> roads = regeocodeAddress.getRoads();
                location += roads.get(0).getName();

                Animation alphaAnimation = new AlphaAnimation(1, (float) 0);
                alphaAnimation.setDuration(500);
                alphaAnimation.setFillAfter(true);
                etLoc.startAnimation(alphaAnimation);
                etLoc.setText(location);
                alphaAnimation = new AlphaAnimation(0, 1);
                alphaAnimation.setDuration(500);
                alphaAnimation.setFillAfter(true);
                etLoc.startAnimation(alphaAnimation);
            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}