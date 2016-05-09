package com.example.chenzhe.eyerhyme.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.activity.MainActivity;
import com.example.chenzhe.eyerhyme.model.TheaterItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenzhe on 2016/5/3.
 */
public class TheatersNearbyAdapter extends BaseAdapter {
    private ArrayList<TheaterItem> theaterItems;
    private LayoutInflater inflater;
    private Context context;
    private SharedPreferences sharedPreferences;

    public TheatersNearbyAdapter(ArrayList<TheaterItem> theaterItems, Context context) {
        this.theaterItems = theaterItems;
        this.context = context;
        sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return theaterItems.size();
    }

    @Override
    public Object getItem(int position) {
        return theaterItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.theater_nearby_item, null);
        }
        final View view = convertView;
        final TheaterItem theaterItem = theaterItems.get(position);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvTheaterGrade.setText(theaterItem.getGrade()+"");
        viewHolder.tvTheaterLoc.setText(theaterItem.getLocation());
        viewHolder.tvTheaterName.setText(theaterItem.getName());
        viewHolder.tvTheaterPrice.setText("￥"+theaterItem.getLowest_price()+"起");
        LatLng st = new LatLng(MainActivity.latitude, MainActivity.longitude);
        LatLng ed = new LatLng(theaterItem.getLatitude(), theaterItem.getLongitude());
        float dis = AMapUtils.calculateLineDistance(st, ed)/1000;

        if (dis >= 1) {
            java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
            viewHolder.tvTheaterDistance.setText(df.format(dis) + "km");
        }
        else {
            java.text.DecimalFormat df = new java.text.DecimalFormat("#");
            dis *= 1000;
            viewHolder.tvTheaterDistance.setText(df.format(dis) + "m");
        }
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_theater_name)
        TextView tvTheaterName;
        @Bind(R.id.tv_theater_loc)
        TextView tvTheaterLoc;
        @Bind(R.id.tv_theater_price)
        TextView tvTheaterPrice;
        @Bind(R.id.tv_theater_grade)
        TextView tvTheaterGrade;
        @Bind(R.id.tv_theater_distance)
        TextView tvTheaterDistance;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
