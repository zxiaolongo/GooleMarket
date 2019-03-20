package com.demo.zxl.user.goolemarket.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by HASEE.
 */

public class RatioImageView extends ImageView {
    public static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private float ratio;

    public RatioImageView(Context context) {
        this(context,null);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomerAttrs(attrs);
    }

    private void initCustomerAttrs(AttributeSet attrs) {
        ratio = attrs.getAttributeFloatValue(NAMESPACE, "ratio", 0.0f);
    }

    //让图片的显示按照ratio比例显示 宽/高

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //widthMeasureSpec  32位2机制数  2位数模式(精确,至多,未定义)   30位 大小控件大小 999
        //精确模式  MeasureSpec.EXACTLY    具体数字
        //至多模式  MeasureSpec.AT_MOST
        //未定义    MeasureSpec.UNSPECIFIED

        //获取模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //获取控件的宽度,因为通过布局文件发现,此控件的宽度是一个固定值
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        //通过固定的宽度获取固定的高度
        int heightSize = (int) (widthSize / ratio);
        //需要将高度模式定义为精确,大小由宽度计算出来的精确值指定
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
