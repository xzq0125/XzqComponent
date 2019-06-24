package com.xzq.module_base.api;

import android.net.ParseException;
import android.support.annotation.NonNull;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.xzq.module_base.mvp.IPostLoadingView;
import com.xzq.module_base.mvp.IStateView;
import com.xzq.module_base.utils.ToastUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 网络回调
 *
 * @author xzq
 */
@SuppressWarnings("all")
public abstract class NetCallback<T> implements Observer<NetBean<T>> {

    private static final int FIRST_PAGE_INDEX = 1;
    //本地自定义错误码
    public static final int CODE_JSON = -125;
    public static final int CODE_TIMEOUT = -126;
    public static final int CODE_NET_BREAK = -127;
    public static final int CODE_FAILED = -128;
    private static final String DEF_LOADING_MSG = "加载中...";
    private String mLoadingMessage;
    private int mPage = FIRST_PAGE_INDEX;
    private boolean onComplete = false;
    private IPostLoadingView mPostLoading;
    private IStateView mStateLoading;

    public NetCallback() {
    }

    public NetCallback(IPostLoadingView mPostLoading) {
        this.mPostLoading = mPostLoading;
        this.mLoadingMessage = DEF_LOADING_MSG;
    }

    public NetCallback(IStateView mStateLoading, int page) {
        this.mStateLoading = mStateLoading;
        this.mPage = page;
        this.mLoadingMessage = DEF_LOADING_MSG;
    }

    public NetCallback(IPostLoadingView mPostLoading, int page) {
        this.mPostLoading = mPostLoading;
        this.mPage = page;
        this.mLoadingMessage = DEF_LOADING_MSG;
    }

    @Override
    public void onSubscribe(Disposable d) {
        callbackLoading();
    }

    @Override
    public void onComplete() {
        if (!onComplete) {
            callbackComplete();
            onComplete = true;
        }
    }

    @Override
    public void onNext(@NonNull NetBean<T> netResponse) {
        final String msg = netResponse.getMsg();
        final int code = netResponse.getStatus();
        if (netResponse.isOk()) {
            onComplete();
            T entity = netResponse.getData();
            boolean isEmpty = entity == null ||
                    (entity instanceof BaseListBean && ((BaseListBean) entity).isEmpty())
                    || entity instanceof List && ((List) entity).isEmpty();
            if (isEmpty) {
                callbackEmpty();
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
        if (e instanceof JsonSyntaxException
                || e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            error = "数据解析异常";
            code = CODE_JSON;
        } else if (e instanceof SocketTimeoutException
                || e instanceof ConnectException) {
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

        onError(error, code);
        onComplete();

        callbackError(error);
    }

    /**
     * 回调加载中
     */
    private void callbackLoading() {
        if (mPostLoading != null) {
            mPostLoading.onShowPostLoading(mLoadingMessage);
        }
        if (mStateLoading != null) {
            mStateLoading.onShowLoading(mLoadingMessage);
            if (isFirstPage()) {
                mStateLoading.onStateLoading(mLoadingMessage);
            }
        }
    }

    /**
     * 回调加载完成
     */
    private void callbackComplete() {
        if (mPostLoading != null) {
            mPostLoading.onHidePostLoading();
        }
        if (mStateLoading != null) {
            mStateLoading.onHideLoading();
            if (isFirstPage()) {
                mStateLoading.onStateNormal();
            }
        }
    }

    /**
     * 回调加载空
     */
    private void callbackEmpty() {
        if (mStateLoading != null) {
            if (isFirstPage()) {
                mStateLoading.onStateEmpty();
            } else {
                mStateLoading.onStateLoadMoreEmpty();
            }
        }
    }

    /**
     * 回调加载错误
     *
     * @param error 错误信息
     */
    private void callbackError(String error) {
        if (mStateLoading != null) {
            if (isFirstPage()) {
                mStateLoading.onStateError(mPage, error);
            } else {
                mStateLoading.onStateLoadMoreError(mPage, error);
            }
        }
    }

    /**
     * 是否是第一页
     *
     * @return 是否是第一页
     */
    protected boolean isFirstPage() {
        return mPage == FIRST_PAGE_INDEX;
    }

    /**
     * 请求返回成功
     *
     * @param data        数据实体
     * @param msg         msg
     * @param code        code
     * @param page        页码，针对分页加载页码
     * @param hasNextPage 是否还有下一页，针对分页加载页码
     */
    protected abstract void onSuccess(T data, String msg, int code, int page, boolean hasNextPage);

    /**
     * 显示默认错误信息，toast一下
     *
     * @param error 错误信息
     * @param code  错误码
     */
    protected void onError(String error, int code) {
        if (mStateLoading == null) {
            ToastUtils.show(error);
        }
    }
}
