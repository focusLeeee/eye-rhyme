package com.example.chenzhe.eyerhyme.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.chenzhe.eyerhyme.util.ToastUtil;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.techery.properratingbar.ProperRatingBar;

public class WritingMovieCommentActivity extends AppCompatActivity implements viewController {

    @Bind(R.id.tb_title)
    TextView tbTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rating_bar)
    ProperRatingBar ratingBar;
    @Bind(R.id.et_comment)
    EditText etComment;
    @Bind(R.id.bn_commit)
    Button bnCommit;
    private SharedPreferences sharedPreferences;
    private String userCommentURL = "/user/give_grades";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_movie_comment);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void init() {
        initToolbar();
        initViews();
    }

    private void initViews() {
        bnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etComment.getText().length() < 15) {
                    ToastUtil.printToast(WritingMovieCommentActivity.this, "影评不得少于15字");
                    return;
                }
                writeComment();
            }
        });
    }

    private void initToolbar() {
        tbTitle.setText("写影评");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void writeComment() {
        HashMap<String , Object> map = new HashMap<>();
        map.put("user_id", sharedPreferences.getInt("user_id",-1));
        map.put("type",1);
        map.put("object_id", getIntent().getIntExtra("movie_id",-1));
        map.put("value", ratingBar.getRating());
        map.put("content", etComment.getText().toString());
        PostUtil.newInstance().sendPost(this, userCommentURL, map);
    }

    @Override
    public void updateView(String url, String response) {
        if (response == null) {
            ToastUtil.printToast(this, "network fail");
            return;
        }
        if (url.equals(userCommentURL)) {
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
