package com.demo.zxl.user.goolemarket.moudule;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.zxl.user.goolemarket.R;
import com.demo.zxl.user.goolemarket.base.BaseModule;
import com.demo.zxl.user.goolemarket.bean.HomeInfo;
import com.demo.zxl.user.goolemarket.utils.Constant;
import com.demo.zxl.user.goolemarket.utils.UIUitls;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HASEE.
 */
public class AppSafeModule extends BaseModule<HomeInfo.AppInfo> {
    @BindView(R.id.iv_safe_icon1)
    ImageView ivSafeIcon1;
    @BindView(R.id.iv_safe_icon2)
    ImageView ivSafeIcon2;
    @BindView(R.id.iv_safe_icon3)
    ImageView ivSafeIcon3;

    @BindView(R.id.iv_arrow)
    ImageView ivArrow;

    @BindView(R.id.iv_safe_desurl)
    ImageView ivSafeDesurl;
    @BindView(R.id.tv_safe_des1)
    TextView tvSafeDes1;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.iv_safe_desur2)
    ImageView ivSafeDesur2;
    @BindView(R.id.tv_safe_des2)
    TextView tvSafeDes2;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.iv_safe_desur3)
    ImageView ivSafeDesur3;
    @BindView(R.id.tv_safe_des3)
    TextView tvSafeDes3;
    @BindView(R.id.ll3)
    LinearLayout ll3;

    @BindView(R.id.ll_container)
    LinearLayout llContainer;//放置3个线性布局容器

    private ImageView[] ivSafeIconArray;
    private ImageView[] ivSafeDesurlArray;
    private TextView[] tvSafeDesArray;
    private LinearLayout[] linearLayouts;

    //isOpen 为true 代表扩展  isOpen为false代表收缩
    public boolean isOpen = false;
    private final LinearLayout.LayoutParams params;

    public AppSafeModule(Context ctx) {
        super(ctx);
        ivSafeIconArray = new ImageView[]{ivSafeIcon1, ivSafeIcon2, ivSafeIcon3};
        ivSafeDesurlArray = new ImageView[]{ivSafeDesurl, ivSafeDesur2, ivSafeDesur3};
        tvSafeDesArray = new TextView[]{tvSafeDes1, tvSafeDes2, tvSafeDes3};
        linearLayouts = new LinearLayout[]{ll1, ll2, ll3};

        //将放置了3个线性布局容器高度设置为0
        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0);
        //将高度0作用在目前线性布局上
        llContainer.setLayoutParams(params);

        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //管理线性布局扩展和收缩
                expendOrBack();
            }
        });
    }

    private void expendOrBack() {
        //获取控件完整显示的高度
        int longHeight = getLongHeight();
        //最短高度0
        ValueAnimator valueAnimator = null;
        if (isOpen){
            //点之前isOpen值为true,点后变为false
            isOpen = false;
            //扩展longHeight------>收缩0
            valueAnimator = ValueAnimator.ofInt(longHeight, 0);
        }else{
            //点之前isOpen值为false,点后变为true
            isOpen = true;
            //收缩 0 ----->扩展  longHeight
            valueAnimator = ValueAnimator.ofInt(0, longHeight);
        }
        //添加高度值在变化过程中的监听
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //值在变化过程中触发方法
                //获取此方法调用的时候的高度大小
                int currentHeight = (int) animation.getAnimatedValue();
                //将渐变的结果作为控件的高度使用
                params.height = currentHeight;
                llContainer.setLayoutParams(params);
            }
        });
        valueAnimator.setDuration(200);
        valueAnimator.start();
    }

    private int getLongHeight() {
        //通过系统测量控件扩展时高度  //模式 爹不管    //大小为0 ===> 自己决定自己大小吧
        llContainer.measure(0,0);
        //通过系统测量后容器的高度
        return llContainer.getMeasuredHeight();
    }

    @Override
    public View initView() {
        View view = UIUitls.inflate(R.layout.layout_safe_info);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void bindData(HomeInfo.AppInfo appInfo) {
        //获取和安全相关的集合
        List<HomeInfo.AppInfo.Safe> safeList = appInfo.getSafe();

        //将顶部绿色图片显示隐藏和图片展示
        for (int i = 0; i < 3; i++) {//0,1,2
            if (i < safeList.size()) {//假设safeList.size()就是2
                //i能够取值到0和1,显示控件
                ivSafeIconArray[i].setVisibility(View.VISIBLE);
                Glide.with(UIUitls.getContext())
                        .load(Constant.IMG + safeList.get(i).getSafeUrl())
                        .into(ivSafeIconArray[i]);
            } else {
                //i能够取值2,隐藏控件
                ivSafeIconArray[i].setVisibility(View.GONE);
            }
        }

        for (int i = 0; i < 3; i++) {
            if (i < safeList.size()) {
                linearLayouts[i].setVisibility(View.VISIBLE);

                Glide.with(UIUitls.getContext())
                        .load(Constant.IMG + safeList.get(i).getSafeDesUrl())
                        .into(ivSafeDesurlArray[i]);
                tvSafeDesArray[i].setText(safeList.get(i).getSafeDes());
            } else {
                linearLayouts[i].setVisibility(View.GONE);
            }
        }
    }
}
