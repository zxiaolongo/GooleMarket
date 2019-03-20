package com.demo.zxl.user.goolemarket.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.demo.zxl.user.goolemarket.R;
import com.demo.zxl.user.goolemarket.bean.SubjectInfo;
import com.demo.zxl.user.goolemarket.utils.Constant;
import com.demo.zxl.user.goolemarket.utils.UIUitls;
import com.demo.zxl.user.goolemarket.view.RatioImageView;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by HASEE.
 */
public class SubjectAdapter extends AnimatorAdapter<SubjectInfo>{
    public SubjectAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }
    @Override
    protected void convert(ViewHolder holder, SubjectInfo subjectInfo, int position) {
        /*super.convert(holder,subjectInfo,position);*/
        //2.43:1

        //设置单条目的文本内容
        holder.setText(R.id.tv_image_des,subjectInfo.getDes());
        //设置单条目的图片内容
        //1.图片控件获取出来
        RatioImageView ratioImageView = holder.getView(R.id.riv);
        //2.给imageView提供一个异步加载的图片
        Glide.with(UIUitls.getContext())
                .load(Constant.IMG+subjectInfo.getUrl())
                .error(R.mipmap.ic_error_page)
                .into(ratioImageView);
    }
}
