package com.xzq.module_order;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.xzq.module_base.base.BaseListFragment;
import com.xzq.module_base.bean.HomePageBean;
import com.xzq.module_base.mvp.MvpContract;
import com.xzq.module_base.utils.ToastUtils;
import com.xzq.module_base.utils.XZQLog;

/**
 * OrderFragment
 * Created by xzq on 2018/12/20.
 */
public class OrderFragment extends BaseListFragment<MvpContract.RXMVPPresenter, HomePageBean> {

    public static OrderFragment newInstance() {

        Bundle args = new Bundle();

        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MvpContract.RXMVPPresenter createPresenter() {
        return new MvpContract.RXMVPPresenter();
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
    protected String getPageTitle() {
        return null;
    }

    @Override
    protected RecyclerView.Adapter getPageAdapter() {
        return new MyAdapter().setOnItemClickListener((v, data, pos) -> ToastUtils.show(data.title + pos));
    }

}
