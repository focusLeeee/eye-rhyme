package com.example.chenzhe.eyerhyme.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.model.MovieCommentItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.techery.properratingbar.ProperRatingBar;

/**
 * Created by chenzhe on 2016/5/4.
 */
public class CommentListAdapter extends BaseAdapter {
    private ArrayList<MovieCommentItem> commentItems;
    private LayoutInflater inflater;
    private Context context;
    private SharedPreferences sharedPreferences;

    public CommentListAdapter(ArrayList<MovieCommentItem> commentItems, Context context) {
        this.commentItems = commentItems;
        this.context = context;
        sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return commentItems.size();
    }

    @Override
    public Object getItem(int position) {
        return commentItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.comment_list_item, null);
        }
        final View view = convertView;
        final MovieCommentItem item = commentItems.get(position);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvContent.setText(item.getContent());
        viewHolder.tvDate.setText(item.getDate_time().substring(0, item.getDate_time().length()-2));
        viewHolder.tvName.setText(item.getName());
        viewHolder.ratingBar.setRating(item.getGrade());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.rating_bar)
        ProperRatingBar ratingBar;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
