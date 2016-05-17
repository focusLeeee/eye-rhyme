package com.example.chenzhe.eyerhyme.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.activity.MyBillActivity;
import com.example.chenzhe.eyerhyme.activity.UserEditActivity;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.model.UserDetail;
import com.example.chenzhe.eyerhyme.util.PostUtil;
import com.example.chenzhe.eyerhyme.util.ToastUtil;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements viewController {


    @Bind(R.id.iv_head)
    RoundedImageView ivHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.rl_user)
    RelativeLayout rlUser;
    @Bind(R.id.ll_payment)
    LinearLayout llPayment;
    @Bind(R.id.ll_activity)
    LinearLayout llActivity;
    @Bind(R.id.ll_comment)
    LinearLayout llComment;
    @Bind(R.id.ll_collect)
    LinearLayout llCollect;
    @Bind(R.id.ll_setting)
    LinearLayout llSetting;
    @Bind(R.id.ll_logout)
    LinearLayout llLogout;

    private SharedPreferences sharedPreferences;
    private String getUserDetailURL = "/user/get_user_detail";
    private UserDetail detail;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        getUserDetail();
        initListener();
    }

    private void initListener() {
        rlUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), UserEditActivity.class);
                it.putExtra("user_detail", detail);
                startActivity(it);
            }
        });

        llPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), MyBillActivity.class);
                startActivity(it);
            }
        });
    }

    private void getUserDetail() {
        HashMap<String , Object> map = new HashMap<>();
        map.put("user_id", sharedPreferences.getInt("user_id", -1));
        PostUtil.newInstance().sendPost(this, getUserDetailURL, map);
        PostUtil.newInstance().imageGET(ivHead, "user", sharedPreferences.getInt("user_id", -1));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void updateView(String url, String response) {
        if (response == null) {
            ToastUtil.printToast(getContext(), "network fail");
            return;
        }
        if (url.equals(getUserDetailURL)) {
            detail = new Gson().fromJson(response, UserDetail.class);
            if (detail.getStatus()) {
                tvName.setText(detail.getName());
                tvPhone.setText("账号: "+sharedPreferences.getString("phone", ""));

            } else {
                ToastUtil.printToast(getContext(), "获取用户信息失败");
            }
        }
    }
}
