package com.example.chenzhe.eyerhyme.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.model.MovieItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenzhe on 2016/5/3.
 */
public class MovieListAdapter extends BaseAdapter {
    private ArrayList<MovieItem> movieItems;
    private LayoutInflater inflater;
    private Context context;
    private SharedPreferences sharedPreferences;

    public MovieListAdapter(ArrayList<MovieItem> movieItems, Context context) {
        this.movieItems = movieItems;
        this.context = context;
        sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int position) {
        return movieItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.movie_list_item, null);
        }
        final View view = convertView;
        final MovieItem movieItem = movieItems.get(position);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvActor.setText(movieItem.getActors());
        viewHolder.tvDirector.setText(movieItem.getDirectors());
        viewHolder.tvGrade.setText(movieItem.getGrade()+"");
        viewHolder.tvName.setText(movieItem.getName());
        viewHolder.tvReleaseDate.setText(movieItem.getRelease_date());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_post)
        ImageView ivPost;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_director)
        TextView tvDirector;
        @Bind(R.id.tv_actor)
        TextView tvActor;
        @Bind(R.id.tv_release_date)
        TextView tvReleaseDate;
        @Bind(R.id.tv_grade)
        TextView tvGrade;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
