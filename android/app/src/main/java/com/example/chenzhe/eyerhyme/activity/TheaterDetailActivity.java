package com.example.chenzhe.eyerhyme.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.adapter.TheaterCommentAdapter;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.model.TheaterCommentItem;
import com.example.chenzhe.eyerhyme.model.TheaterItem;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.chenzhe.eyerhyme.view.MyListView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TheaterDetailActivity extends Activity implements viewController {
    private final String theater_detail_url = "/theater/get_theaer_detail";
    private final String theater_comment_url = "/theater/get_grades";
    private String date_time = "";          // 最后一条评论的时间
    private static int theater_id;          // 影院 id
    private static boolean change = false;  // 是否点赞
    private PostUtil postUtil;
    private TheaterItem item;               // 从上一个页面传递的反序列化对象
    private ImageButton back;
    private ImageView like;
    private TextView name;
    private RelativeLayout location;        // 点击跳转到导航页面
    private RelativeLayout phone_call;      // 点击跳转到拨号页面
    private ImageView[] stars = new ImageView[5];
    private TextView score;                 // 影院的评分
    private TextView assessNum;             // 对影院评分的人数
    private RelativeLayout addComment;      // 对影院评分

    private MyListView lv;                                // ListView
    private List<TheaterCommentItem> theaterCommentList;  // 评论链表
    private TheaterCommentAdapter commentAdapter;         // 评论适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theater_detail);
        postUtil = PostUtil.newInstance();

        // 从后台获取影院的详细信息
        getTheaterInformation();

        // 从上一个页面获取的一个序列化TheaterItem对象
        item = (TheaterItem)getIntent().getSerializableExtra("theater");

        lv = (MyListView)findViewById(R.id.theater_comment_list);
        theaterCommentList = new ArrayList<TheaterCommentItem>();

        // 测试数据
        theaterCommentList.add(new TheaterCommentItem(0, "昵称1", 6.0, "评论内容1", "5月6日 10:10"));
        theaterCommentList.add(new TheaterCommentItem(0, "昵称2", 9.0, "评论内容2", "5月6日 10:09"));
        commentAdapter = new TheaterCommentAdapter(TheaterDetailActivity.this, R.layout.theater_comment_item, theaterCommentList);
        lv.setAdapter(commentAdapter);

        bindView();
        setView();

        // 修改theater id，测试值为0
        theater_id = 0;
    }
    private void bindView() {
        back = (ImageButton)findViewById(R.id.back_to_theater_list);
        like = (ImageView)findViewById(R.id.like_this_theater);
        name = (TextView)findViewById(R.id.theater_name_in_detail_page);
        location = (RelativeLayout)findViewById(R.id.address_in_detail_page);
        phone_call = (RelativeLayout)findViewById(R.id.phone_call_in_detail_page);
        stars[0] = (ImageView)findViewById(R.id.star_1);
        stars[1] = (ImageView)findViewById(R.id.star_2);
        stars[2] = (ImageView)findViewById(R.id.star_3);
        stars[3] = (ImageView)findViewById(R.id.star_4);
        stars[4] = (ImageView)findViewById(R.id.star_5);
        score = (TextView)findViewById(R.id.theater_score_in_detail_page);
        assessNum = (TextView)findViewById(R.id.assess_number);
    }
    private void setView() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!change) {
                    like.setImageResource(R.mipmap.like);
                    change = true;
                } else {
                    like.setImageResource(R.mipmap.not_like);
                    change = false;
                }
            }
        });

        name.setText(item.getName());

        int grade = ((int)item.getGrade() + 1) / 2;
        for (int i = 0; i < grade; i++)
            stars[i].setImageResource(R.mipmap.star);

        double f = (double)item.getGrade();
        BigDecimal b = new BigDecimal(f);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        score.setText(Double.toString(f1));

        assessNum.setText("44人评价");

        // 跳转到导航页面
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        //跳转到拨号页面
        phone_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_number;
                TextView pn = (TextView) findViewById(R.id.phone_number_in_detail_page);
                phone_number = pn.getText().toString();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("phone_number", phone_number);
                /*
                intent.setClass(TheaterDetailActivity.this, SeatActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                */
            }
        });
    }


    private void getTheaterInformation() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("theater_id", theater_id+"");
        postUtil.sendPost(this, theater_detail_url, map);
    }

    private void getTheaterComment() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("theater_id", theater_id);
        map.put("date_time", date_time);
        postUtil.sendPost(this, theater_detail_url, map);
    }

    @Override
    public void updateView(String url, String response) {
        if (response == null) {
            Toast.makeText(TheaterDetailActivity.this, "network fail", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            if (url.equals(theater_detail_url)) {
                Log.i("response", response);
                JSONObject json = new JSONObject(response);
                if (json.getBoolean("status")) {
                    JSONObject detailItem = json.getJSONObject("result");

                    // 更新页面数据
                }
            } else if (url.equals(theater_comment_url)) {
                Log.i("response", response);
                JSONObject json = new JSONObject(response);
                if (json.getBoolean("status")) {
                    JSONObject detailItem = json.getJSONObject("result");

                    // 更新页面数据
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public Context myContext() {
        return this;
    }
}
