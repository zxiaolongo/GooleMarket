package com.demo.zxl.user.goolemarket.fragment;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import com.demo.zxl.user.goolemarket.protocol.RecommendProtocol;
import com.demo.zxl.user.goolemarket.utils.UIUitls;
import com.demo.zxl.user.goolemarket.view.LoadingPage;
import com.demo.zxl.user.goolemarket.view.random.randomlayout.StellarMap;

import java.util.List;
import java.util.Random;

/**
 * Created by HASEE.
 */
public class RecommendFragment extends BaseFragment {

    private List<String> recommendList;

    @Override
    public LoadingPage.ResultState onSubLoad() {
        RecommendProtocol recommendProtocol = new RecommendProtocol();
        recommendList = recommendProtocol.getData("recommend", 0,"");
        return checkData(recommendList);
    }

    @Override
    public View onSubCreateSuccessedView() {
        //添加飞入飞出自定义控件
        //1.创建一个飞入飞出控件
        StellarMap stellarMap = new StellarMap(UIUitls.getContext());

        //2.给控件的内部设置多个TextView,设置数据适配器方式给予内容
        MyAdapter myAdapter = new MyAdapter();
        stellarMap.setAdapter(myAdapter);

        //3.约定从第几组开始去执行动画
        stellarMap.setGroup(0,true);
        stellarMap.setRegularity(5,5);
        //需要返回的就是自定义控件
        return stellarMap;
    }

    class MyAdapter implements StellarMap.Adapter{
        private int pagesize = 10;
        @Override
        public int getGroupCount() {
            //获取组的数量,如果能够整除pagesize则是10的倍数,整除的值就是组的数量
            //否则组的数量是整除后的结果+1
            if (recommendList.size()%pagesize == 0){
                return recommendList.size()/pagesize;
            }
            return recommendList.size()/pagesize+1;
        }

        @Override
        public int getCount(int group) {
            //返回每一组中的textView的个数,最后一组可能是没有整除pagesize的余数所在的组
            //group组的索引位置
            if (group == getGroupCount()-1){
                return recommendList.size()%pagesize;
            }
            return pagesize;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            //group 组号
            //position 组中的编号

            //每一组中的字符串数量*组编号+索引位置 = 在第group组(从0计算)中的第position号(从0计算)字符串编号
            String str = recommendList.get(pagesize * group + position);
            TextView textView = new TextView(UIUitls.getContext());
            //将字符串内容设置给控件
            textView.setText(str);

            //给字符串设置随机大小
            int randomTextSize = 12 + new Random().nextInt(20);//[12,31]
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,randomTextSize);
            //给字符串设置随机颜色
            int red = 30+new Random().nextInt(190);//[30,219]
            int green = 30+new Random().nextInt(190);//[30,219]
            int blue = 30+new Random().nextInt(190);//[30,219]
            int rgb = Color.rgb(red, green, blue);

            textView.setTextColor(rgb);
            return textView;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            //指定飞入动画的下一组是那个一组

            //如果现在group的值指向了最后一组的索引位置,则需要将其group的值修改为-1
            if (recommendList.size()/pagesize == group){
                group = -1;
            }
            return group+1;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            //指定飞出动画的下一组是那个一组
            if (recommendList.size()/pagesize == group){
                group = -1;
            }
            return group+1;
        }
    }
}
