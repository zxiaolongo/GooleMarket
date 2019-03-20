package com.demo.zxl.user.goolemarket.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by HASEE.
 */
public class MyDrawableUtil {
    /**
     * @param rgb   图片颜色
     * @param r     图片圆角半径
     * @return      图片对象
     */
    public static Drawable getDrawable(int rgb, int r) {
        //绘制圆角矩形图片
        GradientDrawable gradientDrawable = new GradientDrawable();
        //绘制矩形形状
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        //绘制矩形圆角半径
        gradientDrawable.setCornerRadius(r);
        //绘制图片颜色
        gradientDrawable.setColor(rgb);
        return gradientDrawable;
    }
    //创建不同状态下显示不同图片的状态选择器
    public static StateListDrawable getStateListDrawable(
            Drawable drawableNormal,Drawable drawablePress){
        //创建图片状态选择器的对象
        StateListDrawable stateListDrawable = new StateListDrawable();
        //参数一:状态的数组,参数二:此状态下需要用到的图片
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed},drawablePress);
        stateListDrawable.addState(new int[]{},drawableNormal);
        return stateListDrawable;
    }
}
