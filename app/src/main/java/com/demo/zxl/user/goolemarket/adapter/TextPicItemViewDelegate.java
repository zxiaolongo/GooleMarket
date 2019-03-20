package com.demo.zxl.user.goolemarket.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demo.zxl.user.goolemarket.R;
import com.demo.zxl.user.goolemarket.bean.CategoryInfo;
import com.demo.zxl.user.goolemarket.utils.Constant;
import com.demo.zxl.user.goolemarket.utils.UIUitls;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by HASEE.
 */
public class TextPicItemViewDelegate implements ItemViewDelegate {
    @Override
    public int getItemViewLayoutId() {
        //指定单条目布局
        return R.layout.item_category_text_pic;
    }

    @Override
    public boolean isForViewType(Object item, int position) {
        return item instanceof CategoryInfo.CategoryInfoItem;
    }

    @Override
    public void convert(ViewHolder holder, Object o, int position) {
        //给holder中的控件赋值,holder中的控件来源于item_category_text_pic布局文件
        CategoryInfo.CategoryInfoItem item = (CategoryInfo.CategoryInfoItem) o;
        //设置文本内容
        holder.setText(R.id.tv_image1_title,item.name1);
        holder.setText(R.id.tv_image2_title,item.name2);
        holder.setText(R.id.tv_image3_title,item.name3);

        //设置图片内容
        ImageView ivImage1 = holder.getView(R.id.iv_image1);
        ImageView ivImage2 = holder.getView(R.id.iv_image2);
        ImageView ivImage3 = holder.getView(R.id.iv_image3);

        Glide.with(UIUitls.getContext())
                .load(Constant.IMG+item.url1)
                .error(R.mipmap.ic_error_page)
                .bitmapTransform(new CropCircleTransformation(UIUitls.getContext()))
                .into(ivImage1);

        Glide.with(UIUitls.getContext())
                .load(Constant.IMG+item.url2)
                .error(R.mipmap.ic_error_page)
                .bitmapTransform(new CropCircleTransformation(UIUitls.getContext()))
                .into(ivImage2);


        Glide.with(UIUitls.getContext())
                .load(Constant.IMG+item.url3)
                .error(R.mipmap.ic_error_page)
                .bitmapTransform(new CropCircleTransformation(UIUitls.getContext()))
                .into(ivImage3);
    }
}
