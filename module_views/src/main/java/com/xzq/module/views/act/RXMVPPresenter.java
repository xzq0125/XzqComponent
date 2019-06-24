package com.xzq.module.views.act;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.xzq.module_base.mvp.AbsPresenter;
import com.xzq.module_base.mvp.BaseListContract;


/**
 * RXMVPPresenter
 * Created by Wesley on 2018/7/10.
 */
public class RXMVPPresenter extends AbsPresenter<RXMVPContract.View> implements BaseListContract.Presenter {

    public RXMVPPresenter(@NonNull RXMVPContract.View view) {
        super(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getList(final int page) {
        doBaseListRequest(api -> api.getWangAndroidHomePage(page), page);
    }
}
