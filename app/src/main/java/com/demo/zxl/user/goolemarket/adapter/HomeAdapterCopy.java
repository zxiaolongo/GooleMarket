package com.demo.zxl.user.goolemarket.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.zxl.user.goolemarket.R;
import com.demo.zxl.user.goolemarket.bean.HomeInfo;
import com.demo.zxl.user.goolemarket.utils.Constant;
import com.demo.zxl.user.goolemarket.utils.UIUitls;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HASEE.
 */
public class HomeAdapterCopy extends RecyclerView.Adapter {
    private HomeInfo home;

    public HomeAdapterCopy(HomeInfo home) {
        this.home = home;
    }

   /* @Override
    public int getItemViewType(int position) {
        //判断position对应的条目类型状态码
       if (position == 0){
           return 0;//返回轮播图条目类型状态码
       }else{
           return 1;//返回普通条目类型状态码
       }
    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*if (viewType == 0){
            //创建轮播图控件所在的ViewHolder
        }else{
            //创建普通条目控件所在ViewHolder
        }*/
        View view = View.inflate(UIUitls.getContext(), R.layout.item_home, null);
        HomeViewHolder homeViewHolder = new HomeViewHolder(view);
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeInfo.AppInfo appInfo = home.getList().get(position);
        //应用名称
        ((HomeViewHolder) holder).tvName.setText(appInfo.getName());
        //应用大小 转换成合理单位显示
        String fileSize = Formatter.formatFileSize(UIUitls.getContext(), appInfo.getSize());
        ((HomeViewHolder) holder).tvSize.setText(fileSize);
        //应用描述
        ((HomeViewHolder) holder).tvDes.setText(appInfo.getDes());
        //应用星星评分
        ((HomeViewHolder) holder).ratingBar.setRating(appInfo.getStars());
        //
//        ((HomeViewHolder)holder).tvName.setText(appInfo.getName());
        //1.先内存   2  文件   3 网络(存储在文件和内存)
        Glide
                .with(UIUitls.getContext())
                .load(Constant.IMG + appInfo.getIconUrl())
                .centerCrop()
//            .bitmapTransform(new PixelationFilterTransformation(UIUtils.getContext()))
//               .apply(bitmapTransform(new SepiaFilterTransformation()))
                .placeholder(R.mipmap.ic_default)//图片在异步加载过程中的占位图
                .crossFade(1000)//异步下载下来的图片,在1s钟之后在显示
                .error(R.mipmap.ic_error_page)//异步加载图片链接地址有问题时,显示图片
                .into(((HomeViewHolder) holder).ivIcon);


//        ((HomeViewHolder) holder).setPosition(position);
    }

    @Override
    public int getItemCount() {
        if (home != null && home.getList() != null && home.getList().size() > 0) {
            return home.getList().size();
        }
        return 0;
    }

    class HomeViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.rating_bar)
        RatingBar ratingBar;
        @BindView(R.id.tv_size)
        TextView tvSize;
        @BindView(R.id.tv_des)
        TextView tvDes;
        private int position;

        HomeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });*/
        }

        /*public void setPosition(int position) {
            this.position = position;
        }*/
    }
}
