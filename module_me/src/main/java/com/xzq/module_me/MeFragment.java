package com.xzq.module_me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xzq.module.User;
import com.xzq.module_base.arouter.RouterUtils;
import com.xzq.module_base.base.BasePresenterFragment;
import com.xzq.module_base.utils.XZQLog;

/**
 * MeFragment
 * Created by xzq on 2018/12/20.
 */
public class MeFragment extends BasePresenterFragment<MePresenter> implements MeContract.MView {

    public static MeFragment newInstance() {

        Bundle args = new Bundle();

        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MePresenter createPresenter() {
        return new MePresenter(this);
    }

    @Override
    protected void isVisibleToUser(boolean isVisibleToUser) {
        super.isVisibleToUser(isVisibleToUser);
        XZQLog.debug("isVisibleToUser  = " + isVisibleToUser);
        if (isVisibleToUser && !User.isLogin()) {
            RouterUtils.openLogin();
        }
    }

    @Override
    protected void loadData() {
        XZQLog.debug("loadData");
    }

    @Override
    protected int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_me;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void setData(Object o) {

    }
}
