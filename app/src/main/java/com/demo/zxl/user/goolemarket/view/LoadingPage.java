package com.demo.zxl.user.goolemarket.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.demo.zxl.user.goolemarket.R;
import com.demo.zxl.user.goolemarket.utils.UIUitls;


/**
 * Created by HASEE.
 */

public abstract class LoadingPage extends FrameLayout {
    //后续会添加在LoadingPage中的view对象
    private View loadingView;
    private View errorView;
    private View emptyView;
    private View successedView;
    //请求网络过程中后请求完成后会出现的状态
    public static final int STATE_NONE = 0;//初始状态
    public static final int STATE_LOADING = 1;//请求过程中状态
    public static final int STATE_ERROR = 2;//请求失败状态
    public static final int STATE_EMPTY = 3;//请求为空状态
    public static final int STATE_SUCCESSED = 4;//请求成功状态

    //目前所处什么状态,用一个变量记录
    private int current_state = STATE_NONE;

    public LoadingPage(Context context) {
        this(context,null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //1.3种类型界面的添加过程
        if (loadingView == null){
            loadingView = UIUitls.inflate(R.layout.layout_loading);
            addView(loadingView);
        }
        if (errorView == null){
            errorView = UIUitls.inflate(R.layout.layout_error);
            addView(errorView);
        }
        if (emptyView == null){
            emptyView = UIUitls.inflate(R.layout.layout_empty);
            addView(emptyView);
        }
        showPage();
    }
    //根据目前current_state所处的状态,决定展示界面内容
    private void showPage() {
        //对进度条的显示和隐藏通过状态码进行判断
        if (loadingView!=null){
            if (current_state == STATE_NONE || current_state == STATE_LOADING){
                loadingView.setVisibility(View.VISIBLE);
            }else{
                loadingView.setVisibility(View.GONE);
            }
        }
        //对加载失败的view对象显示隐藏通过状态码进行判断
        if (errorView!=null){
            errorView.setVisibility((current_state == STATE_ERROR)?View.VISIBLE:View.GONE);
        }

        //对加载为空的view对象显示隐藏通过状态码进行判断
        if (emptyView!=null){
            emptyView.setVisibility((current_state == STATE_EMPTY)?View.VISIBLE:View.GONE);
        }

        //请求成功
        if (current_state == STATE_SUCCESSED){
            //返回的就是加载数据成功后,布局文件转换成的view对象
            successedView = onCreateSuccessedView();
            if (successedView!=null){
                addView(successedView);
                successedView.setVisibility((current_state == STATE_SUCCESSED)?View.VISIBLE:View.GONE);
            }
        }
    }

    //网络请求触发方法,此方法用于获取请求的状态,此状态必须记录在current_state中,用于决定4个view中每一个view的显示隐藏
    public void show(){
        new Thread(){
            @Override
            public void run() {
                //发送网络请求?
                //首页 http://www.google.market/home.jsp?home=value
                //应用 http://www.google.market/app.jsp?app=value
                //游戏 http://www.google.market/game.jsp?game=value
                final ResultState resultState = onLoad();
//                resultState.state// 只能是2,3,4中的某一个状态码
                //获取状态码最终的目的是,根据请求结果决定页面的显示view是那个
                UIUitls.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resultState!=null){
                            current_state = resultState.state;
                            showPage();
                        }
                    }
                });
            }
        }.start();
    }
    //因为首页,应用,游戏.....所有的页面模块,在网络请求过程中,链接地址不同,参数不同,请求方式不同,解析json也不同
    public abstract ResultState onLoad();//成功  失败  成功但是没有数据(为空)  枚举
    //因为首页,应用,游戏在请求网络成功后,需要展示的页面效果完全不一样,所以不能在父类中统一写死布局文件,于是抽象
    public abstract View onCreateSuccessedView() ;

    //只能有 RESULT_STATE_ERROR RESULT_STATE_EMPTY RESULT_STATE_SUCCESSED三个对象,不能有其他情况
   public enum ResultState{
        //定义3个对象
        RESULT_STATE_ERROR(2),
        RESULT_STATE_EMPTY(3),
        RESULT_STATE_SUCCESSED(4);

        private int state;
        private ResultState(int state){
            this.state = state;
        }
    }

}
