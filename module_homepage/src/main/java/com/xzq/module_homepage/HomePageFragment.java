package com.xzq.module_homepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xzq.module_base.arouter.RouterUtils;
import com.xzq.module_base.base.BasePresenterFragment;
import com.xzq.module_base.utils.XZQLog;

/**
 * HomePageFragment
 * Created by xzq on 2018/12/20.
 */
public class HomePageFragment extends BasePresenterFragment  {

    public static HomePageFragment newInstance() {

        Bundle args = new Bundle();

        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void isVisibleToUser(boolean isVisibleToUser) {
        super.isVisibleToUser(isVisibleToUser);
        XZQLog.debug("isVisibleToUser  = " + isVisibleToUser);
    }

    @Override
    protected void loadData() {
        XZQLog.debug("loadData");
    }

    @Override
    protected int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        TextView tv = findViewById(R.id.tv);
        tv.setOnClickListener(v -> RouterUtils.openMvp(me, 10));
    }

}
