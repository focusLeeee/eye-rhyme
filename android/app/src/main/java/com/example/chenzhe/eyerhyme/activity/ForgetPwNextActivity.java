package com.example.chenzhe.eyerhyme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ForgetPwNextActivity extends AppCompatActivity {

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
        setActionBar(toolbar);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
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
                    resetPw();
                }
            }
        });
    }

    private void resetPw() {
        Intent it = new Intent(ForgetPwNextActivity.this, LoginActivity.class);
        startActivity(it);
        ForgetPwNextActivity.this.finish();
    }
}
