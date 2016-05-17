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
import com.example.chenzhe.eyerhyme.model.ActivityItem;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenzhe on 2016/5/10.
 */
public class ActivitiesListAdapter extends BaseAdapter {
    private ArrayList<ActivityItem> items;
    private LayoutInflater inflater;
    private Context context;
    private SharedPreferences sharedPreferences;

    public ActivitiesListAdapter(ArrayList<ActivityItem> items, Context context) {
        this.items = items;
        this.context = context;
        sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_list_item, null);
        }
        final View view = convertView;
        final ActivityItem item = items.get(position);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvName.setText(item.getName());
        viewHolder.tvFilm.setText(item.getMovie_name());
        viewHolder.tvJoinNum.setText(item.getJoin_num()+"人/"+item.getJoin_bound()+"人");
        viewHolder.tvLoc.setText(item.getPlace());
        String date_time = item.getDate_time();

        viewHolder.tvTime.setText(date_time.substring(0, date_time.length()-2));
        PostUtil.newInstance().imageGET(viewHolder.ivHead, "user", item.getUser_id());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.iv_head)
        RoundedImageView ivHead;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.iv_film)
        ImageView ivFilm;
        @Bind(R.id.tv_film)
        TextView tvFilm;
        @Bind(R.id.iv_loc)
        ImageView ivLoc;
        @Bind(R.id.tv_loc)
        TextView tvLoc;
        @Bind(R.id.iv_time)
        ImageView ivTime;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_state)
        TextView tvState;
        @Bind(R.id.tv_join_num)
        TextView tvJoinNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
