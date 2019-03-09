package com.demo.zxl.user.goolemarket.utils;

import android.support.v4.app.Fragment;

import com.demo.zxl.user.goolemarket.fragment.AppFragment;
import com.demo.zxl.user.goolemarket.fragment.BaseFragment;
import com.demo.zxl.user.goolemarket.fragment.CategoryFragment;
import com.demo.zxl.user.goolemarket.fragment.GameFragment;
import com.demo.zxl.user.goolemarket.fragment.HomeFragment;
import com.demo.zxl.user.goolemarket.fragment.HotFragment;
import com.demo.zxl.user.goolemarket.fragment.RecommendFragment;
import com.demo.zxl.user.goolemarket.fragment.SubjectFragment;

import java.util.HashMap;

/**
 * Created by HASEE.
 */
public class FragmentFactory {
    private static HashMap<Integer,BaseFragment> fragmentHashMap = new HashMap<>();
    public static Fragment createFragment(int position) {
        BaseFragment fragment = fragmentHashMap.get(position);
        if (fragment != null){
            return fragment;
        }else{
            switch (position){
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new AppFragment();
                    break;
                case 2:
                    fragment = new GameFragment();
                    break;
                case 3:
                    fragment = new SubjectFragment();
                    break;
                case 4:
                    fragment = new RecommendFragment();
                    break;
                case 5:
                    fragment = new CategoryFragment();
                    break;
                case 6:
                    fragment = new HotFragment();
                    break;
            }
            fragmentHashMap.put(position,fragment);
            return fragment;
        }
    }
}
