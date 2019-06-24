package com.xzq.module_shoppingcart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xzq.module_base.base.BasePresenterFragment;
import com.xzq.module_base.utils.XZQLog;

/**
 * ShoppingCartFragment
 * Created by xzq on 2018/12/20.
 */
public class ShoppingCartFragment extends BasePresenterFragment<ShoppingCartPresenter> implements ShoppingCartContract.SCView {

    public static ShoppingCartFragment newInstance() {

        Bundle args = new Bundle();

        ShoppingCartFragment fragment = new ShoppingCartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ShoppingCartPresenter createPresenter() {
        return new ShoppingCartPresenter(this);
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
        return R.layout.fragment_shoppingcart;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void setData(Object o) {

    }
}
