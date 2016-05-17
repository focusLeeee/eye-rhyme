package com.example.chenzhe.eyerhyme.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.activity.UserDetailActivity;
import com.example.chenzhe.eyerhyme.model.UserIdName;

public class TextClickableSpan extends ClickableSpan {
    private Context context;
    private UserIdName user;

    public TextClickableSpan(Context context, UserIdName id) {
        this.context = context;
        this.user = id;
    }

    @Override
    public void onClick(View widget) {
        // do sth.

        Intent it = new Intent(context, UserDetailActivity.class);
        it.putExtra("user_id", user.getUser_id());
        context.startActivity(it);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(context.getResources().getColor(R.color.blue));
        ds.setUnderlineText(false);
    }
}
