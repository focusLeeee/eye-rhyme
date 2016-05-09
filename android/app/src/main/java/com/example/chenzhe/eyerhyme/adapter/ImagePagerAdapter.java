package com.example.chenzhe.eyerhyme.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.chenzhe.eyerhyme.R;

import java.util.ArrayList;

/**
 * Created by chenzhe on 2016/5/4.
 */
public class ImagePagerAdapter extends PagerAdapter {

    private ArrayList<View> views;

    public ImagePagerAdapter(ArrayList<View> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    /**
     * 关联key 与 obj是否相等，即是否为同一个对象
     */
    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj; // key
    }

    /**
     * 销毁当前page的相隔2个及2个以上的item时调用
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object); // 将view 类型 的object熊容器中移除,根据key
    }

    /**
     * 当前的page的前一页和后一页也会被调用，如果还没有调用或者已经调用了destroyItem
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        System.out.println("pos:" + position);
        View view = views.get(position);
        // 如果访问网络下载图片，此处可以进行异步加载
        ImageView img = (ImageView) view.findViewById(R.id.icon);
        img.setImageResource(R.mipmap.head);
        container.addView(view);
        return views.get(position); // 返回该view对象，作为key
    }
}
