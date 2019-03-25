package com.demo.zxl.user.goolemarket.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demo.zxl.user.goolemarket.R;
import com.demo.zxl.user.goolemarket.adapter.CategoryAdapter;
import com.demo.zxl.user.goolemarket.adapter.TextItemViewDelegate;
import com.demo.zxl.user.goolemarket.adapter.TextPicItemViewDelegate;
import com.demo.zxl.user.goolemarket.bean.CategoryInfo;
import com.demo.zxl.user.goolemarket.protocol.CategoryProtocol;
import com.demo.zxl.user.goolemarket.utils.UIUitls;
import com.demo.zxl.user.goolemarket.view.LoadingPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HASEE.
 */
public class CategoryFragment extends BaseFragment {
    @BindView(R.id.rv_category)
    RecyclerView rvCategory;
    private List<CategoryInfo> categoryInfoList;
    private ArrayList<Object> categoryList;

    @Override
    public LoadingPage.ResultState onSubLoad() {
        CategoryProtocol categoryProtocol = new CategoryProtocol();
        categoryInfoList = categoryProtocol.getData("category", 0,"");
        initData(categoryInfoList);
        return checkData(categoryInfoList);
    }

    private void initData(List<CategoryInfo> categoryInfoList) {
        categoryList = new ArrayList<>();
        if (categoryInfoList==null) {
            return;
        }
        for (int i = 0; i < categoryInfoList.size(); i++) {
            CategoryInfo categoryInfo = categoryInfoList.get(i);
            //添加标题
            categoryList.add(categoryInfo.title);
            //添加infos中维护的多个对象
            categoryList.addAll(categoryInfo.infos);
        }
    }

    @Override
    public View onSubCreateSuccessedView() {
        View view = UIUitls.inflate(R.layout.fragment_category);
        ButterKnife.bind(this,view);

        CategoryAdapter categoryAdapter = new CategoryAdapter(UIUitls.getContext(),categoryList);
        //指定不同类型条目
        //指定存文本类型条目
        categoryAdapter.addItemViewDelegate(new TextItemViewDelegate());
        //指定图片+文本类型条目
        categoryAdapter.addItemViewDelegate(new TextPicItemViewDelegate());

        rvCategory.setLayoutManager(new LinearLayoutManager(UIUitls.getContext()));
        rvCategory.setAdapter(categoryAdapter);
        return view;
    }
}
