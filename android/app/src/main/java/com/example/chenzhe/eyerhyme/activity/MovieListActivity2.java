package com.example.chenzhe.eyerhyme.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

public class MovieListActivity2 extends MovieListActivity {


    @Override
    protected void initListview() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it;
                if (getIntent().getIntExtra("edit", -1) == 1) {
                    it = new Intent(MovieListActivity2.this, EditEventActivity.class);
                } else {
                    it = new Intent(MovieListActivity2.this, LaunchEventActivity.class);
                }
                it.putExtra("movie_id", movieItems.get(position).getMovie_id());
                it.putExtra("name", movieItems.get(position).getName());
                it.putExtra("type", 0);
                startActivity(it);
                finish();
            }
        });
    }
}
