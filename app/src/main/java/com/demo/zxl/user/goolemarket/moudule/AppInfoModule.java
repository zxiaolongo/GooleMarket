package com.demo.zxl.user.goolemarket.moudule;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.zxl.user.goolemarket.R;
import com.demo.zxl.user.goolemarket.base.BaseModule;
import com.demo.zxl.user.goolemarket.bean.HomeInfo;
import com.demo.zxl.user.goolemarket.utils.Constant;
import com.demo.zxl.user.goolemarket.utils.UIUitls;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HASEE.
 */

public class AppInfoModule extends BaseModule<HomeInfo.AppInfo> {
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.tv_downloadnum)
    TextView tvDownloadnum;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_size)
    TextView tvSize;

    public AppInfoModule(Context ctx) {
        super(ctx);
    }

    @Override
    public View initView() {
        View view = UIUitls.inflate(R.layout.layout_app_info);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void bindData(HomeInfo.AppInfo appInfo) {
        tvName.setText(appInfo.getName());
        ratingBar.setRating(appInfo.getStars());

        tvDownloadnum.setText("下载量:"+appInfo.getDownloadNum());
        tvVersion.setText("版本号:"+appInfo.getVersion());
        tvDate.setText("发布时间:"+appInfo.getDate());
        String fileSize = Formatter.formatFileSize(UIUitls.getContext(), appInfo.getSize());
        tvSize.setText("大小:"+fileSize);

        Glide.with(UIUitls.getContext())
                .load(Constant.IMG+appInfo.getIconUrl())
                .error(R.mipmap.ic_error_page)
                .placeholder(R.mipmap.ic_default)
                .into(ivIcon);
    }
}
