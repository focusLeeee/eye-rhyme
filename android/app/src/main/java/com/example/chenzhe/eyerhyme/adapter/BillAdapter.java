package com.example.chenzhe.eyerhyme.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.greendao.dao.Bill;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenzhe on 2016/5/16.
 */
public class BillAdapter extends BaseAdapter {

    private List<Bill> bills;
    private LayoutInflater inflater;
    private Context context;
    private SharedPreferences sharedPreferences;

    public BillAdapter(List<Bill> bills, Context context) {
        this.bills = bills;
        this.context = context;
        sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bills.size();
    }

    @Override
    public Object getItem(int position) {
        return bills.get(position);
    }

    @Override
    public long getItemId(int position) {
        return bills.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.bill_item, null);
        }
        final View view = convertView;
        ViewHolder holder = new ViewHolder(view);
        final Bill bill = bills.get(position);
        holder.tvMovieName.setText(bill.getMovieName());
        holder.tvPrice.setText("¥" + bill.getPrice());
        holder.tvTheaterName.setText(bill.getTheaterName());
        holder.tvDate.setText("订单时间: "+bill.getDate());
        PostUtil.newInstance().imageGET(holder.ivHead, "movie", bill.getMovieId());
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.iv_head)
        RoundedImageView ivHead;
        @Bind(R.id.tv_movie_name)
        TextView tvMovieName;
        @Bind(R.id.tv_theater_name)
        TextView tvTheaterName;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.rl_bill)
        RelativeLayout rlBill;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
