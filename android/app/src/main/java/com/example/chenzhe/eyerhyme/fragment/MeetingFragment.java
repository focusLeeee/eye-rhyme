package com.example.chenzhe.eyerhyme.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.activity.ActivityDetailActivity;
import com.example.chenzhe.eyerhyme.activity.MainActivity;
import com.example.chenzhe.eyerhyme.adapter.ActivitiesListAdapter;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.model.ActivityItem;
import com.example.chenzhe.eyerhyme.model.getActivitiesResponse;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.chenzhe.eyerhyme.util.ToastUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeetingFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate, viewController {


    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.refreshlayout)
    BGARefreshLayout refreshlayout;

    private String getActivitiesURL = "/movie_activity/get_activities";
    private ArrayList<ActivityItem> items;
    private ActivitiesListAdapter adapter;

    public MeetingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meeting, container, false);
        ButterKnife.bind(this, view);

        init();

//        items = null;
//        getActivities(-1);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        items = null;
        getActivities(-1);
    }

    private void init() {
        initRefreshLayout();
        initListener();
    }

    private void initListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(getActivity(), ActivityDetailActivity.class);
                it.putExtra("activity_id", items.get(position).getActivity_id());
                it.putExtra("date_time", items.get(position).getDate_time());
                startActivity(it);
            }
        });
    }

    private void initRefreshLayout() {
        refreshlayout.setDelegate(this);
        BGARefreshViewHolder viewHolder = new BGANormalRefreshViewHolder(getActivity(), true);
        refreshlayout.setRefreshViewHolder(viewHolder);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void getActivities(int activity_id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("longitude", MainActivity.longitude);
        map.put("latitude", MainActivity.latitude);
        if (activity_id != -1) {
            map.put("activity_id", activity_id);
        }
        PostUtil.newInstance().sendPost(this, getActivitiesURL, map);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        items = null;
        getActivities(-1);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (items == null) {
            getActivities(-1);
        } else {
            getActivities(items.get(items.size()-1).getActivity_id());
        }
        return true;
    }

    @Override
    public void updateView(String url, String response) {
        refreshlayout.endRefreshing();
        refreshlayout.endLoadingMore();
        if (response == null) {
            ToastUtil.printToast(getActivity(), "network fail");
            return;
        }
        if (url.equals(getActivitiesURL)) {
            getActivitiesResponse response1 = new Gson().fromJson(response, getActivitiesResponse.class);
            if (response1.status) {
                if (items == null) {
                    items = response1.result;
                    adapter = new ActivitiesListAdapter(items, getActivity());
                    listview.setAdapter(adapter);
                } else {
                    items.addAll(response1.result);
                    adapter.notifyDataSetChanged();
                }
            } else {
                ToastUtil.printToast(getActivity(), "获取约影失败");
            }
        }
    }
}
