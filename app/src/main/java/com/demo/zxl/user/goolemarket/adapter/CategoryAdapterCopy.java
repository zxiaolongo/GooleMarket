package com.demo.zxl.user.goolemarket.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.zxl.user.goolemarket.R;
import com.demo.zxl.user.goolemarket.bean.CategoryInfo;
import com.demo.zxl.user.goolemarket.utils.Constant;
import com.demo.zxl.user.goolemarket.utils.UIUitls;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by HASEE.
 */
public class CategoryAdapterCopy extends RecyclerView.Adapter {
    private ArrayList<Object> categoryList;
    public static final int ITEM_TEXT = 0;
    public static final int ITEM_TEXT_PIC = 1;

    public CategoryAdapterCopy(ArrayList<Object> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public int getItemViewType(int position) {
        Object o = categoryList.get(position);
        //o是字符串的对象,或者字符串子类对象,则可以判断此position位置需要显示存文本类型条目
        if (o instanceof String) {
            //存文本类型条目  指定状态码
            return ITEM_TEXT;
        } else {
            //图片+文本类型条目
            return ITEM_TEXT_PIC;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //通过viewType判断条目类型,不同的条目类型不同的布局,不同的布局必然会有不同viewHolder
        if (viewType == ITEM_TEXT) {
            //存文本条目类型
            View view = UIUitls.inflate(R.layout.item_category_text);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);

            TitleViewHolder titleViewHolder = new TitleViewHolder(view);
            return titleViewHolder;
        } else {
            //图片+文本条目类型
            View view = UIUitls.inflate(R.layout.item_category_text_pic);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);

            PicViewHolder picViewHolder = new PicViewHolder(view);
            return picViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleViewHolder){
            //获取到的对象是存文本条目的holder对象
            String title = (String) categoryList.get(position);
            ((TitleViewHolder)holder).tvCategoryTitle.setText(title);
        }else{
            //获取到的对象是图片+文本条目的holder对象
            CategoryInfo.CategoryInfoItem item = (CategoryInfo.CategoryInfoItem) categoryList.get(position);
            ((PicViewHolder)holder).tvImage1Title.setText(item.name1);
            ((PicViewHolder)holder).tvImage2Title.setText(item.name2);
            ((PicViewHolder)holder).tvImage3Title.setText(item.name3);

            Glide.with(UIUitls.getContext())
                    .load(Constant.IMG+item.url1)
                    .bitmapTransform(new CropCircleTransformation(UIUitls.getContext()))
                    .error(R.mipmap.ic_error_page)
                    .into(((PicViewHolder)holder).ivImage1);

            Glide.with(UIUitls.getContext())
                    .load(Constant.IMG+item.url2)
                    .error(R.mipmap.ic_error_page)
                    .bitmapTransform(new CropCircleTransformation(UIUitls.getContext()))
                    .into(((PicViewHolder)holder).ivImage2);

            Glide.with(UIUitls.getContext())
                    .load(Constant.IMG+item.url3)
                    .error(R.mipmap.ic_error_page)
                    .bitmapTransform(new CropCircleTransformation(UIUitls.getContext()))
                    .into(((PicViewHolder)holder).ivImage3);
        }
    }

    @Override
    public int getItemCount() {
        //条目的总数
        if (categoryList != null && categoryList.size() > 0) {
            return categoryList.size();
        }
        return 0;
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_category_title)
        TextView tvCategoryTitle;

        TitleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class PicViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_image1)
        ImageView ivImage1;
        @BindView(R.id.tv_image1_title)
        TextView tvImage1Title;
        @BindView(R.id.iv_image2)
        ImageView ivImage2;
        @BindView(R.id.tv_image2_title)
        TextView tvImage2Title;
        @BindView(R.id.iv_image3)
        ImageView ivImage3;
        @BindView(R.id.tv_image3_title)
        TextView tvImage3Title;

        PicViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
