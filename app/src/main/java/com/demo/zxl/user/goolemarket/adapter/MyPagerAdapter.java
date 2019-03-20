package com.demo.zxl.user.goolemarket.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demo.zxl.user.goolemarket.R;
import com.demo.zxl.user.goolemarket.utils.Constant;
import com.demo.zxl.user.goolemarket.utils.UIUitls;

import java.util.List;

/**
 * Created by HASEE.
 */
public class MyPagerAdapter extends PagerAdapter{
    private List<String> picList;

    public MyPagerAdapter(List<String> picture) {
        this.picList = picture;
    }

    @Override
    public int getCount() {
        if (picList!=null && picList.size()>0){
            return picList.size()*1000;// 8000
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {//[0-7999]
        ImageView imageView = new ImageView(UIUitls.getContext());
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide
                .with(UIUitls.getContext())
                .load(Constant.IMG+picList.get(position%picList.size()))//[0----picList.size()-1]
                .placeholder(R.mipmap.ic_default)//异步加载过程中默认图片
                .error(R.mipmap.ic_error_page)
                .centerCrop()
                .into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
