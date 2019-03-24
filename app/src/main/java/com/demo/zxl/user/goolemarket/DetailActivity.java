package com.demo.zxl.user.goolemarket;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.demo.zxl.user.goolemarket.bean.HomeInfo;
import com.demo.zxl.user.goolemarket.moudule.AppDesModule;
import com.demo.zxl.user.goolemarket.moudule.AppInfoModule;
import com.demo.zxl.user.goolemarket.moudule.AppPicModule;
import com.demo.zxl.user.goolemarket.moudule.AppSafeModule;
import com.demo.zxl.user.goolemarket.protocol.DetailProtocol;
import com.demo.zxl.user.goolemarket.utils.UIUitls;
import com.demo.zxl.user.goolemarket.view.LoadingPage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HASEE.
 */
public class DetailActivity extends Activity {
    @BindView(R.id.fl_app_info)
    FrameLayout flAppInfo;

    @BindView(R.id.fl_app_safe)
    FrameLayout flAppSafe;

    @BindView(R.id.fl_app_pic)
    FrameLayout flAppPic;

    @BindView(R.id.fl_app_des)
    FrameLayout flAppDes;

    @BindView(R.id.scrollview)
    ScrollView scrollview;

    private String packagename;
    private LoadingPage loadingPage;
    private HomeInfo.AppInfo detailAppInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packagename = getIntent().getStringExtra("packagename");

        loadingPage = new LoadingPage(UIUitls.getContext()) {
            @Override
            public ResultState onLoad() {
                return onSubLoad();
            }

            @Override
            public View onCreateSuccessedView() {
                return onSubCreateSuccessedView();
            }
        };
        //因为在loadingPage中包含了4中界面展示情况,所以将loadingPage作为activity页面显示内容
        setContentView(loadingPage);

        //在show方法中会触发onLoad方法,在onLoad方法中需要请求网络获取结果,结果还必须是(ResultState)限定类型
        loadingPage.show();
    }

    private View onSubCreateSuccessedView() {
        View view = UIUitls.inflate(R.layout.activity_detail);
        ButterKnife.bind(this,view);

        //将顶部应用基本信息添加至帧布局中
        AppInfoModule appInfoModule = new AppInfoModule(UIUitls.getContext());
        appInfoModule.bindData(detailAppInfo);
        flAppInfo.addView(appInfoModule.view);
        //安全相关
        AppSafeModule appSafeModule = new AppSafeModule(UIUitls.getContext());
        appSafeModule.bindData(detailAppInfo);
        flAppSafe.addView(appSafeModule.view);
        //图片相关
        AppPicModule appPicModule = new AppPicModule(UIUitls.getContext());
        appPicModule.bindData(detailAppInfo);
        flAppPic.addView(appPicModule.view);

        AppDesModule appDesModule = new AppDesModule(UIUitls.getContext(),scrollview);
        appDesModule.bindData(detailAppInfo);
        flAppDes.addView(appDesModule.view);
        return view;
    }

    private LoadingPage.ResultState onSubLoad() {
        DetailProtocol detailProtocol = new DetailProtocol();
        detailAppInfo = detailProtocol.getData(
                "detail", 0, "&packageName=" + packagename);
        if (detailAppInfo == null) {
            return LoadingPage.ResultState.RESULT_STATE_ERROR;
        }
        return LoadingPage.ResultState.RESULT_STATE_SUCCESSED;
    }
}
