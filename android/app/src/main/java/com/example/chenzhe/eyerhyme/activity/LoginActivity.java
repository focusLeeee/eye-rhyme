package com.example.chenzhe.eyerhyme.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.google.gson.JsonObject;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements viewController {


    @Bind(R.id.tb_title)
    TextView tbTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_head)
    RoundedImageView ivHead;
    @Bind(R.id.tv_username)
    EditText tvUsername;
    @Bind(R.id.tv_password)
    EditText tvPassword;
    @Bind(R.id.bn_signin)
    Button bnSignin;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.tv_forgetpw)
    TextView tvForgetpw;

    private SharedPreferences sharedPreferences;
    private PostUtil postUtil;
    private String salt;
    private String password;
    private static String getSaltUrl = "/account/get_salt";
    private static String loginUrl = "/account/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
    }

    private void init() {
        initToolbar();
        initButton();
        postUtil = PostUtil.newInstance();
    }

    private void initToolbar() {
        toolbar.setTitle("");
        tbTitle.setText("登录");
        setActionBar(toolbar);
        getActionBar().setHomeButtonEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void initButton() {
        bnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvUsername.getText().toString().equals("")) {
                    ToastUtil.printToast(LoginActivity.this, "用户名不能为空");
                    return;
                }
                if (tvPassword.getText().toString().equals("")) {
                    ToastUtil.printToast(LoginActivity.this, "密码不能为空");
                    return;
                }

                getSalt();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(it);
            }
        });
        tvForgetpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, ForgetPwActivity.class);
                startActivity(it);
            }
        });
    }

    private void getSalt() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("phone", tvUsername.getText().toString());
        bnSignin.setText("正在登录....");
        postUtil.sendPost(this, getSaltUrl, map);
    }

    private void Login() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("phone", tvUsername.getText().toString());
        password = Md5(tvPassword.getText().toString() + salt);
        map.put("password", password);
        postUtil.sendPost(this, loginUrl, map);
    }

    public static String Md5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //System.out.println("result: " + buf.toString());//32位的加密
            return buf.toString();
            //System.out.println("result: " + buf.toString().substring(8,24));//16位的加密
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "error";
    }

    @Override
    public void updateView(String url, String response) {
        if (response == null) {
            Toast.makeText(LoginActivity.this, "network fail", Toast.LENGTH_SHORT).show();
            bnSignin.setText("登录");
            return;
        }
        if (url.equals(getSaltUrl)) {
            try {
                JSONObject json = new JSONObject(response);
                if (json.getBoolean("status")) {
                    salt = json.getString("salt");
                    Login();
                } else {
                    Toast.makeText(LoginActivity.this, "账号尚未注册", Toast.LENGTH_SHORT).show();
                    bnSignin.setText("登录");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (url.equals(loginUrl)) {
            try {
                JSONObject json = new JSONObject(response);
                if (json.getBoolean("status")) {
                    int id = json.getInt("id");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("id", id);
                    editor.putString("salt", salt);
                    editor.putString("password", password);
                    editor.commit();
                    Intent it = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(it);
                } else {
                    Toast.makeText(LoginActivity.this, "账号尚未注册或者密码错误", Toast.LENGTH_SHORT).show();
                    bnSignin.setText("登录");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Context myContext() {
        return this;
    }
}
