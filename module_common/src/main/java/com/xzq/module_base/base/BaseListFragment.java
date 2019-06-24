package com.xzq.module_base.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Fragment列表基类
 *
 * @author xzq
 */

public abstract class BaseListFragment<P extends BaseListContract.Presenter, T>
        extends BasePresenterFragment<P>
        implements BaseListContract.View<T>,
        StateFrameLayout.OnStateClickListener,
        BaseRecyclerFooterAdapter.OnLoadMoreCallback, OnRefreshListener {

    StateFrameLayout sfl;//状态布局
    RecyclerView recyclerView;
    SmartRefreshLayout refreshLayout;
    private IAdapter<T> mAdapter;
    private int mPage = 1;

    @Override
    protected int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return getLayoutId();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        View root = getView();
        if (root == null) {
            return;
        }
        sfl = root.findViewById(R.id.sfl);
        recyclerView = root.findViewById(R.id.recyclerView);
        refreshLayout = root.findViewById(R.id.refreshLayout);

        if (sfl != null) {
            sfl.setOnStateClickListener(this);
        }
        refreshLayout.setOnRefreshListener(this);
        BaseRecyclerFooterAdapter<T> wrapAdapter = new BaseRecyclerFooterAdapter<>((RecyclerView.Adapter) getPageAdapter());
        wrapAdapter.setLoadMoreCallback(this);
        mAdapter = wrapAdapter;
        recyclerView.setAdapter(wrapAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(me));
        addItemDecoration(recyclerView);
        onErrorClick(null);
    }

    @Override
    public void onErrorClick(StateFrameLayout layout) {
        refreshLayout.autoRefresh(1);
    }

    @Override
    public void onFirstLoading() {
        //empty
    }

    @Override
    public void onFirstLoadFinish() {
        if (sfl != null) {
            sfl.normal();
        }
        refreshLayout.finishRefresh();
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
    public void onFirstLoadEmpty() {
        if (sfl != null) {
            sfl.empty();
        }
        if (mAdapter != null) {
            mAdapter.clear();
        }
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

    @Override
    public void onAutoLoadMore(StateFrameLayout loadMore) {
        onPageLoad(mPage);
    }

    @Override
    public void onReloadMore(StateFrameLayout loadMore) {
        onPageLoad(mPage);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refresh();
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

    protected int getLayoutId() {
        return R.layout.layout_common_list;
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
     * 获取页面适配器
     *
     * @return 适配器
     */
    protected abstract RecyclerView.Adapter getPageAdapter();

}
