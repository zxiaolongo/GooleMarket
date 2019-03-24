package com.demo.zxl.user.goolemarket.moudule;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.demo.zxl.user.goolemarket.R;
import com.demo.zxl.user.goolemarket.base.BaseModule;
import com.demo.zxl.user.goolemarket.bean.HomeInfo;
import com.demo.zxl.user.goolemarket.global.MyApplication;
import com.demo.zxl.user.goolemarket.utils.Constant;
import com.demo.zxl.user.goolemarket.utils.UIUitls;
import com.itheima.okhttpdownloader.download.DownloadEngine;
import com.itheima.okhttpdownloader.download.DownloadInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HASEE.
 */
public class AppDownloadModule extends BaseModule<HomeInfo.AppInfo> {
    @BindView(R.id.btn_fav)
    Button btnFav;

    @BindView(R.id.btn_share)
    Button btnShare;

    @BindView(R.id.btn_download)
    Button btnDownload;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private HomeInfo.AppInfo appInfo;

    public AppDownloadModule(Context ctx) {
        super(ctx);
    }

    @Override
    public View initView() {
        View view = UIUitls.inflate(R.layout.layout_app_download);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void bindData(HomeInfo.AppInfo appInfo) {
        //下载module中维护数据(下载状态匹配文字,当前下载位置)
        this.appInfo = appInfo;
        //判断目前打开详情界面的的这个应用,之前是否有下载过,如果下载过,则需要从数据库中获取之前记录的下载状态和当前下载位置用于展示
        //如果此应用之前就没有下载过程,进入详情界面的时候,只需要显示"下载"

        DownloadInfo downloadInfo = DownloadEngine.create(UIUitls.getContext()).getDownloadInfo(appInfo.getId() + "");
        if (downloadInfo == null){
            //数据库没有此应用记录,没有下载过
            btnDownload.setText("下载");
        }else{
            updateUIByDownloadInfo(downloadInfo);
        }

        //下载过程注册一个监听,用于更新进度和文字状态
        DownloadEngine.create(UIUitls.getContext()).addDownloadObserver(new DownloadEngine.DownloadObserver() {
            @Override
            public void onDownloadUpdate(DownloadInfo downloadInfo) {
                //一旦下载状态发生变化,则会触发此方法,一旦下载进度发生变化同样也会触发此方法
                //依赖于回调方法中维护了目前下载状态和下载位置downloadInfo页面的更新
                updateUIByDownloadInfo(downloadInfo);
            }
        },appInfo.getId()+"");
    }

    /**
     * @param downloadInfo 已经触发过下载的应用,记录在数据库中的信息
     */
    private void updateUIByDownloadInfo(DownloadInfo downloadInfo) {
        switch (downloadInfo.state){
            case DownloadEngine.STATE_DOWNLOADING://下载中
                if (downloadInfo.size>0){
                    //将目前暂停时候的进度显示在进度条控件上
                    int progress = (int) (downloadInfo.currentLength * 100 / downloadInfo.size);
                    progressBar.setProgress(progress);
                    //如果现在在下载中,则需要显示目前下载的百分比的值
                    btnDownload.setText(progress+"%");
                }
                break;
            case DownloadEngine.STATE_PAUSE://下载暂停
                btnDownload.setText("点击继续下载");
                if (downloadInfo.size>0){
                    //将目前暂停时候的进度显示在进度条控件上
                    int progress = (int) (downloadInfo.currentLength * 100 / downloadInfo.size);
                    progressBar.setProgress(progress);
                }
                break;
            case DownloadEngine.STATE_ERROR://下载失败
                btnDownload.setText("下载失败,点击重试");
                break;
            case DownloadEngine.STATE_WAITING://等待
                btnDownload.setText("准备下载");
                break;
            case DownloadEngine.STATE_FINISH://下载完成
                btnDownload.setText("请安装");
                break;
        }
    }

    @OnClick(R.id.btn_download)
    public void onViewClicked() {
        downloadOrPauseOrInstall();
    }

    private void downloadOrPauseOrInstall() {
        //判断目前点击按钮的时候,这个应用是之前已经触发过下载的,还是没有触发过下载的
        DownloadInfo downloadInfo = DownloadEngine.create(UIUitls.getContext()).getDownloadInfo(appInfo.getId() + "");
        if (downloadInfo == null){
            //如果是之前没有下载过的应用,则重头开始下
            String path = MyApplication.getPath()+"/"+appInfo.getName()+".apk";
            String downloadUrl = Constant.DOWNLOADURL+appInfo.getDownloadUrl();
            DownloadEngine.create(UIUitls.getContext()).download(
                    appInfo.getId()+"",
                    downloadUrl,
                    path);//path新创建的一个存储的apk
        }else{
            //如果是已经触发过下载的应用,断点续传接着下   range
            String downloadUrl = String.format(Constant.CONTINUEDOWNLOADURL,
                    appInfo.getDownloadUrl(),
                    downloadInfo.currentLength);
            if (downloadInfo.state == DownloadEngine.STATE_DOWNLOADING
                    || downloadInfo.state == DownloadEngine.STATE_WAITING){
                //暂停
                DownloadEngine.create(UIUitls.getContext()).pause(downloadInfo.taskId);
            }else if (downloadInfo.state == DownloadEngine.STATE_ERROR ||
                    downloadInfo.state == DownloadEngine.STATE_PAUSE){
                //继续下载
                DownloadEngine.create(UIUitls.getContext()).download(
                        downloadInfo.taskId,
                        downloadUrl,
                        downloadInfo.path);//因为现在文件已经存在,只需要在原路径下的文件继续写入数据即可
            }else if (downloadInfo.state == DownloadEngine.STATE_FINISH){
                //安装 隐式意图安装apk
                installApk(downloadInfo.path);
            }
        }
    }

    private void installApk(String downloadPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //指定apk文件路径,安装apk
        intent.setDataAndType(Uri.parse("file://"+downloadPath),"application/vnd.android.package-archive");
        UIUitls.getContext().startActivity(intent);
    }

}
