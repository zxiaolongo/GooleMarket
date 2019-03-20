package com.demo.zxl.user.goolemarket.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by HASEE.
 */
public class AnimatorAdapter<T> extends CommonAdapter<T>{
    public AnimatorAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, T t, int position) {
        //动画编写
        //1.recycleView单个条目的view对象
        holder.itemView.setScaleX(0.5f);
        holder.itemView.setScaleY(0.5f);

        //2.让x和y由之前的0.5倍变成1倍大小
        ViewCompat.animate(holder.itemView)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .setDuration(100)
                .start();
    }
}
