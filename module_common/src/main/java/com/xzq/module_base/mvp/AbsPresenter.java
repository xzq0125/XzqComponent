package com.xzq.module_base.mvp;

import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.xzq.module_base.api.ModelService;
import com.xzq.module_base.api.NetBean;
import com.xzq.module_base.api.NetCallback;
import com.xzq.module_base.base.lifecycle.ILifeCycleProviderSupplier;

import java.util.List;

import io.reactivex.Observable;


/**
 * 基类Presenter
 */
public abstract class AbsPresenter<V> implements ILifeCycleProviderSupplier, BasePresenter {

    protected V mView;
    protected LifecycleProvider<?> provider;

    public AbsPresenter(@NonNull V view) {
        this.mView = view;
        if (view instanceof ILifeCycleProviderSupplier) {
            provider = ((ILifeCycleProviderSupplier) view).getLifecycleProvider();
        }
    }

    @Override
    public LifecycleProvider<?> getLifecycleProvider() {
        return provider;
    }

    /**
     * 发起实体请求
     *
     * @param callback 回调获取方法
     * @param <T>      实体类型
     * @param cls      Class
     * @return Observable
     */
    protected <T> Observable<NetBean<T>> doEntityRequest(ModelService.MethodCallback<T> callback, Class<T> cls) {
        return ModelService.getEntity(getLifecycleProvider(), callback, cls);
    }

    /**
     * 发起列表请求
     *
     * @param callback 回调获取方法
     * @param <T>      实体类型
     * @return Observable
     */
    protected <T> Observable<NetBean<T>> doListRequest(ModelService.MethodCallback<T> callback) {
        return ModelService.getList(getLifecycleProvider(), callback);
    }

    /**
     * 发起列表请求
     *
     * @param callback 回调获取方法
     * @param <T>      实体类型
     * @return Observable
     */
    @SuppressWarnings("all")
    protected <T> void doListRequest(ModelService.MethodCallback<T> callback, int page) {
        ModelService.getList(getLifecycleProvider(), callback)
                .subscribeWith(new PagingNetCallback<T>(page) {
                    @Override
                    protected void onSetData(T data, int page, boolean hasNextPage) {
                        if (mView instanceof IListView) {
                            ((IListView) mView).setData((List) data, page, hasNextPage);
                        }
                    }

                    @Override
                    protected void onAddData(T data, int page, boolean hasNextPage) {
                        if (mView instanceof IListView) {
                            ((IListView) mView).addData((List) data, page, hasNextPage);
                        }
                    }
                });
    }

    /**
     * 实体加载回调
     *
     * @param <T>
     */
    public abstract class EntityNetCallback<T> extends NetCallback<T> {

        public EntityNetCallback() {
            super(mView instanceof ILoadingEntityView ? (ILoadingEntityView) mView : null, 1);
        }

        @Override
        protected void onStart() {
            if (mView != null)
                super.onStart();
        }

        @Override
        public void onComplete() {
            if (mView != null)
                super.onComplete();
        }

        @Override
        protected void onError(String error, int code) {
            if (mView != null)
                super.onError(error, code);
        }

        @Override
        public void onNext(@NonNull NetBean<T> netResponse) {
            if (mView != null)
                super.onNext(netResponse);
        }

    }

    /**
     * 分页加载回调
     *
     * @param <T>
     */
    public abstract class PagingNetCallback<T> extends NetCallback<T> {

        public PagingNetCallback(int page) {
            super(mView instanceof ILoadingEntityView ? (ILoadingEntityView) mView : null, page);
        }

        @Override
        protected void onStart() {
            if (mView != null)
                super.onStart();
        }

        @Override
        public void onComplete() {
            if (mView != null)
                super.onComplete();
        }

        @Override
        protected void onError(String error, int code) {
            if (mView != null)
                super.onError(error, code);
        }

        @Override
        public void onNext(@NonNull NetBean<T> netResponse) {
            if (mView != null)
                super.onNext(netResponse);
        }

        @Override
        protected void onSuccess(T data, String msg, int code, int page, boolean hasNextPage) {
            if (mView != null) {
                if (isFirstPage()) {
                    onSetData(data, page, hasNextPage);
                } else {
                    onAddData(data, page, hasNextPage);
                }
            }
        }

        protected abstract void onSetData(T data, int page, boolean hasNextPage);

        protected abstract void onAddData(T data, int page, boolean hasNextPage);
    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
