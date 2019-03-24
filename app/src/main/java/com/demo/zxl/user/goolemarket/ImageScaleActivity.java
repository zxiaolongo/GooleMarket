package com.demo.zxl.user.goolemarket;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demo.zxl.user.goolemarket.utils.Constant;
import com.demo.zxl.user.goolemarket.utils.UIUitls;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;


/**
 * Created by HASEE.
 */
public class ImageScaleActivity extends Activity {
    @BindView(R.id.vp)
    ViewPager vp;
    private ArrayList<String> screenList;
    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_scale);
        ButterKnife.bind(this);

        screenList = (ArrayList<String>) getIntent().getSerializableExtra("screenList");
        index = getIntent().getIntExtra("index", -1);
        //给vp设置数据
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        vp.setAdapter(myPagerAdapter);
        //展示点中图片的大图
        vp.setCurrentItem(index);
    }
    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return screenList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            /*ImageView imageView = new ImageView(UIUtils.getContext());*/
            PhotoView photoView = new PhotoView(UIUitls.getContext());
            photoView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(UIUitls.getContext())
                    .load(Constant.IMG+ screenList.get(position)).into(photoView);
            container.addView(photoView);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
