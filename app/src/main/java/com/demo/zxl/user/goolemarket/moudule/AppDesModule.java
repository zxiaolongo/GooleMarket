package com.demo.zxl.user.goolemarket.moudule;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demo.zxl.user.goolemarket.R;
import com.demo.zxl.user.goolemarket.base.BaseModule;
import com.demo.zxl.user.goolemarket.bean.HomeInfo;
import com.demo.zxl.user.goolemarket.utils.UIUitls;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

/**
 * Created by HASEE.
 */

public class AppDesModule extends BaseModule<HomeInfo.AppInfo> {
    private ScrollView scrollview;

    @BindView(R.id.tv_Des)
    TextView tvDes;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    //isopen true 扩展  false 收缩
    private boolean isOpen = false;
    private HomeInfo.AppInfo appInfo;
    private LinearLayout.LayoutParams params;

    public AppDesModule(Context ctx, ScrollView scrollview) {
        super(ctx);
        this.scrollview = scrollview;
    }

    @Override
    public View initView() {
        View view = UIUitls.inflate(R.layout.layout_app_des);
        ButterKnife.bind(this,view);


        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expendOrBack();
            }
        });
        return view;
    }

    private void expendOrBack() {
        //获取收缩状态下控件的高度(7行高度)
        int shortHeight = getShortHeight();
        //获取扩展状态下控件的高度(完整显示所有文字的高度)
        int longHeight = getLongHeight();

        ValueAnimator valueAnimator = null;
        if (isOpen){
            //点击前是扩展的,点击后就收缩
            isOpen = false;
            if (shortHeight<longHeight){
                valueAnimator = ValueAnimator.ofInt(longHeight,shortHeight);
            }
        }else{
            //点击前是收缩的,点击后就扩展
            isOpen = true;
            if (shortHeight<longHeight){
                valueAnimator = ValueAnimator.ofInt(shortHeight,longHeight);
            }
        }
        if (valueAnimator!=null){
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //在执行动画的过程中,修改控件目前所处的高度值大小
                    int value = (int) animation.getAnimatedValue();
                    params.height = value;
                    tvDes.setLayoutParams(params);
                }
            });
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }
                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            valueAnimator.setDuration(200);
            valueAnimator.start();
        }
    }

    /**
     * 获取tvDes在显示所有文字时候的高度
     */
    private int getLongHeight() {
        //创建一个TextView克隆(模拟)已经给附上值的tvDes,从而获取其高度
        TextView textView = new TextView(UIUitls.getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(appInfo.getDes(), FROM_HTML_MODE_LEGACY, null, null));
        }else{
            textView.setText(Html.fromHtml(appInfo.getDes()));
        }
        //让模拟的textView宽和tvDes宽目前先保持一致
        int width = tvDes.getMeasuredWidth();

        //让某一个控件的宽度安装我们的约定显示,构建约定宽度32位数
        int width32 = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        //高度的范围,除了需要指定7行以外,限定一个最大值
        int height32 = View.MeasureSpec.makeMeasureSpec(1500,View.MeasureSpec.AT_MOST);

        //把宽度和高度的32位数作用在textView控件上
        textView.measure(width32,height32);

        //获取模拟控件的高度,即原tvDes控件的高度(7行高度)
        return textView.getMeasuredHeight();
    }

    /**
     * 获取tvDes在显示7行文字时候的高度
     */
    private int getShortHeight() {
        //创建一个TextView克隆(模拟)已经给附上值的tvDes,从而获取其高度
        TextView textView = new TextView(UIUitls.getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(appInfo.getDes(), FROM_HTML_MODE_LEGACY, null, null));
        }else{
            textView.setText(Html.fromHtml(appInfo.getDes()));
        }
        //让模拟的textView宽和tvDes宽目前先保持一致
        int width = tvDes.getMeasuredWidth();

        //约定目前模拟的TextView高只有原tvDes高度的7行即可
        textView.setLines(7);
        textView.setMaxLines(7);

        //让某一个控件的宽度安装我们的约定显示,构建约定宽度32位数
        int width32 = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        //高度的范围,除了需要指定7行以外,限定一个最大值
        int height32 = View.MeasureSpec.makeMeasureSpec(500,View.MeasureSpec.AT_MOST);

        //把宽度和高度的32位数作用在textView控件上
        textView.measure(width32,height32);

        //获取模拟控件的高度,即原tvDes控件的高度(7行高度)
        return textView.getMeasuredHeight();
    }

    @Override
    public void bindData(HomeInfo.AppInfo appInfo) {
        this.appInfo = appInfo;
        //在描述内容中包含<br/>关键字,所以在解析描述内容时,必须添加如下Api
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvDes.setText(Html.fromHtml(appInfo.getDes(), FROM_HTML_MODE_LEGACY, null, null));
        }else{
            tvDes.setText(Html.fromHtml(appInfo.getDes()));
        }
        tvAuthor.setText(appInfo.getAuthor());

        //打开界面的时候,先将描述文本控件高度设置为7行的高度
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, getShortHeight());
        tvDes.setLayoutParams(params);
    }
}
