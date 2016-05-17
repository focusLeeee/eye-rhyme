package com.example.chenzhe.eyerhyme.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.fragment.BillFragment;
import com.example.greendao.dao.BillDao;
import com.example.greendao.dao.DaoMaster;
import com.example.greendao.dao.DaoSession;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyBillActivity extends AppCompatActivity {

    @Bind(R.id.tb_title)
    TextView tbTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.indicator)
    SlidingTabLayout indicator;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bill);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initToolbar();
        initIndicator();
    }

    private void initIndicator() {
        String[] strings = new String[]{"未支付", "已支付"};
        fragments = new ArrayList<>();
        addFragment(0);
        addFragment(1);
        indicator.setViewPager(viewpager, strings, this, fragments);
    }

    private void addFragment(int type) {
        Fragment fragment = new BillFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        fragments.add(fragment);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        toolbar.setTitle("");
        tbTitle.setText("我的订单");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
}
