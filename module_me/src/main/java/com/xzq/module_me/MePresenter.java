package com.xzq.module_me;

import android.support.annotation.NonNull;

import com.xzq.module_base.mvp.AbsPresenter;

/**
 * MePresenter
 * Created by xzq on 2018/12/20.
 */
public class MePresenter extends AbsPresenter<MeContract.MView>
        implements MeContract.Presenter {

    public MePresenter(@NonNull MeContract.MView view) {
        super(view);
    }
}
