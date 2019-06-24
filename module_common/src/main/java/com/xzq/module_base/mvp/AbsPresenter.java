package com.xzq.module_base.mvp;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.xzq.module_base.api.ApiCallback;
import com.xzq.module_base.api.BaseListBean;
import com.xzq.module_base.api.ModelService;
import com.xzq.module_base.api.NetBean;
import com.xzq.module_base.api.NetCallback;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * 基类Presenter
 */
public abstract class AbsPresenter<V> implements BasePresenter {

    protected V mView;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AbsPresenter(@NonNull V view) {
        this.mView = view;
    }

    /**
     * POST请求网络回调
     *
     * @param <E>
     */
    public abstract class PostLoadingCallback<E> extends NetCallback<E> {

        /**
         * 创建一个显示loading的POST请求网络回调
         */
        public PostLoadingCallback() {
            this(true);
        }

        /**
         * 创建一个POST请求网络回调
         *
         * @param showLoading 是否显示loading
         */
        public PostLoadingCallback(boolean showLoading) {
            super(showLoading ? (mView instanceof IPostLoadingView ? (IPostLoadingView) mView : null) : null);
        }

        /**
         * 创建一个POST请求的网络回调
         *
         * @param page 页码
         */
        public PostLoadingCallback(int page) {
            super(mView instanceof IPostLoadingView ? (IPostLoadingView) mView : null, page);
        }

        @Override
        public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
            super.onSubscribe(d);
        }
    }

    /**
     * 带状态加载的网络回调
     */
    public abstract class StateCallback<E> extends NetCallback<E> {

        /**
         * 创建一个带状态的网络回调
         */
        public StateCallback() {
            this(1);
        }

        /**
         * 创建一个带状态的网络回调
         *
         * @param page 页码
         */
        public StateCallback(int page) {
            super(mView instanceof IStateView ? (IStateView) mView : null, page);
        }

        @Override
        public void onSubscribe(Disposable d) {
            compositeDisposable.add(d);
            super.onSubscribe(d);
        }

        @Override
        protected void onSuccess(E data, String msg, int code, int page, boolean hasNextPage) {
            if (mView != null) {
                if (data instanceof List ||
                        data instanceof BaseListBean) {
                    if (isFirstPage()) {
                        onSetData(data, page, hasNextPage);
                    } else {
                        onAddData(data, page, hasNextPage);
                    }
                } else {
                    onSuccess(data, msg, code);
                }
            }
        }

        protected abstract void onSuccess(E data, String msg, int code);

        protected void onSetData(E data, int page, boolean hasNextPage) {
        }

        protected void onAddData(E data, int page, boolean hasNextPage) {
        }
    }

    /**
     * 发起网络请求，响应体{@link NetBean}中data字段为对象
     *
     * @param callback 回调获取方法
     * @param <E>      实体类型
     * @param cls      Class
     * @return Observable
     */
    protected <E> Observable<NetBean<E>> doEntityRequest(ApiCallback<E> callback, Class<E> cls) {
        return ModelService.doEntityRequest(cls, callback);
    }

    /**
     * 发起列表请求（NetBean/data为{@link java.util.List}）
     *
     * @param callback 回调获取方法
     * @param <E>      实体类型
     * @return Observable
     */
    protected <E> Observable<NetBean<E>> doListRequest(ApiCallback<E> callback) {
        return ModelService.doListRequest(callback);
    }

    /**
     * 发起分页列表请求，状态回调
     *
     * @param callback 回调获取方法
     * @param <E>
     */
    @SuppressWarnings("all")
    protected <E> void doPagingListRequest(ApiCallback<E> callback) {
        ModelService.doListRequest(callback).subscribe(new StateCallback<E>(1) {
            @Override
            protected void onSuccess(E data, String msg, int code) {
                //do nothing
            }

            @Override
            protected void onSetData(E data, int page, boolean hasNextPage) {
                if (data instanceof List && mView instanceof IListView) {
                    ((IListView) mView).setData((List) data, page, hasNextPage);
                }
            }

            @Override
            protected void onAddData(E data, int page, boolean hasNextPage) {
                if (data instanceof List && mView instanceof IListView) {
                    ((IListView) mView).addData((List) data, page, hasNextPage);
                }
            }
        });
    }

    /**
     * 发起列表请求（NetBean/data为{@link BaseListBean}）
     *
     * @param callback 回调获取方法
     * @param <E>      BaseListBean
     * @return Observable
     */
    @SuppressWarnings("all")
    @SuppressLint("CheckResult")
    protected <E> void doBaseListRequest(ApiCallback<E> callback, int page) {
        ModelService.doEntityRequest2((E) new BaseListBean<>(), callback)
                .subscribe(new StateCallback<E>(page) {
                    @Override
                    protected void onSuccess(E data, String msg, int code) {
                        //do nothing
                    }

                    @Override
                    protected void onSetData(E data, int page, boolean hasNextPage) {
                        if (mView instanceof IListView && data instanceof BaseListBean) {
                            ((IListView) mView).setData(((BaseListBean) data).getList(), page, hasNextPage);
                        }
                    }

                    @Override
                    protected void onAddData(E data, int page, boolean hasNextPage) {
                        if (mView instanceof IListView && data instanceof BaseListBean) {
                            ((IListView) mView).addData(((BaseListBean) data).getList(), page, hasNextPage);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        mView = null;
        compositeDisposable.clear();
    }
}
