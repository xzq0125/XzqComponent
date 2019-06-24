package com.xzq.module_base.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xzq.module_base.R;

import java.lang.ref.SoftReference;
import java.util.List;

import am.widget.stateframelayout.StateFrameLayout;

/**
 * 列表适配器基类，带加载更多功能
 *
 * @author xzq
 */

@SuppressWarnings("all")
public class BaseRecyclerFooterAdapter<T>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements IAdapter<T>,
        StateFrameLayout.OnStateClickListener {

    private static final int TYPE_FOOTER = 464564876;
    private LayoutInflater mInflater = null;
    protected boolean hasNext = false;//是否有下一页
    protected boolean showFooter = false;//是否显示页脚
    private boolean isLoadingMore = false;//是否正在加载更多,防止多次回调
    protected OnLoadMoreCallback loadMoreCallback;//加载更多回调
    private StateFrameLayout sflLoadMore;
    private final RecyclerView.Adapter mUserAdapter;

    public BaseRecyclerFooterAdapter(RecyclerView.Adapter adapter) {
        this(null, adapter);
    }

    public BaseRecyclerFooterAdapter(OnLoadMoreCallback loadMoreCallback, RecyclerView.Adapter adapter) {
        this.loadMoreCallback = loadMoreCallback;
        this.mUserAdapter = adapter;
        this.mUserAdapter.registerAdapterDataObserver(new MyAdapterDataObserver(new SoftReference<RecyclerView.Adapter>(this)));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }

        switch (viewType) {
            default: {
                return mUserAdapter.onCreateViewHolder(parent, viewType);
            }

            case TYPE_FOOTER: {
                View itemView = mInflater.inflate(R.layout.item_common_loadmore, parent, false);
                sflLoadMore = (StateFrameLayout) itemView;
                sflLoadMore.setLoadingDrawable(new FooterProgressDrawable(parent.getContext()));
                sflLoadMore.setOnStateClickListener(this);
                return onCreateFooterViewHolder(itemView);
            }
        }
    }

    RecyclerView.ViewHolder onCreateFooterViewHolder(View itemView) {
        return new LoadMoreViewHolder(itemView);
    }

    class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //empty
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (showFooter && isLastItem(position)) {
            if (hasNext) {
                if (loadMoreCallback != null && !isLoadingMore) {
                    isLoadingMore = true;
                    sflLoadMore.loading();
                    loadMoreCallback.onAutoLoadMore(sflLoadMore);
                }
            } else {
                sflLoadMore.empty();
            }
        } else {
            mUserAdapter.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemCount() {
        return getItemCountOfAdapter() + getItemCountOfFooter();
    }

    public int getItemCountOfAdapter() {
        return mUserAdapter == null ? 0 : mUserAdapter.getItemCount();
    }

    public int getItemCountOfFooter() {
        return showFooter ? 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (showFooter && isLastItem(position)) ? TYPE_FOOTER : mUserAdapter.getItemViewType(position);
    }

    private boolean isLastItem(int position) {
        return position == getItemCount() - 1;
    }

    @Override
    public void setData(List<T> data) {
        setData(data, false);
    }

    @Override
    public void setData(List<T> data, boolean hasNext) {
        isLoadingMore = false;
        if (data != null) {
            this.hasNext = hasNext;
            this.showFooter = hasNext && !data.isEmpty();
            if (mUserAdapter instanceof IAdapter) {
                ((IAdapter) mUserAdapter).setData(data, hasNext);
            }
        }
    }

    @Override
    public boolean addData(List<T> data, boolean hasNext) {
        isLoadingMore = false;
        boolean notifyFooter = false;
        if (data != null) {
            this.hasNext = hasNext;
            if (mUserAdapter instanceof IAdapter) {
                notifyFooter = ((IAdapter) mUserAdapter).addData(data, hasNext);
            }
            if (notifyFooter) {
                notifyItemChanged(getItemCount() - 1);
            }
        }
        return notifyFooter;
    }

    @Override
    public void onError() {
        isLoadingMore = false;
        sflLoadMore.error();
    }

    @Override
    public void onEmpty() {
        isLoadingMore = false;
        this.hasNext = false;
        notifyItemChanged(getItemCount() - 1);
    }

    @Override
    public void clear() {
        showFooter = false;
        if (mUserAdapter instanceof IAdapter) {
            ((IAdapter) mUserAdapter).clear();
        }
    }

    @Override
    public void onErrorClick(StateFrameLayout layout) {
        if (layout != null) {
            layout.loading();
        }
        if (loadMoreCallback != null) {
            loadMoreCallback.onReloadMore(layout);
        }
    }

    /**
     * 加载更多回调
     */
    public interface OnLoadMoreCallback {

        /**
         * 自动加载更多
         *
         * @param loadMore StateFrameLayout
         */
        void onAutoLoadMore(StateFrameLayout loadMore);

        /**
         * 点击重新加载更多
         *
         * @param loadMore StateFrameLayout
         */
        void onReloadMore(StateFrameLayout loadMore);

    }

    public void setLoadMoreCallback(OnLoadMoreCallback loadMoreCallback) {
        this.loadMoreCallback = loadMoreCallback;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (mUserAdapter != null) mUserAdapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (mUserAdapter != null) mUserAdapter.onDetachedFromRecyclerView(recyclerView);
    }

    private class MyAdapterDataObserver extends RecyclerView.AdapterDataObserver {

        private final SoftReference<RecyclerView.Adapter> adapterSoftReference;

        MyAdapterDataObserver(SoftReference<RecyclerView.Adapter> adapterSoftReference) {
            this.adapterSoftReference = adapterSoftReference;
        }

        public void onChanged() {
            if (alive()) {
                adapterSoftReference.get().notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            if (alive()) {
                adapterSoftReference.get().notifyItemRangeChanged(positionStart, itemCount, payload);
            }
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (alive()) {
                adapterSoftReference.get().notifyItemRangeChanged(positionStart, itemCount);
            }
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (alive()) {
                adapterSoftReference.get().notifyItemRangeInserted(positionStart, itemCount);
            }
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (alive()) {
                adapterSoftReference.get().notifyItemRangeRemoved(positionStart, itemCount);
            }
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (alive()) {
                adapterSoftReference.get().notifyItemMoved(fromPosition, toPosition);
            }
        }

        private boolean alive() {
            return adapterSoftReference != null && adapterSoftReference.get() != null;
        }
    }

}
