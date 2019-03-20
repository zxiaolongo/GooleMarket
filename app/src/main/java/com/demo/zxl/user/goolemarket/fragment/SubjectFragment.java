package com.demo.zxl.user.goolemarket.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.demo.zxl.user.goolemarket.R;
import com.demo.zxl.user.goolemarket.adapter.SubjectAdapter;
import com.demo.zxl.user.goolemarket.bean.SubjectInfo;
import com.demo.zxl.user.goolemarket.protocol.SubjectProtocol;
import com.demo.zxl.user.goolemarket.utils.UIUitls;
import com.demo.zxl.user.goolemarket.view.LoadingPage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HASEE.
 */
public class SubjectFragment extends BaseFragment {
    @BindView(R.id.rv_subject)
    RecyclerView rvSubject;

    /*@BindView(R.id.xrefreshLayout)
    XRefreshLayout xRefreshLayout;*/
    private List<SubjectInfo> subjectInfoList;

    @Override
    public LoadingPage.ResultState onSubLoad() {
        SubjectProtocol subjectProtocol = new SubjectProtocol();
        subjectInfoList = subjectProtocol.getData("subject", 0,"");
        return checkData(subjectInfoList);
    }

    @Override
    public View onSubCreateSuccessedView() {
        View view = UIUitls.inflate(R.layout.fragment_subject);
        ButterKnife.bind(this,view);

        SubjectAdapter subjectAdapter = new SubjectAdapter(
                UIUitls.getContext(), R.layout.item_subject, subjectInfoList);
        rvSubject.setLayoutManager(new LinearLayoutManager(UIUitls.getContext()));
        rvSubject.setAdapter(subjectAdapter);
        return view;
    }
}
