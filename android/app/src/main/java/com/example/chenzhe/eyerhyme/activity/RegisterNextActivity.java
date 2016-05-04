package com.example.chenzhe.eyerhyme.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.chenzhe.eyerhyme.util.ToastUtil;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.smssdk.SMSSDK;

public class RegisterNextActivity extends AppCompatActivity implements viewController {

    @Bind(R.id.tb_title)
    TextView tbTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_nickname)
    EditText tvNickname;
    @Bind(R.id.tv_pw)
    EditText tvPw;
    @Bind(R.id.tv_repw)
    EditText tvRepw;
    @Bind(R.id.bn_commit)
    Button bnCommit;

    private static String registerUrl = "/account/register";
    private String salt;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initToolbar();
        initListener();
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
    }

    private void initListener() {
        bnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvNickname.getText().toString().equals("")) {
                    Toast.makeText(RegisterNextActivity.this, "请输入昵称", Toast.LENGTH_SHORT).show();
                    return;
                }  else if (tvPw.getText().toString().equals("")){
                    Toast.makeText(RegisterNextActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else if (tvRepw.getText().toString().equals("")) {
                    Toast.makeText(RegisterNextActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                } else if (!tvPw.getText().toString().equals(tvRepw.getText().toString())) {
                    Toast.makeText(RegisterNextActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    register();
                }
            }
        });
    }

    private void initToolbar() {
        toolbar.setTitle("");
        tbTitle.setText("注册信息");
        setActionBar(toolbar);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void register() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", getIntent().getStringExtra("phone"));
        map.put("name", tvNickname.getText().toString());
        map.put("password", tvPw.getText().toString());
        PostUtil.newInstance().sendPost(this, registerUrl, map);
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
        try {
            JSONObject json = new JSONObject(response);
            if (json.getBoolean("status")) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                salt = json.getString("salt");
                editor.putInt("id", json.getInt("id"));
                editor.putString("salt", salt);
                editor.putString("password", LoginActivity.Md5(tvPw.getText().toString() + salt));
                Intent it = new Intent(RegisterNextActivity.this, MainActivity.class);
                startActivity(it);
                RegisterNextActivity.this.finish();
            } else {
                ToastUtil.printToast(this, "该手机号已注册");
                return;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context myContext() {
        return this;
    }
}
