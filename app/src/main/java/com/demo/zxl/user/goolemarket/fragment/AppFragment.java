package com.demo.zxl.user.goolemarket.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demo.zxl.user.goolemarket.R;
import com.demo.zxl.user.goolemarket.adapter.AppAdapter;
import com.demo.zxl.user.goolemarket.bean.HomeInfo;
import com.demo.zxl.user.goolemarket.protocol.AppProtocol;
import com.demo.zxl.user.goolemarket.utils.UIUitls;
import com.demo.zxl.user.goolemarket.view.LoadingPage;
import com.itheima.xrefreshlayout.XRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HASEE.
 */
public class AppFragment extends BaseFragment {

    private List<HomeInfo.AppInfo> appInfoList;
    @BindView(R.id.xrefreshLayout)
    XRefreshLayout xRefreshLayout;

    @BindView(R.id.rv_app)
    RecyclerView rvApp;
    private AppAdapter appAdapter;

    @Override
    public LoadingPage.ResultState onSubLoad() {
        AppProtocol appProtocol = new AppProtocol();
        appInfoList = appProtocol.getData("app", 0,"");
        return checkData(appInfoList);
    }

    @Override
    public View onSubCreateSuccessedView() {
        View view = UIUitls.inflate(R.layout.fragment_app);
        ButterKnife.bind(this,view);
        //应用模块,应用模块和首页模块单个条目布局效果一致的所以数据适配器中的代码一致
        appAdapter = new AppAdapter(UIUitls.getContext(), R.layout.item_app,appInfoList);
        rvApp.setLayoutManager(new LinearLayoutManager(UIUitls.getContext()));
        rvApp.setAdapter(appAdapter);

        xRefreshLayout.setOnRefreshListener(new XRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新方法
                loadData(true);
            }
            @Override
            public void onLoadMore() {
                //加载方法
                loadData(false);
            }
        });
        return view;
    }

    private void loadData(final boolean isRefresh) {
        new Thread(){
            @Override
            public void run() {
                AppProtocol appProtocol = new AppProtocol();
                List<HomeInfo.AppInfo> list = null;
                if (isRefresh){
                    list = appProtocol.getData("app", 0,"");
                }else{
                    list = appProtocol.getData("app",appInfoList.size(),"");
                }
                if (list!=null && list.size()>0){
                    //刷新或者加载更多的行为拿到数据了,则需要对列表数据进行变更
                    if (isRefresh){
                        appInfoList.clear();
                    }
                    appInfoList.addAll(list);
                    UIUitls.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            appAdapter.notifyDataSetChanged();
                            xRefreshLayout.completeRefresh();
                        }
                    });
                }else{
                    UIUitls.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            xRefreshLayout.completeRefresh();
                        }
                    });
                }
            }
        }.start();
    }
}
