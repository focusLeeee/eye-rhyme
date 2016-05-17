package com.example.chenzhe.eyerhyme.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.model.ActivityDetailResponse;
import com.example.chenzhe.eyerhyme.model.UserIdName;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.chenzhe.eyerhyme.util.ToastUtil;
import com.example.chenzhe.eyerhyme.view.TextClickableSpan;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityDetailActivity extends AppCompatActivity implements viewController {

    @Bind(R.id.tb_title)
    TextView tbTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_head)
    RoundedImageView ivHead;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.iv_sex)
    ImageView ivSex;
    @Bind(R.id.tv_level)
    TextView tvLevel;
    @Bind(R.id.rl_user)
    RelativeLayout rlUser;
    @Bind(R.id.tv_film)
    TextView tvFilm;
    @Bind(R.id.ll_film)
    LinearLayout llFilm;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.ll_time)
    LinearLayout llTime;
    @Bind(R.id.tv_loc)
    TextView tvLoc;
    @Bind(R.id.ll_loc)
    LinearLayout llLoc;
    @Bind(R.id.tv_join_num)
    TextView tvJoinNum;
    @Bind(R.id.tv_join_name)
    TextView tvJoinName;
    @Bind(R.id.bn_edit)
    Button bnEdit;
    @Bind(R.id.bn_cancel)
    Button bnCancel;
    @Bind(R.id.bn_join)
    Button bnJoin;
    @Bind(R.id.tv_content)
    TextView tvContent;

    private String ActivityDetailURL = "/movie_activity/get_activity_detail";
    private String CancelActivityURL = "/movie_activity/update_activity";
    private String operateActivityURL = "/movie_activity/operate_activity";
    private SharedPreferences sharedPreferences;
    private int user_id;
    private ActivityDetailResponse Response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_detail);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        user_id = sharedPreferences.getInt("user_id", -1);
        init();
    }

    private void init() {
        initToolbar();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    private void initViews() {
        getDetail();
        rlUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ActivityDetailActivity.this, UserDetailActivity.class);
                it.putExtra("user_id", Response.result.launcher_detail.getUser_id());
                startActivity(it);
            }
        });
    }

    private void initToolbar() {

        toolbar.setTitle("");
        tbTitle.setText("约影详情");
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

    private void cancelActivity() {
        HashMap<String , Object> map = new HashMap<>();
        map.put("activity_id", getIntent().getIntExtra("activity_id", -1));
        map.put("user_id", user_id);
        map.put("state", 2);
        PostUtil.newInstance().sendPost(this, CancelActivityURL, map);
    }

    private void getDetail() {
        int activity_id = getIntent().getIntExtra("activity_id", -1);

        HashMap<String, Object> map = new HashMap<>();
        map.put("activity_id", activity_id);
        PostUtil.newInstance().sendPost(this, ActivityDetailURL, map);
    }

    private void operateActivity(int type) {
        HashMap<String , Object> map = new HashMap<>();
        map.put("type",type);
        map.put("activity_id", getIntent().getIntExtra("activity_id", -1));
        map.put("user_id", user_id);
        PostUtil.newInstance().sendPost(this, operateActivityURL, map);
    }

    private void initTextSpan(ArrayList<UserIdName> users) {
        if (users.size() == 0) {
            tvJoinName.setText("无人参与");
        } else {
            String s = users.get(0).getName();
            ArrayList<SpannableString> list = new ArrayList<SpannableString>();
            SpannableString string = new SpannableString(s);
            string.setSpan(new TextClickableSpan(this, users.get(0)), 0, s.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            list.add(string);
            for (int i = 1; i < users.size(); i++) {
                s = "," + users.get(i).getName();
                string = new SpannableString(s);
                string.setSpan(new TextClickableSpan(this, users.get(i)), 0, s.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                list.add(string);
            }
            s = "";

            s += "\t" + users.size() + "人参与";
            Spanned replyText = Html.fromHtml("<font color=" + getResources().getColor(R.color.black) + ">" + s + "</font>");
            SpannableString[] temp = new SpannableString[list.size()];
            Spanned richText = (Spanned) TextUtils.concat(list.toArray(temp));
            richText = (Spanned) TextUtils.concat(richText, replyText);
            tvJoinName.setText(richText);
            tvJoinName.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    @Override
    public void updateView(String url, String response) {
        if (response == null) {
            ToastUtil.printToast(this, "network fail");
            return;
        }
        if (url.equals(ActivityDetailURL)) {
            Response = new Gson().fromJson(response, ActivityDetailResponse.class);
            if (Response.status) {
                final ActivityDetailResponse.ActivityDetail detail = Response.result;
                tvFilm.setText(detail.movie_detail.getMovie_name());

                String date = getIntent().getStringExtra("date_time");
                date = date.substring(0, date.length()-2);
                tvTime.setText(date);
                detail.activity_detail.setDate_time(date);
                detail.activity_detail.setActivity_id(getIntent().getIntExtra("activity_id", -1));

                tvJoinNum.setText(detail.activity_detail.getJoin_num() + "人/" + detail.activity_detail.getJoin_bound() + "人");
                tvLevel.setText("lv." + (int)detail.launcher_detail.getRank());
                tvLoc.setText(detail.activity_detail.getPlace());
                tvContent.setText(detail.activity_detail.getContent());
                tvUserName.setText(detail.launcher_detail.getName());
                initTextSpan(detail.join_detail);

                if (detail.launcher_detail.getGender() == 1) {
                    ivSex.setImageResource(R.mipmap.female);
                }


                PostUtil.newInstance().imageGET(ivHead, "user", detail.launcher_detail.getUser_id());

                if (user_id == detail.launcher_detail.getUser_id()) {
                    bnJoin.setVisibility(View.GONE);
                    bnEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(ActivityDetailActivity.this, EditEventActivity.class);
                            it.putExtra("activity_detail", detail.activity_detail);
                            it.putExtra("movie_detail", detail.movie_detail);
                            startActivity(it);
                        }
                    });

                    bnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDetailActivity.this);
                            builder.setTitle("撤销活动");
                            builder.setMessage("确定撤销活动吗？");

                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    cancelActivity();
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        }
                    });
                } else if (detail.IdisJoin(user_id)) {
                    bnCancel.setVisibility(View.GONE);
                    bnEdit.setVisibility(View.GONE);
                    bnJoin.setText("退出约影");
                    bnJoin.setBackgroundResource(R.drawable.button_selector);
                    bnJoin.setTextColor(getResources().getColor(R.color.black));
                    bnJoin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            operateActivity(0);
                        }
                    });
                } else {
                    bnCancel.setVisibility(View.GONE);
                    bnEdit.setVisibility(View.GONE);
                    bnJoin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            operateActivity(1);
                        }
                    });
                }
            } else {
                ToastUtil.printToast(this, "获取详情失败");
            }
        } else if (url.equals(CancelActivityURL)) {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")) {
                    finish();
                } else {
                    ToastUtil.printToast(this, "撤销失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (url.equals(operateActivityURL)) {
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")) {
                    finish();
                } else {
                    ToastUtil.printToast(this, "操作失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
