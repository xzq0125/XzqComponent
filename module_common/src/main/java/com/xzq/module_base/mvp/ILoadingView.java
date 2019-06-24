package com.xzq.module_base.mvp;

/**
 * 加载框接口，适用于发送类似Post请求时显示加载框
 * <p>
 * 网络回调使用{@link io.reactivex.Observer}的子类
 * {@link com.gxkeji.ob.base.mvp.BasePresenter.PostLoadingCallback}
 * 作为观察者时会触发onShowPostLoading和onHidePostLoading回调
 *
 * @author xzq
 */
public interface ILoadingView {
    /**
     * 显示Loading回调
     *
     * @param loadingMessage 加载提示
     */
    void onShowLoading(String loadingMessage);

    /**
     * 隐藏Loading回调
     */
    void onHideLoading();
}
