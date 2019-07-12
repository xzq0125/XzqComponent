package com.xzq.module_base.base;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xzq.module_base.R;
import com.xzq.module_base.adapter.BaseRecyclerFooterAdapter;
import com.xzq.module_base.adapter.IAdapter;
import com.xzq.module_base.mvp.IListView;
import com.xzq.module_base.mvp.MvpContract;
import com.xzq.module_base.utils.DividerFactory;

import java.util.List;

import am.widget.stateframelayout.StateFrameLayout;

/**
 * Fragment列表基类
 *
 * @author xzq
 */

public abstract class BaseListFragment<P extends MvpContract.CommonPresenter, T>
        extends BasePresenterFragment<P>
        implements IListView<T>,
        BaseRecyclerFooterAdapter.OnLoadMoreCallback {

    protected RecyclerView recyclerView;
    protected IAdapter<T> mAdapter;
    protected int mPage = 1;

    @Override
    protected int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.activity_base_list;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setRefreshEnable(true);
        BaseRecyclerFooterAdapter<T> wrapAdapter = new BaseRecyclerFooterAdapter<>(getPageAdapter());
        wrapAdapter.setLoadMoreCallback(this);
        mAdapter = wrapAdapter;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(wrapAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(me));
        initRecyclerView(recyclerView);
    }

    @Override
    protected void getFirstPageData() {
        refresh();
    }

    @Override
    public void onStateEmpty() {
        super.onStateEmpty();
        if (mAdapter != null) {
            mAdapter.clear();
        }
    }

    @Override
    public void onStateError(int page, String error) {
        super.onStateError(page, error);
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
    public void onStateLoadMoreError(int page, String error) {
        super.onStateLoadMoreError(page, error);
        if (mAdapter != null) {
            mAdapter.onError();
        }
    }

    @Override
    public void onStateLoadMoreEmpty() {
        super.onStateLoadMoreEmpty();
        if (mAdapter != null) {
            mAdapter.onEmpty();
        }
    }

    @Override
    public void setData(List<T> list, int page, boolean hasNextPage, int totalCount) {
        if (hasNextPage) {
            mPage++;
        }
        if (mAdapter != null) {
            mAdapter.setData(list, hasNextPage);
        }
    }

    @Override
    public void addData(List<T> list, int page, boolean hasNextPage, int totalCount) {
        if (hasNextPage) {
            mPage++;
        }
        if (mAdapter != null) {
            mAdapter.addData(list, hasNextPage);
        }
    }

    public void onPageLoad(int page) {
        if (presenter != null) {
            presenter.setPage(page);
            presenter.getList();
        }
    }

    public void refresh() {
        mPage = 1;
        onPageLoad(mPage);
    }

    /**
     * 初始化RecyclerView
     *
     * @param recyclerView RecyclerView
     */
    protected void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.addItemDecoration(DividerFactory.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(me));
    }

    /**
     * 获取页面适配器
     *
     * @return 适配器
     */
    protected abstract RecyclerView.Adapter getPageAdapter();

}
