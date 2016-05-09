package com.example.chenzhe.eyerhyme.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.model.ProductItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenzhe on 2016/5/5.
 */
public class ProductListAdapter extends BaseAdapter {
    private ArrayList<ProductItem> items;
    private LayoutInflater inflater;
    private Context context;
    private SharedPreferences sharedPreferences;

    public ProductListAdapter(ArrayList<ProductItem> items, Context context) {
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
            convertView = inflater.inflate(R.layout.product_item, null);
        }
        final View view = convertView;
        final ProductItem item = items.get(position);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tvHall.setText(item.getHall()+"号厅");
        viewHolder.tvOriginPrice.setText("$"+item.getPrice());
        viewHolder.tvOriginPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG| Paint.ANTI_ALIAS_FLAG);
        viewHolder.tvRealPrice.setText("$"+(item.getPrice()-item.getDiscount()));

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_start_time)
        TextView tvStartTime;
        @Bind(R.id.tv_type)
        TextView tvType;
        @Bind(R.id.tv_hall)
        TextView tvHall;
        @Bind(R.id.tv_real_price)
        TextView tvRealPrice;
        @Bind(R.id.tv_origin_price)
        TextView tvOriginPrice;
        @Bind(R.id.rl_product)
        RelativeLayout rlProduct;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
