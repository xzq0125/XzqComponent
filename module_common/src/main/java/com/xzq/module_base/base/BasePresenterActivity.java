package com.xzq.module_base.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xzq.module_base.mvp.MvpContract;


/**
 * MVP Activity基类
 *
 * @author xzq
 */

public abstract class BasePresenterActivity<P extends MvpContract.CommonPresenter> extends BaseActivity {

    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        presenter.attachView(this);
        super.onCreate(savedInstanceState);
    }

    @SuppressWarnings("unchecked")
    protected P createPresenter() {
        return (P) new MvpContract.CommonPresenter();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }

}
