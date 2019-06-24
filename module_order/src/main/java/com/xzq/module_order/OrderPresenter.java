package com.xzq.module_order;

import android.support.annotation.NonNull;

import com.xzq.module_base.mvp.AbsPresenter;
import com.xzq.module_base.mvp.BaseListContract;

/**
 * OrderPresenter
 * Created by xzq on 2018/12/20.
 */
public class OrderPresenter extends AbsPresenter<BaseListContract.View>
        implements BaseListContract.Presenter {

    public OrderPresenter(@NonNull BaseListContract.View view) {
        super(view);
    }

    @Override
    public void getList(int page) {
        doBaseListRequest(api -> api.getWangAndroidHomePage(page), page);
    }
}
