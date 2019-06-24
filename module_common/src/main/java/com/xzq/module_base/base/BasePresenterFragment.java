package com.xzq.module_base.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xzq.module_base.mvp.BasePresenter;

/**
 * MVP Fragment基类
 *
 * @author xzq
 */

public abstract class BasePresenterFragment<P> extends LazyLoadFragment {

    protected P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    /**
     * 创建Presenter
     *
     * @return Presenter
     */
    protected abstract P createPresenter();

    public P getPresenter() {
        return presenter;
    }

    @Override
    public void onDestroy() {
        if (presenter instanceof BasePresenter) {
            ((BasePresenter) presenter).onDestroy();
        }
        super.onDestroy();
    }

}
