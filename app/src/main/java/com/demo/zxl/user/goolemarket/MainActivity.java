package com.demo.zxl.user.goolemarket;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.demo.zxl.user.goolemarket.utils.FragmentFactory;
import com.demo.zxl.user.goolemarket.utils.UIUitls;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //1.给viewPager设置数据适配器
        MyFragmentPagerAdapter myFragmentPagerAdapter
                = new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(myFragmentPagerAdapter);
        //2.让tabLayout和viewPager绑定
        tabLayout.setupWithViewPager(viewpager);
    }
    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private String[] stringArray;
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            stringArray = UIUitls.getResource().getStringArray(R.array.tab_names);
        }
        @Override
        public Fragment getItem(int position) {
            //每一个索引位置的页面效果,Fragment产出工厂类
            return FragmentFactory.createFragment(position);
        }
        @Override
        public int getCount() {
            //页面数量
            return stringArray.length;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return stringArray[position];
        }
    }
}
