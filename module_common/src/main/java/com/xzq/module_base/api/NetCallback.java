package com.xzq.module_base.api;

import android.support.annotation.NonNull;

import com.google.gson.JsonSyntaxException;
import com.xzq.module_base.mvp.BaseListBean;
import com.xzq.module_base.mvp.ILoadingEntityView;
import com.xzq.module_base.mvp.ILoadingListView;
import com.xzq.module_base.utils.XZQLog;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.observers.ResourceObserver;

/**
 * 网络响应基础回调
 * Created by Wesley on 2018/7/9.
 */

public abstract class NetCallback<Entity> extends ResourceObserver<NetBean<Entity>> {

    private static final int FIRST_PAGE_INDEX = 1;
    //本地自定义错误码
    public static final int CODE_JSON = -125;
    public static final int CODE_TIMEOUT = -126;
    public static final int CODE_NET_BREAK = -127;
    public static final int CODE_FAILED = -128;
    private static final String DEF_LOADING_MSG = "加载中...";
    private ILoadingListView mLoadingListView;
    private ILoadingEntityView mLoadingView;
    private String mLoadingMessage;
    private int mPage = FIRST_PAGE_INDEX;
    private boolean onComplete = false;

    public NetCallback() {
        this(null, DEF_LOADING_MSG);
    }

    public NetCallback(String loadingMessage) {
        this(null, loadingMessage);
    }

    public NetCallback(ILoadingEntityView loadingView) {
        this(loadingView, DEF_LOADING_MSG);
    }

    public NetCallback(ILoadingEntityView loadingView, String loadingMessage) {
        this(loadingView, loadingMessage, FIRST_PAGE_INDEX);
    }

    public NetCallback(ILoadingEntityView loadingView, int page) {
        this(loadingView, DEF_LOADING_MSG, page);
    }

    public NetCallback(ILoadingEntityView loadingView, String loadingMessage, int page) {
        this.mLoadingView = loadingView;
        this.mLoadingMessage = loadingMessage;
        this.mPage = page;
        if (page > 0 && loadingView instanceof ILoadingListView) {
            this.mLoadingListView = (ILoadingListView) loadingView;
        }
    }

    @Override
    protected void onStart() {
        if (mLoadingView != null) {
            mLoadingView.onShowLoading(mLoadingMessage);
        }
        if (mLoadingListView != null && isFirstPage()) {
            mLoadingListView.onFirstLoading();
        }
    }

    @Override
    public void onComplete() {
        if (!onComplete) {
            complete();
            onComplete = true;
        }
    }

    private void complete() {
        if (mLoadingView != null) {
            mLoadingView.onHideLoading();
        }
        if (mLoadingListView != null && isFirstPage()) {
            mLoadingListView.onFirstLoadFinish();
        }
    }

    @Override
    public void onNext(@NonNull NetBean<Entity> netResponse) {
        final String msg = netResponse.getMsg();
        final int code = netResponse.getStatus();
        if (netResponse.isOk()) {
            onComplete();
            Entity entity = netResponse.getData();
            boolean isEmpty = entity == null ||
                    entity instanceof List && ((List) entity).isEmpty();
            if (isEmpty) {
                if (mLoadingView != null) {
                    mLoadingView.onShowEmpty();
                }
                if (mLoadingListView != null) {
                    if (isFirstPage()) {
                        mLoadingListView.onFirstLoadEmpty();
                    } else {
                        mLoadingListView.onShowLoadMoreEmpty();
                    }
                }
            }
            boolean hasNextPage = netResponse.hasNextPage(mPage);
            if (entity instanceof BaseListBean) {
                hasNextPage = ((BaseListBean) entity).hasNextPage(mPage);
            }
            onSuccess(entity, msg, code, mPage, hasNextPage);

        } else {
            onError(new ErrorCodeException(msg, code));
        }
    }

    @Override
    public void onError(Throwable e) {
        //返回错误信息
        String error;
        int code;
        if (e instanceof JsonSyntaxException) {
            error = "数据解析异常";
            code = CODE_JSON;
        } else if (e instanceof SocketTimeoutException) {
            error = "请求超时";
            code = CODE_TIMEOUT;
        } else if (e instanceof UnknownHostException) {
            error = "网络已断开";
            code = CODE_NET_BREAK;
        } else if (e instanceof ErrorCodeException) {
            ErrorCodeException ex = (ErrorCodeException) e;
            error = ex.getMessage();
            code = ex.errorCode();
        } else {
            error = "未知错误";
            code = CODE_FAILED;
        }

        XZQLog.debug("NetCallback", e.getMessage());

        onError(error, code);
        onComplete();

        if (mLoadingView != null) {
            mLoadingView.onShowError(error, mPage);
        }
        if (mLoadingListView != null) {
            if (isFirstPage())
                mLoadingListView.onFirstLoadError(mPage, error);
            else
                mLoadingListView.onShowLoadMoreError(mPage, error);
        }
    }

    protected boolean isFirstPage() {
        return mPage == FIRST_PAGE_INDEX;
    }

    protected abstract void onSuccess(Entity data, String msg, int code, int page, boolean hasNextPage);

    protected void onError(String error, int code) {
        //ToastUtils.show(error);
    }

}
