package com.xzq.module_order;

import android.support.annotation.NonNull;

import com.xzq.module_base.mvp.AbsPresenter;

/**
 * OrderPresenter
 * Created by xzq on 2018/12/20.
 */
public class OrderPresenter extends AbsPresenter<OrderContract.OView>
        implements OrderContract.Presenter {

    public OrderPresenter(@NonNull OrderContract.OView view) {
        super(view);
    }
}
