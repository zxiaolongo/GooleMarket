package com.demo.zxl.user.goolemarket.moudule;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.demo.zxl.user.goolemarket.ImageScaleActivity;
import com.demo.zxl.user.goolemarket.R;
import com.demo.zxl.user.goolemarket.base.BaseModule;
import com.demo.zxl.user.goolemarket.bean.HomeInfo;
import com.demo.zxl.user.goolemarket.utils.Constant;
import com.demo.zxl.user.goolemarket.utils.UIUitls;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HASEE.
 */

public class AppPicModule extends BaseModule<HomeInfo.AppInfo> {
    @BindView(R.id.ll_pic)
    LinearLayout llPic;
    private ArrayList<String> screenList;

    public AppPicModule(Context ctx) {
        super(ctx);
    }

    @Override
    public View initView() {
        View view = UIUitls.inflate(R.layout.layout_app_pic);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void bindData(HomeInfo.AppInfo appInfo) {
        screenList = (ArrayList<String>) appInfo.getScreen();
        llPic.removeAllViews();
        int width = UIUitls.dip2px(90);
        int height = UIUitls.dip2px(150);
        int margin = UIUitls.dip2px(6);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        for (int i = 0; i < screenList.size(); i++) {
            ImageView imageView = new ImageView(UIUitls.getContext());
            Glide.with(UIUitls.getContext())
                    .load(Constant.IMG+ screenList.get(i)).into(imageView);
            params.setMargins(margin,margin,0,margin);
            llPic.addView(imageView,params);

            final int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UIUitls.getContext(), ImageScaleActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("screenList", screenList);
                    intent.putExtra("index", finalI);
                    UIUitls.getContext().startActivity(intent);
                }
            });
        }
    }
}
