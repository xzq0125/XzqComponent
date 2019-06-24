package com.xzq.module_base.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xzq.module_base.R;
import com.xzq.module_base.adapter.BaseRecyclerFooterAdapter;
import com.xzq.module_base.adapter.IAdapter;
import com.xzq.module_base.mvp.BaseListContract;
import com.xzq.module_base.utils.DividerFactory;

import java.util.List;

import am.widget.stateframelayout.StateFrameLayout;

/**
 * Activity列表基类
 *
 * @author xzq
 */

public abstract class BaseListActivity<P extends BaseListContract.Presenter, T>
        extends BasePresenterActivity<P>
        implements BaseListContract.View<T>,
        StateFrameLayout.OnStateClickListener,
        OnRefreshListener,
        BaseRecyclerFooterAdapter.OnLoadMoreCallback {

    protected StateFrameLayout sfl;//状态布局
    protected RecyclerView recyclerView;
    protected SmartRefreshLayout refreshLayout;
    protected IAdapter<T> mAdapter;
    protected int mPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_list;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setToolbar(getPageTitle());
        sfl = findViewById(R.id.sfl);
        if (sfl != null) {
            sfl.setOnStateClickListener(this);
        }
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        BaseRecyclerFooterAdapter<T> wrapAdapter = new BaseRecyclerFooterAdapter<>(getPageAdapter());
        wrapAdapter.setLoadMoreCallback(this);
        mAdapter = wrapAdapter;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(wrapAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addItemDecoration(recyclerView);
        onErrorClick(null);
    }

    @Override
    public void onErrorClick(StateFrameLayout layout) {
        refresh();
    }

    @Override
    public void onFirstLoading() {
        if (sfl != null) {
            sfl.loading();
        }
    }

    @Override
    public void onFirstLoadFinish() {
        if (sfl != null) {
            sfl.normal();
        }
        refreshLayout.finishRefresh();
    }

    @Override
    public void onFirstLoadEmpty() {
        if (sfl != null) {
            sfl.empty();
        }
        if (mAdapter != null) {
            mAdapter.clear();
        }
    }

    @Override
    public void onFirstLoadError(int page, String error) {
        if (sfl != null) {
            sfl.error();
        }
        if (mAdapter != null) {
            mAdapter.clear();
        }
    }

    @Override
    public void onAutoLoadMore(StateFrameLayout loadMore) {
        onPageLoad(mPage);
    }

    @Override
    public void onReloadMore(StateFrameLayout loadMore) {
        onPageLoad(mPage);
    }

    @Override
    public void onShowLoadMoreError(int page, String error) {
        if (mAdapter != null) {
            mAdapter.onError();
        }
    }

    @Override
    public void onShowLoadMoreEmpty() {
        if (mAdapter != null) {
            mAdapter.onEmpty();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refresh();
    }

    @Override
    public void setData(List<T> list, int page, boolean hasNextPage) {
        if (hasNextPage) {
            mPage++;
        }
        if (mAdapter != null) {
            mAdapter.setData(list, hasNextPage);
        }
    }

    @Override
    public void addData(List<T> list, int page, boolean hasNextPage) {
        if (hasNextPage) {
            mPage++;
        }
        if (mAdapter != null) {
            mAdapter.addData(list, hasNextPage);
        }
    }

    public void onPageLoad(int page) {
        if (presenter != null) {
            presenter.getList(page);
        }
    }

    public void refresh() {
        mPage = 1;
        onPageLoad(mPage);
    }

    /**
     * 添加默认分割线
     *
     * @param recyclerView RecyclerView
     */
    protected void addItemDecoration(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(DividerFactory.VERTICAL);
    }

    /**
     * 获取页面标题
     *
     * @return 标题
     */
    protected abstract String getPageTitle();

    /**
     * 获取页面适配器
     *
     * @return 适配器
     */
    protected abstract RecyclerView.Adapter getPageAdapter();
}
