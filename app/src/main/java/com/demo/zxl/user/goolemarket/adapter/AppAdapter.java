package com.demo.zxl.user.goolemarket.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demo.zxl.user.goolemarket.R;
import com.demo.zxl.user.goolemarket.bean.HomeInfo;
import com.demo.zxl.user.goolemarket.utils.Constant;
import com.demo.zxl.user.goolemarket.utils.UIUitls;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by HASEE.
 */
public class AppAdapter extends AnimatorAdapter<HomeInfo.AppInfo> {

    public AppAdapter(Context context, int item_app, List<HomeInfo.AppInfo> appInfoList) {
        super(context, item_app, appInfoList);
    }

    @Override
    protected void convert(ViewHolder holder, HomeInfo.AppInfo appInfo, int position) {
        super.convert(holder,appInfo,position);
        //通过holder找控件,然后通过appInfo字段进行赋值
        //参数一:holder用于存储控件对象
        //参数二:每一个条目javabean对象
        //参数三:每一个条目索引
        //应用名称
        holder.setText(R.id.tv_name,appInfo.getName());
        //应用大小
        String fileSize = Formatter.formatFileSize(UIUitls.getContext(), appInfo.getSize());
        holder.setText(R.id.tv_size,fileSize);
        //应用描述
        holder.setText(R.id.tv_des,appInfo.getDes());
        //应用评分
        holder.setRating(R.id.rating_bar,appInfo.getStars());
        //图片图标

        //获取显示图片的控件对象
        ImageView ivIcon = holder.getView(R.id.iv_icon);
        Glide
                .with(UIUitls.getContext())
                .load(Constant.IMG+appInfo.getIconUrl())
                .centerCrop()
                .placeholder(R.mipmap.ic_default)//图片在异步加载过程中的占位图
                .crossFade(1000)//异步下载下来的图片,在1s钟之后在显示
                .error(R.mipmap.ic_error_page)//异步加载图片链接地址有问题时,显示图片
                .into(ivIcon);
    }
}
