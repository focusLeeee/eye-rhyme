package com.example.chenzhe.eyerhyme.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.chenzhe.eyerhyme.util.ToastUtil;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LaunchEventActivity extends AppCompatActivity implements viewController {

    @Bind(R.id.tb_title)
    TextView tbTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_film)
    TextView tvFilm;
    @Bind(R.id.ll_film)
    LinearLayout llFilm;
    @Bind(R.id.et_description)
    EditText etDescription;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.ll_time)
    LinearLayout llTime;
    @Bind(R.id.tv_loc)
    TextView tvLoc;
    @Bind(R.id.ll_loc)
    LinearLayout llLoc;
    @Bind(R.id.et_num)
    TextView etNum;
    @Bind(R.id.ll_num)
    LinearLayout llNum;

    protected int user_id;
    protected int movie_id;
    protected String date_time;
    protected String location;
    protected String movie_name;
    protected String phone;
    protected String content;
    protected int join_num;
    protected double longitude;
    protected double latitude;
    protected String commitActivityURL = "/movie_activity/add_activity";
    private SharedPreferences sharedPreferences;
    protected SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
            SimpleDateFormat tvformat = new SimpleDateFormat("yyyy年MM月dd日   HH:mm");
            date_time = format.format(date);
            tvTime.setText(tvformat.format(date));
        }

        @Override
        public void onDateTimeCancel() {
            // Overriding onDateTimeCancel() is optional.
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_event);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
        initToolbar();
        initListener();
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        user_id = sharedPreferences.getInt("user_id",-1);
        phone = sharedPreferences.getString("phone", "");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
    }

    protected void initListener() {
        llFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LaunchEventActivity.this, MovieListActivity2.class);
                startActivity(it);
            }
        });

        llLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LaunchEventActivity.this, SetLocationActivity.class);
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
                View mToolbar = LayoutInflater.from(LaunchEventActivity.this).inflate(R.layout.toolbar_support,null);
                TextView title = (TextView)mToolbar.findViewById(R.id.tb_title);
                title.setText("请选择人数");
                AlertDialog.Builder builder = new AlertDialog.Builder(LaunchEventActivity.this)
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
    protected void onResume() {
        super.onResume();
        if (getIntent().getExtras() != null) {
            int type = getIntent().getIntExtra("type", -1);
            if (type == -1) return;
            switch (type) {
                case 0:
                    movie_id = getIntent().getIntExtra("movie_id", -1);
                    tvFilm.setText(getIntent().getStringExtra("name"));
                    break;
                case 1:
                    location = getIntent().getStringExtra("location");
                    tvLoc.setText(location);
                    longitude = getIntent().getDoubleExtra("longitude",0);
                    latitude = getIntent().getDoubleExtra("latitude", 0);
            }
        }
    }

    protected void initToolbar() {

        toolbar.setTitle("");
        tbTitle.setText("发起活动");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_launch_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_commit) {
            if (tvTime.getText().toString().equals("")) {
                ToastUtil.printToast(this, "请设定时间");
                return super.onOptionsItemSelected(item);
            } else if (tvLoc.getText().toString().equals("")) {
                ToastUtil.printToast(this, "请设定地点");
                return super.onOptionsItemSelected(item);
            } else if (tvFilm.getText().toString().equals("")) {
                ToastUtil.printToast(this, "请设定电影");
                return super.onOptionsItemSelected(item);
            } else if (etDescription.getText().toString().equals("")) {
                ToastUtil.printToast(this, "请设定活动描述");
                return super.onOptionsItemSelected(item);
            } else if(etNum.getText().toString().equals("")) {
                ToastUtil.printToast(this, "请设定参与人数");
                return super.onOptionsItemSelected(item);
            }
            content = etDescription.getText().toString();
            join_num = Integer.parseInt(etNum.getText().toString());
            commitActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void refreshView() {
        tvFilm.setText(movie_name);
        tvLoc.setText(location);
        tvTime.setText(date_time);
        etNum.setText(join_num+"");
        etDescription.setText(content);
    }

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
        map.put("movie_id", movie_id);
        map.put("date_time", date_time);
        map.put("place", location);
        map.put("contact", phone);
        map.put("content", content);
        map.put("join_bound", join_num);
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        PostUtil.newInstance().sendPost(this, commitActivityURL, map);
    }

    @Override
    public void updateView(String url, String response) {
        if (response == null) {
            ToastUtil.printToast(this, "network fail");
            return;
        }
        if (url.equals(commitActivityURL)) {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")) {
                    finish();
                } else {
                    ToastUtil.printToast(this, "提交失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
