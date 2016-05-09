package com.example.chenzhe.eyerhyme.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.customInterface.viewController;

import org.json.JSONObject;

public class SeatActivity extends Activity implements viewController {
    private Button[] seat = new Button[64]; // 64个座位
    private short[] status = new short[64]; // 座位状态：-1代表已售，0代表可选，1代表已选
    private ImageView back;                 // 返回上一页
    private TextView theater_name;          // Title展示的影院名
    private TextView movie_name;            // 电影名字
    private TextView movie_time;            // 电影时间
    private TextView movie_description;     // 电影简要描述
    private Button submit;                  // 选定座位，提交
    private static int count = 0;           // 已选座位数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        bindView();
        setView();
    }

    private void setView() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*
        theater_name.setText();
        movie_name.setText();
        movie_time.setText();
        movie_description.setText();
        */

        for (int i = 0; i < 64; i++) {
            status[i] = 0;
            final int index = i;
            seat[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (status[index] == 0) {
                        status[index] = 1;
                        seat[index].setBackgroundColor(Color.rgb(30, 144, 255));
                        count++;
                        submit.setBackgroundColor(Color.rgb(30, 144, 255));
                    } else if (status[index] == 1) {
                        status[index] = 0;
                        seat[index].setBackgroundColor(Color.rgb(255, 255, 255));
                        count--;
                        if (count == 0)
                            submit.setBackgroundColor(Color.rgb(112, 128, 144));
                    }
                }
            });
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 0) {
                    // 提交
                }
            }
        });
    }
    private void getMovieInfo() {

    }
    @Override
    public void updateView(String url, String response) {
        if (response == null) {
            Toast.makeText(SeatActivity.this, "network fail", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            // code
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public Context myContext() {
        return this;
    }

    private void bindView() {
        back = (ImageView)findViewById(R.id.back_to_movie);
        theater_name = (TextView)findViewById(R.id.theater_name_seat_page);
        movie_name = (TextView)findViewById(R.id.movie_name_in_seat_page);
        movie_time = (TextView)findViewById(R.id.movie_time_in_seat_page);
        movie_description = (TextView)findViewById(R.id.movie_profile_in_seat_page);
        submit = (Button)findViewById(R.id.submit_seat);
        seat[0] = (Button)findViewById(R.id.seat1);
        seat[1] = (Button)findViewById(R.id.seat2);
        seat[2] = (Button)findViewById(R.id.seat3);
        seat[3] = (Button)findViewById(R.id.seat4);
        seat[4] = (Button)findViewById(R.id.seat5);
        seat[5] = (Button)findViewById(R.id.seat6);
        seat[6] = (Button)findViewById(R.id.seat7);
        seat[7] = (Button)findViewById(R.id.seat8);
        seat[8] = (Button)findViewById(R.id.seat9);
        seat[9] = (Button)findViewById(R.id.seat10);
        seat[10] = (Button)findViewById(R.id.seat11);
        seat[11] = (Button)findViewById(R.id.seat12);
        seat[12] = (Button)findViewById(R.id.seat13);
        seat[13] = (Button)findViewById(R.id.seat14);
        seat[14] = (Button)findViewById(R.id.seat15);
        seat[15] = (Button)findViewById(R.id.seat16);
        seat[16] = (Button)findViewById(R.id.seat17);
        seat[17] = (Button)findViewById(R.id.seat18);
        seat[18] = (Button)findViewById(R.id.seat19);
        seat[19] = (Button)findViewById(R.id.seat20);
        seat[20] = (Button)findViewById(R.id.seat21);
        seat[21] = (Button)findViewById(R.id.seat22);
        seat[22] = (Button)findViewById(R.id.seat23);
        seat[23] = (Button)findViewById(R.id.seat24);
        seat[24] = (Button)findViewById(R.id.seat25);
        seat[25] = (Button)findViewById(R.id.seat26);
        seat[26] = (Button)findViewById(R.id.seat27);
        seat[27] = (Button)findViewById(R.id.seat28);
        seat[28] = (Button)findViewById(R.id.seat29);
        seat[29] = (Button)findViewById(R.id.seat30);
        seat[30] = (Button)findViewById(R.id.seat31);
        seat[31] = (Button)findViewById(R.id.seat32);
        seat[32] = (Button)findViewById(R.id.seat33);
        seat[33] = (Button)findViewById(R.id.seat34);
        seat[34] = (Button)findViewById(R.id.seat35);
        seat[35] = (Button)findViewById(R.id.seat36);
        seat[36] = (Button)findViewById(R.id.seat37);
        seat[37] = (Button)findViewById(R.id.seat38);
        seat[38] = (Button)findViewById(R.id.seat39);
        seat[39] = (Button)findViewById(R.id.seat40);
        seat[40] = (Button)findViewById(R.id.seat41);
        seat[41] = (Button)findViewById(R.id.seat42);
        seat[42] = (Button)findViewById(R.id.seat43);
        seat[43] = (Button)findViewById(R.id.seat44);
        seat[44] = (Button)findViewById(R.id.seat45);
        seat[45] = (Button)findViewById(R.id.seat46);
        seat[46] = (Button)findViewById(R.id.seat47);
        seat[47] = (Button)findViewById(R.id.seat48);
        seat[48] = (Button)findViewById(R.id.seat49);
        seat[49] = (Button)findViewById(R.id.seat50);
        seat[50] = (Button)findViewById(R.id.seat51);
        seat[51] = (Button)findViewById(R.id.seat52);
        seat[52] = (Button)findViewById(R.id.seat53);
        seat[53] = (Button)findViewById(R.id.seat54);
        seat[54] = (Button)findViewById(R.id.seat55);
        seat[55] = (Button)findViewById(R.id.seat56);
        seat[56] = (Button)findViewById(R.id.seat57);
        seat[57] = (Button)findViewById(R.id.seat58);
        seat[58] = (Button)findViewById(R.id.seat59);
        seat[59] = (Button)findViewById(R.id.seat60);
        seat[60] = (Button)findViewById(R.id.seat61);
        seat[61] = (Button)findViewById(R.id.seat62);
        seat[62] = (Button)findViewById(R.id.seat63);
        seat[63] = (Button)findViewById(R.id.seat64);
    }
}
