package com.example.chenzhe.eyerhyme.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.model.UserDetail;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.chenzhe.eyerhyme.util.ToastUtil;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserEditActivity extends AppCompatActivity implements viewController {

    @Bind(R.id.tb_title)
    TextView tbTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_head)
    RoundedImageView ivHead;
    @Bind(R.id.ll_head)
    LinearLayout llHead;
    @Bind(R.id.ll_alias)
    LinearLayout llAlias;
    @Bind(R.id.ll_email)
    LinearLayout llEmail;
    @Bind(R.id.ll_sex)
    LinearLayout llSex;
    @Bind(R.id.ll_birthday)
    LinearLayout llBirthday;
    @Bind(R.id.ll_signature)
    LinearLayout llSignature;
    @Bind(R.id.ll_hobby)
    LinearLayout llHobby;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_alias)
    TextView tvAlias;
    @Bind(R.id.tv_email)
    TextView tvEmail;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_birthday)
    TextView tvBirthday;
    @Bind(R.id.tv_signature)
    TextView tvSignature;
    @Bind(R.id.tv_hobby)
    TextView tvHobby;

    private String editUserURL = "/user/update";
    private String getUserURL = "/user/get_user_detail";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        initListener();
        initToolbar();
        getUserDetail();

    }

    private void initToolbar() {

        toolbar.setTitle("");
        tbTitle.setText("Eye Rhyme");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initListener() {
        View.OnClickListener textListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int id = v.getId();
                final EditText editText = new EditText(UserEditActivity.this);
                editText.setHint("修改内容");
                editText.setCursorVisible(true);
                final Toolbar mToolbar = new Toolbar(UserEditActivity.this);
                mToolbar.setTitle("修改内容");
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                AlertDialog.Builder builder = new AlertDialog.Builder(UserEditActivity.this)
                        .setView(editText)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditUser(id, editText.getText().toString());
                            }
                        });
                builder.show();
            }
        };

        llAlias.setOnClickListener(textListener);
        llEmail.setOnClickListener(textListener);
        llHobby.setOnClickListener(textListener);
        llSignature.setOnClickListener(textListener);

    }

    private void getUserDetail() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", sharedPreferences.getInt("user_id", -1));
        PostUtil.newInstance().sendPost(this, getUserURL, map);
        PostUtil.newInstance().imageGET(ivHead, "user", sharedPreferences.getInt("user_id", -1));
    }

    private void EditUser(int key, Object value) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", sharedPreferences.getInt("user_id", -1));
        String name = "";
        switch (key) {
            case R.id.ll_sex:
                name = "gender";
                break;
            case R.id.ll_alias:
                name = "name";
                break;
            case R.id.ll_signature:
                name = "signature";
                break;
            case R.id.ll_hobby:
                name = "hobby";
                break;
            case R.id.ll_birthday:
                name = "birthday";
                break;
            case R.id.ll_email:
                name = "email";
                break;
        }
        map.put(name, value);
        PostUtil.newInstance().sendPost(this, editUserURL, map);
    }

    @Override
    public void updateView(String url, String response) {
        if (response == null) {
            ToastUtil.printToast(this, "network fail");
            return;
        }
        if (url.equals(editUserURL)) {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")) {
                    getUserDetail();
                } else {
                    ToastUtil.printToast(this, "修改失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (url.equals(getUserURL)) {
            UserDetail detail = new Gson().fromJson(response, UserDetail.class);
            if (detail.getStatus()) {
                tvAlias.setText(detail.getName());
                tvBirthday.setText(detail.getBirthday());
                tvEmail.setText(detail.getEmail());
                tvHobby.setText(detail.getHobby());
                tvPhone.setText(sharedPreferences.getString("phone",""));
                tvSignature.setText(detail.getSignature());
                if (detail.getGender() == 1) {
                    tvSex.setText("女");
                }
            } else {
                ToastUtil.printToast(this, "获取个人信息失败");
            }
        }
    }
}
