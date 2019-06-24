package com.xzq.module_shoppingcart;

import android.support.annotation.NonNull;

import com.xzq.module_base.mvp.AbsPresenter;

/**
 * ShoppingCartPresenter
 * Created by xzq on 2018/12/20.
 */
public class ShoppingCartPresenter extends AbsPresenter<ShoppingCartContract.SCView>
        implements ShoppingCartContract.Presenter {

    public ShoppingCartPresenter(@NonNull ShoppingCartContract.SCView view) {
        super(view);
    }
}
