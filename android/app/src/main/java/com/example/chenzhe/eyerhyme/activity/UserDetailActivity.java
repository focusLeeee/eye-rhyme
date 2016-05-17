package com.example.chenzhe.eyerhyme.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.model.UserDetail;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.chenzhe.eyerhyme.util.ToastUtil;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserDetailActivity extends AppCompatActivity implements viewController {

    @Bind(R.id.tb_title)
    TextView tbTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_head)
    RoundedImageView ivHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.iv_sex)
    ImageView ivSex;
    @Bind(R.id.tv_age)
    TextView tvAge;
    @Bind(R.id.tv_level)
    TextView tvLevel;
    @Bind(R.id.tv_hobby)
    TextView tvHobby;

    private String userDetailURL = "/user/get_user_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initToolbar();
        initViews();
    }

    private void initViews() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", getIntent().getIntExtra("user_id", -1));
        PostUtil.newInstance().sendPost(this, userDetailURL, map);
    }

    private void initToolbar() {

        toolbar.setTitle("");
        tbTitle.setText("用户详情");
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

    @Override
    public void updateView(String url, String response) {
        if (response == null) {
            ToastUtil.printToast(this, "network fail");
            return;
        }
        if (url.equals(userDetailURL)) {
            UserDetail detail = new Gson().fromJson(response, UserDetail.class);
            if (detail.getStatus()) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                tvDescription.setText(detail.getSignature());
                tvName.setText(detail.getName());
                tvLevel.setText("lv."+(int)detail.getRank());
                tvHobby.setText(detail.getHobby());

                if (detail.getBirthday() != null) {
                    try {
                        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                        Date curDate = new Date(System.currentTimeMillis());
                        Date birth = formatter2.parse(detail.getBirthday());
                        tvAge.setText((curDate.getYear()-birth.getYear())+"岁");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    tvAge.setText("");
                }


                if (detail.getGender() == 1) {
                    ivSex.setImageResource(R.mipmap.female);
                }
                PostUtil.newInstance().imageGET(ivHead, "user", getIntent().getIntExtra("user_id", -1));
            } else {
                ToastUtil.printToast(this, "获取用户信息失败");
            }


        }
    }
}
