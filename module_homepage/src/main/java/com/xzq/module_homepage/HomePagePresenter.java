package com.xzq.module_homepage;

import android.support.annotation.NonNull;

import com.xzq.module_base.mvp.AbsPresenter;

/**
 * HomePagePresenter
 * Created by xzq on 2018/12/20.
 */
public class HomePagePresenter extends AbsPresenter<HomePageContract.HPView>
        implements HomePageContract.Presenter {

    public HomePagePresenter(@NonNull HomePageContract.HPView view) {
        super(view);
    }
}
