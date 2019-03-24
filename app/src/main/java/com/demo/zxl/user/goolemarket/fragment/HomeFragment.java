package com.demo.zxl.user.goolemarket.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ToxicBakery.viewpager.transforms.TabletTransformer;
import com.demo.zxl.user.goolemarket.DetailActivity;
import com.demo.zxl.user.goolemarket.R;
import com.demo.zxl.user.goolemarket.adapter.HomeAdapter;
import com.demo.zxl.user.goolemarket.adapter.MyPagerAdapter;
import com.demo.zxl.user.goolemarket.bean.HomeInfo;
import com.demo.zxl.user.goolemarket.protocol.HomeProtocol;
import com.demo.zxl.user.goolemarket.utils.UIUitls;
import com.demo.zxl.user.goolemarket.view.LoadingPage;
import com.itheima.xrefreshlayout.XRefreshLayout;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HASEE.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.rv_home)
    RecyclerView rvHome;

    @BindView(R.id.xrefreshLayout)
    XRefreshLayout xRefreshLayout;

    private HomeInfo home;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private ViewPager vp;
    private LinearLayout llDotContainer;
    private ArrayList<HomeInfo.AppInfo> appInfoList;
    private ArrayList<String> picList;
    private MyPagerAdapter myPagerAdapter;

    @Override
    public LoadingPage.ResultState onSubLoad() {
        appInfoList = new ArrayList<>();
        picList = new ArrayList<>();
        //请求网络触发
        HomeProtocol homeProtocol = new HomeProtocol();
        home = homeProtocol.getData("home", 0,"");

        if (home == null){
            return LoadingPage.ResultState.RESULT_STATE_ERROR;
        }
        if (home.getList()!=null){
            appInfoList.addAll(home.getList());
        }
        if (home.getPicture()!=null){
            picList.addAll(home.getPicture());
        }
        //请求过程中失败,失败枚举对象状态码
        return checkData(home.getList());
    }

    @Override
    public View onSubCreateSuccessedView() {
        //提供一个包含列表布局
        View view = UIUitls.inflate(R.layout.fragment_home);
        ButterKnife.bind(this,view);


        /*HomeAdapter homeAdapter = new HomeAdapter(home);
        //指定recycleView显示效果(1列,多列,瀑布流)
        rvHome.setLayoutManager(new LinearLayoutManager(UIUtils.getContext()));
        rvHome.setAdapter(homeAdapter);*/

        HomeAdapter homeAdapter = new HomeAdapter(
                UIUitls.getContext(),R.layout.item_home,appInfoList);
        headerAndFooterWrapper = new HeaderAndFooterWrapper(homeAdapter);
        initHeader();

        rvHome.setLayoutManager(new LinearLayoutManager(UIUitls.getContext()));
        rvHome.setAdapter(headerAndFooterWrapper);
        headerAndFooterWrapper.notifyDataSetChanged();

        xRefreshLayout.setOnRefreshListener(new XRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("","刷新当前页面的数据................");
                loadData(true);
            }

            @Override
            public void onLoadMore() {
                Log.i("","加载更多的数据.................");
                loadData(false);
            }
        });
        //点击事件
        // 1.先尝试在recycleView上添加和ListView想法一致,没有找到
        //2.尝试在recycleView带头的数据适配器中添加单条目点击事件,没有找到
        //3.recycleView不带头的数据适配器上注册点击事件
        homeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //参数一:view就是点中条目的view对象
                //参数二:holder单个条目view对象存储控件的viewHolder
                //参数三:点中条目的索引位置
                Log.i("","===================position = "+position);
                //页面跳转
                Intent intent = new Intent(UIUitls.getContext(),DetailActivity.class);
                HomeInfo.AppInfo appInfo = appInfoList.get(position - 1);
                intent.putExtra("packagename",appInfo.getPackageName());
                startActivity(intent);
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        return view;
    }

    /**
     * @param isRefresh  true 刷新  false 加载
     *                   即维护刷新,又维护加载
     */
    private void loadData(final boolean isRefresh) {
        new Thread(){
            @Override
            public void run() {
                HomeProtocol homeProtocol = new HomeProtocol();
                HomeInfo home = null;
                if (isRefresh){
                    home = homeProtocol.getData("home", 0,"");
                }else{
                    //只需要维护appInfoList集合中的数据即可
                    home = homeProtocol.getData("home",appInfoList.size(),"");
                }
                if (home!=null && home.getList()!=null && home.getPicture()!=null){
                    if (isRefresh){
                        //刷新和轮播图有关,加载和轮播图没有关系
                        //确保从服务器拿到了数据,再将之前的轮播图数据清空,添加最新的
                        if (home.getPicture().size()>0){
                            picList.clear();
                            picList.addAll(home.getPicture());
                        }
                        //列表
                        if (home.getList().size()>0){
                            appInfoList.clear();
                        }
                    }
                    if (home.getList().size()>0){
                        appInfoList.addAll(home.getList());
                    }
                    UIUitls.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isRefresh){
                                //刷新轮播图,将轮播图viewpager数据适配器进行刷新
                                myPagerAdapter.notifyDataSetChanged();
                                //重新初始化点,
                                initDot();
                                //轮播图进行轮转重头开始
                                initScroll();
                            }
                            //列表处理(无论是刷新还是加载,列表都需要进行刷新操作)
                            headerAndFooterWrapper.notifyDataSetChanged();
                            //告知下拉刷新和上拉加载的自定义控件,刷新加载完毕
                            xRefreshLayout.completeRefresh();
                        }
                    });
                }else{
                    UIUitls.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            //虽然本次请求出错了,没有刷新或者加载到数据,但是依然要将刷新加载完毕的方法调用,否则刷新头和加载脚底无法隐藏
                            xRefreshLayout.completeRefresh();
                        }
                    });
                }
            }
        }.start();
    }

    private void initHeader() {
        View view = UIUitls.inflate(R.layout.layout_home_header);
        llDotContainer = (LinearLayout) view.findViewById(R.id.ll_dot_container);
        vp = (ViewPager) view.findViewById(R.id.vp);

        headerAndFooterWrapper.addHeaderView(view);

        initViewPager();
        initDot();
        initScroll();
    }

    private void initScroll() {
        RunnableTask runnableTask = new RunnableTask();
        runnableTask.start();
    }
    class RunnableTask implements Runnable{
        //run方法的触发方法
        public void start(){
            //将前一次的任务移除掉
//            UIUtils.getHandler().removeCallbacks(this);
            UIUitls.getHandler().removeCallbacksAndMessages(null);
            UIUitls.getHandler().postDelayed(this,3000);
        }
        @Override
        public void run() {
            //指示器的点向后移动
            //1.获取目前viewpager所出点的索引位置
            int currentItem = vp.getCurrentItem();
            currentItem = currentItem+1;
            vp.setCurrentItem(currentItem);

            UIUitls.getHandler().removeCallbacks(this);
            UIUitls.getHandler().postDelayed(this,3000);
        }
    }

    private void initDot() {
        vp.setPageTransformer(true,new TabletTransformer());
        //将viewpager开始的位置指向总图片张数的中间位置
        vp.setCurrentItem(picList.size()*1000/2);
        //1.将线性布局中所有的控件全部移除,用于添加点
        llDotContainer.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //2.添加点,点的个数等于图片张数
        for (int i = 0; i < picList.size(); i++) {
            ImageView imageView = new ImageView(UIUitls.getContext());
            if (i == 0){
                //选中点的图片
                imageView.setImageResource(R.mipmap.indicator_selected);
            }else{
                //未选中点的图片
                imageView.setImageResource(R.mipmap.indicator_normal);
            }
            params.setMargins(0,0,UIUitls.dip2px(5),UIUitls.dip2px(5));
            llDotContainer.addView(imageView,params);
        }
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                //[0-7999]------>[0-7]
                position = position%picList.size();
                for (int i = 0; i < picList.size(); i++) {
                    ImageView imageView = (ImageView) llDotContainer.getChildAt(i);
                    if (i == position){
                        imageView.setImageResource(R.mipmap.indicator_selected);
                    }else{
                        imageView.setImageResource(R.mipmap.indicator_normal);
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initViewPager() {
        //轮播图用到的数据适配器
        myPagerAdapter = new MyPagerAdapter(picList);
        vp.setAdapter(myPagerAdapter);

    }

    //将4套大家都有的布局效果封装在父类中统一处理
}
