package com.example.chenzhe.eyerhyme.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.chenzhe.eyerhyme.util.SMSUtil;
import com.example.chenzhe.eyerhyme.util.ToastUtil;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends AppCompatActivity implements viewController {

    @Bind(R.id.tb_title)
    TextView tbTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_phone)
    EditText tvPhone;
    @Bind(R.id.bn_verify)
    Button bnVerify;
    @Bind(R.id.tv_verify)
    EditText tvVerify;
    @Bind(R.id.bn_next)
    Button bnNext;

    private static String chechPhoneUrl = "/account/check_validty";
    private boolean checking;
    private PostUtil postUtil;
    private EventHandler eventHandler;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                int time = msg.arg1;
                if (time == 0) {
                    bnVerify.setClickable(true);
                    bnVerify.setText("获取验证码");
                    bnVerify.setBackgroundResource(R.drawable.button_selector_2);
                } else {
                    bnVerify.setClickable(false);
                    bnVerify.setBackgroundResource(R.drawable.button_invalid);
                    bnVerify.setText(time + "秒重发");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initToolbar();
        initListener();
        initSMS();
        checking = false;
        postUtil = PostUtil.newInstance();
    }

    private void initSMS() {
        SMSSDK.initSDK(this, SMSUtil.smskey, SMSUtil.smssecret);
        eventHandler = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        Log.i("sms", "verify success");
                        Intent it = new Intent(RegisterActivity.this, RegisterNextActivity.class);
                        it.putExtra("phone", tvPhone.getText().toString());
                        startActivity(it);
                        RegisterActivity.this.finish();
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Log.i("sms", "sending verify");

                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    try {
                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
                        int status = object.optInt("status");//错误代码
                        if (status > 0 && !TextUtils.isEmpty(des)) {
                            Toast.makeText(RegisterActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        //do something
                    }
                }
            }

        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    private void initListener() {
        bnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvPhone.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (tvPhone.getText().toString().length() !=11) {
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    checkPhoneNum();
                    counting();
                }
            }
        });

        bnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvPhone.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (tvPhone.getText().toString().length() !=11) {
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (tvVerify.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                } else {
                    SMSSDK.submitVerificationCode("86", tvPhone.getText().toString(), tvVerify.getText().toString());

                }
            }
        });
    }

    private void initToolbar() {

        toolbar.setTitle("");
        tbTitle.setText("注册");
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

    private void checkPhoneNum() {
        checking = true;
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("phone", tvPhone.getText().toString());
        postUtil.sendPost(this, chechPhoneUrl, map);
    }

    private void counting() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 60;
                for (int i = count; i >= 0; i--) {
                    if (!checking) i = 0;
                    Message message = new Message();
                    message.what = 0;
                    message.arg1 = i;
                    handler.sendMessage(message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    @Override
    public void updateView(String url, String response) {
        if (response == null) {
            ToastUtil.printToast(RegisterActivity.this, "network fail");
            checking = false;
            return;
        }
        if (url.equals(chechPhoneUrl)) {
            try {
                JSONObject json = new JSONObject(response);
                if (json.getBoolean("status")) {
                    int state = json.getInt("state");
                    if (state == 1) {
                        ToastUtil.printToast(this, "该账号已存在");
                        checking = false;
                    } else {
                        SMSSDK.getVerificationCode("86", tvPhone.getText().toString());
                    }
                } else {
                    ToastUtil.printToast(this, "操作失败");
                    checking = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
