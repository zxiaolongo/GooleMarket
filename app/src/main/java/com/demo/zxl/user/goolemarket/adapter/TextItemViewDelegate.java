package com.demo.zxl.user.goolemarket.adapter;


import com.demo.zxl.user.goolemarket.R;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by HASEE.
 */
public class TextItemViewDelegate implements ItemViewDelegate {
    @Override
    public int getItemViewLayoutId() {
        //指定一个条目的布局文件id
        return R.layout.item_category_text;
    }

    //item单个条目需要用到的对象
    //position条目的索引位置
    @Override
    public boolean isForViewType(Object item, int position) {
        //告知条目类型是什么,并且用到的数据类型是什么
        return item instanceof String;
    }

    @Override
    public void convert(ViewHolder holder, Object o, int position) {
        holder.setText(R.id.tv_category_title,(String)o);
    }
}
