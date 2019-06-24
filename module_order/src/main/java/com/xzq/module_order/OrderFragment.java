package com.xzq.module_order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xzq.module_base.base.BasePresenterFragment;
import com.xzq.module_base.utils.XZQLog;

/**
 * OrderFragment
 * Created by xzq on 2018/12/20.
 */
public class OrderFragment extends BasePresenterFragment<OrderPresenter> implements OrderContract.OView {

    public static OrderFragment newInstance() {

        Bundle args = new Bundle();

        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected OrderPresenter createPresenter() {
        return new OrderPresenter(this);
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
        return R.layout.fragment_order;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void setData(Object o) {

    }
}
