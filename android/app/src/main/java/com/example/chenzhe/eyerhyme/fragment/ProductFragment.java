package com.example.chenzhe.eyerhyme.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.activity.SeatActivity;
import com.example.chenzhe.eyerhyme.adapter.ProductListAdapter;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.model.ProductResponse;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.chenzhe.eyerhyme.util.ToastUtil;
import com.example.chenzhe.eyerhyme.view.MyListView;
import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment implements viewController {


    @Bind(R.id.my_list_view)
    ListView myListView;
    private int theater_id;
    private String date;
    private int movie_id;
    private ProductResponse productResponse;
    private String getProductURL = "/movie_product/get_products";

    public ProductFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        theater_id = getArguments().getInt("theater_id");
        date = getArguments().getString("date");
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getActivity(), SeatActivity.class);
                int product_id = productResponse.result.get(position).getProduct_id();
                it.putExtra("product_id", product_id);
                startActivity(it);
            }
        });

        movie_id = getArguments().getInt("movie_id");
        if(movie_id != -1) getProducts(movie_id);
    }

    public void getProducts(int movie_id) {
        HashMap<String , Object> map = new HashMap<>();
//        map.put("movie_id", movie_id);
        map.put("movie_id", 1);
        map.put("theater_id", theater_id);
        map.put("date", date);
        PostUtil.newInstance().sendPost(this, getProductURL, map);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void updateView(String url, String response) {
        if (response == null) {
            ToastUtil.printToast(getActivity(), "network fail");
            return;
        }
        if (url.equals(getProductURL)) {
            productResponse = new Gson().fromJson(response, ProductResponse.class);
            if (productResponse.status) {
                ProductListAdapter adapter = new ProductListAdapter(productResponse.result, getActivity());
                myListView.setAdapter(adapter);
            } else {
                ToastUtil.printToast(getActivity(), "获取场次失败");
            }
        }
    }

    @Override
    public Context myContext() {
        return getActivity();
    }
}
