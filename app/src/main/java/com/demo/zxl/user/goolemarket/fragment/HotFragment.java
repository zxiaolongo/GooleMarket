package com.demo.zxl.user.goolemarket.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demo.zxl.user.goolemarket.protocol.HotProtocol;
import com.demo.zxl.user.goolemarket.utils.MyDrawableUtil;
import com.demo.zxl.user.goolemarket.utils.UIUitls;
import com.demo.zxl.user.goolemarket.view.FlowLayout;
import com.demo.zxl.user.goolemarket.view.LoadingPage;

import java.util.List;
import java.util.Random;

/**
 * Created by HASEE.
 */
public class HotFragment extends BaseFragment {

    private List<String> hotList;

    @Override
    public LoadingPage.ResultState onSubLoad() {
        HotProtocol hotProtocol = new HotProtocol();
        hotList = hotProtocol.getData("hot", 0,"");
        return checkData(hotList);
    }

    @Override
    public View onSubCreateSuccessedView() {
        //整体布局结构如下
        /*<ScrollView>
                <FlowLayout>
                    TextView
                    TextView
                     ....
                </FlowLayout>
        </ScrollView>   */
        ScrollView scrollView = new ScrollView(UIUitls.getContext());
        int padding = UIUitls.dip2px(6);
        scrollView.setPadding(padding,padding,padding,padding);

        FlowLayout flowLayout = new FlowLayout(UIUitls.getContext());
        for (int i = 0; i < hotList.size(); i++) {
            TextView textView = new TextView(UIUitls.getContext());
            textView.setText(hotList.get(i));
            textView.setTextColor(Color.WHITE);
            int paddingInner = UIUitls.dip2px(10);
            textView.setPadding(paddingInner,paddingInner,paddingInner,paddingInner);
            textView.setGravity(Gravity.CENTER);

            // 红red 00   绿green 55  蓝blue  77
            //0-254        0-254        0-254
            int red = 30+new Random().nextInt(190);//[30,219]
            int green = 30+new Random().nextInt(190);//[30,219]
            int blue = 30+new Random().nextInt(190);//[30,219]
            //将3个颜色混合成一个颜色最终作为图片的背景色
            int rgb = Color.rgb(red, green, blue);

            Drawable drawableNormal = MyDrawableUtil.getDrawable(rgb, UIUitls.dip2px(6));
            Drawable drawablePress = MyDrawableUtil.getDrawable(Color.LTGRAY, UIUitls.dip2px(6));
            //创建一个选择器对象,给textView作为背景使用
            StateListDrawable stateListDrawable = MyDrawableUtil.getStateListDrawable(
                    drawableNormal, drawablePress);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                textView.setBackground(stateListDrawable);
            }else{
                textView.setBackgroundDrawable(stateListDrawable);
            }

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            flowLayout.addView(textView);
        }
        scrollView.addView(flowLayout);
        return scrollView;
    }
}
