package com.xzq.component.paging;

import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.xzq.module_base.arouter.RouterPath;
import com.xzq.module_base.base.BaseListActivity;
import com.xzq.module_base.bean.HomePageBean;
import com.xzq.module_base.mvp.MvpContract;

@Route(path = RouterPath.PAGING)
public class RXMVPActivity extends BaseListActivity<MvpContract.CommonPresenter, HomePageBean> {

    @Override
    protected MvpContract.RXMVPPresenter createPresenter() {
        return new MvpContract.RXMVPPresenter();
    }

    @Override
    protected String getPageTitle() {
        return "分页加载";
    }

    @Override
    protected RecyclerView.Adapter getPageAdapter() {
        return new MyAdapter().setOnItemClickListener((v, data, pos) -> ToastUtils.showShort(data.title + pos));
    }

}
