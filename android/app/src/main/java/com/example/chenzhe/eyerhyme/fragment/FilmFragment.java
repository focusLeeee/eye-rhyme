package com.example.chenzhe.eyerhyme.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.chenzhe.eyerhyme.R;
import com.example.chenzhe.eyerhyme.customInterface.viewController;
import com.example.chenzhe.eyerhyme.model.TheatersNearbyResponse;
import com.example.chenzhe.eyerhyme.model.getMoviesResponse;
import com.example.chenzhe.eyerhyme.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilmFragment extends Fragment implements viewController {


    @Bind(R.id.viewFlipper)
    ViewFlipper viewFlipper;
    @Bind(R.id.iv_post1)
    ImageView ivPost1;
    @Bind(R.id.tv_post1)
    TextView tvPost1;
    @Bind(R.id.iv_post2)
    ImageView ivPost2;
    @Bind(R.id.tv_post2)
    TextView tvPost2;
    @Bind(R.id.iv_post3)
    ImageView ivPost3;
    @Bind(R.id.tv_post3)
    TextView tvPost3;
    @Bind(R.id.iv_post4)
    ImageView ivPost4;
    @Bind(R.id.tv_post4)
    TextView tvPost4;
    @Bind(R.id.iv_post5)
    ImageView ivPost5;
    @Bind(R.id.tv_post5)
    TextView tvPost5;
    @Bind(R.id.iv_post6)
    ImageView ivPost6;
    @Bind(R.id.tv_post6)
    TextView tvPost6;
    @Bind(R.id.iv_arrow)
    ImageView ivArrow;
    @Bind(R.id.listview)
    ListView listview;
    private Animation slideLeftIn;
    private Animation slideLeftOut;
    private Animation slideRightIn;
    private Animation slideRightOut;
    private String getTheatersNearby = "/theater/get_theaters_nearby";
    private String getMovies = "/movie/get_movies";
    private TheatersNearbyResponse theatersNearbyResponse;
    private getMoviesResponse moviesResponse;
    private ArrayList<TextView> filmNames;
    private ArrayList<ImageView> filmImages;

    public FilmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_film, container, false);
        ButterKnife.bind(this, view);
        slideLeftIn = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_left_in);
        slideLeftOut = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_left_out);
        slideRightIn = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_right_in);
        slideRightOut = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_right_out);
        init();
        return view;
    }

    private void init() {
        initViewFlipper();
    }

    private void initViewFlipper() {
        viewFlipper.setInAnimation(slideLeftIn);
        viewFlipper.setOutAnimation(slideLeftOut);
        viewFlipper.startFlipping();
    }

    private void initfilms() {
        filmImages.add(ivPost1);
        filmImages.add(ivPost2);
        filmImages.add(ivPost3);
        filmImages.add(ivPost4);
        filmImages.add(ivPost5);
        filmImages.add(ivPost6);

        filmNames.add(tvPost1);
        filmNames.add(tvPost2);
        filmNames.add(tvPost3);
        filmNames.add(tvPost4);
        filmNames.add(tvPost5);
        filmNames.add(tvPost6);


    }

    private void getFilms() {
        HashMap<String, Object> map = new HashMap<String, Object>();

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
        if (url.equals(getMovies)) {

        } else if (url.equals(getTheatersNearby)) {

        }
    }

    @Override
    public Context myContext() {
        return getActivity();
    }
}
