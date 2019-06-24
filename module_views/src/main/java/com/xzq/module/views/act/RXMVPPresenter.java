package com.xzq.module.views.act;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.xzq.module_base.bean.HomePageBean;
import com.xzq.module_base.mvp.AbsPresenter;
import com.xzq.module_base.mvp.BaseListContract;


/**
 * RXMVPPresenter
 * Created by Wesley on 2018/7/10.
 */
public class RXMVPPresenter extends AbsPresenter<RXMVPContract.View> implements BaseListContract.Presenter {

    RXMVPPresenter(@NonNull RXMVPContract.View view) {
        super(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getList(final int page) {
        doEntityRequest(api -> api.getWangAndroidHomePage(page), HomePageBean.class)
                .subscribeWith(new PagingNetCallback<HomePageBean>(page) {

                    @Override
                    protected void onSetData(HomePageBean data, int page, boolean hasNextPage) {
                        mView.setData(data.datas, page, hasNextPage);
                    }

                    @Override
                    protected void onAddData(HomePageBean data, int page, boolean hasNextPage) {
                        mView.addData(data.datas, page, hasNextPage);
                    }
                });
    }
}
