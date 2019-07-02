package com.xzq.module.views.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.xzq.module_base.arouter.Router;
import com.xzq.module_base.arouter.RouterUtils;
import com.xzq.module_base.base.BaseListActivity;
import com.xzq.module_base.bean.HomePageBean;
import com.xzq.module_base.mvp.MvpContract;
import com.xzq.module_base.utils.ToastUtils;
import com.xzq.module_base.utils.XZQLog;

@Route(path = Router.Path.MVP)
public class RXMVPActivity extends BaseListActivity<MvpContract.CommonPresenter, HomePageBean> {

    @Autowired(name = Router.Extra.MVP_POS)
    int pos;

    @Override
    protected MvpContract.RXMVPPresenter createPresenter() {
        return new MvpContract.RXMVPPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        RouterUtils.inject(this);
        XZQLog.debug("Router pos = " + pos);
        int pos = getIntent().getIntExtra(Router.Extra.MVP_POS, -1);
        XZQLog.debug("getIntent pos = " + pos);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getPageTitle() {
        return "分页加载";
    }

    @Override
    protected RecyclerView.Adapter getPageAdapter() {
        return new MyAdapter().setOnItemClickListener((v, data, pos) -> ToastUtils.show(data.title + pos));
    }

}
