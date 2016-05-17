package com.example.chenzhe.eyerhyme.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.chenzhe.eyerhyme.util.ToastUtil;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ForgetPwNextActivity extends AppCompatActivity implements viewController {

    @Bind(R.id.tb_title)
    TextView tbTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_newpw)
    EditText tvNewpw;
    @Bind(R.id.tv_repw)
    EditText tvRepw;
    @Bind(R.id.bn_commit)
    Button bnCommit;

    private String salt;
    private String password;
    private String getSalt = "/account/get_salt";
    private String updatePw = "/account/update";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pw_next);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initToolbar();
        initListener();
    }

    private void initToolbar() {

        toolbar.setTitle("");
        tbTitle.setText("重置密码");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initListener() {
        bnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvNewpw.getText().toString().equals("")) {
                    ToastUtil.printToast(ForgetPwNextActivity.this, "请输入新密码");
                } else if (tvRepw.getText().toString().equals("")) {
                    ToastUtil.printToast(ForgetPwNextActivity.this, "请再次输入新密码");
                } else if (!tvNewpw.getText().toString().equals(tvRepw.getText().toString())) {
                    ToastUtil.printToast(ForgetPwNextActivity.this, "两次密码不一致");
                } else {
                    password = tvNewpw.getText().toString();
                    getSalt();
                }
            }
        });
    }

    private void getSalt() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", getIntent().getStringExtra("phone"));
        PostUtil.newInstance().sendPost(this, getSalt, map);
    }

    private void resetPw() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", getIntent().getIntExtra("id",-1));
        map.put("password", LoginActivity.Md5(password+salt));
        PostUtil.newInstance().sendPost(this, updatePw, map);
    }

    @Override
    public void updateView(String url, String response) {
        if (response == null) {
            ToastUtil.printToast(this, "network fail");
            return;
        }
        if (url.equals(getSalt)) {
            try {
                JSONObject json = new JSONObject(response);
                if (json.getBoolean("status")) {
                    salt = json.getString("salt");
                    resetPw();
                } else {
                    Toast.makeText(ForgetPwNextActivity.this, "账号尚未注册", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (url.equals(updatePw)) {
            try {
                JSONObject json = new JSONObject(response);
                if (json.getBoolean("status")) {
                    Intent it = new Intent(ForgetPwNextActivity.this, LoginActivity.class);
                    startActivity(it);
                    ForgetPwNextActivity.this.finish();
                } else {
                    Toast.makeText(ForgetPwNextActivity.this, "重置失败", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
