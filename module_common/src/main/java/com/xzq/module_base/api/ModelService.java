package com.xzq.module_base.api;


import java.util.Collections;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * ModelService
 *
 * @author xzq
 */
public class ModelService {

    private static <T> ObservableTransformer<T, T> schedulersTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 发起网络请求，响应体{@link NetBean}中data字段为对象
     */
    public static <T> Observable<NetBean<T>> doEntityRequest(Class<T> cls, ApiCallback<T> apiCallback) {
        ApiService api = NetManager.retrofit().create(ApiService.class);
        return apiCallback.getApi(api)
                .compose(schedulersTransformer())
                .map(netBean -> {
                    T data = netBean.getData();
                    if (data == null) {
                        //data为空处理
                        NetBean<T> bean = new NetBean<>();
                        bean.copyFrom(netBean);
                        bean.setData(cls.newInstance());
                        return bean;
                    }
                    return netBean;
                });
    }

    /**
     * 发起网络请求，响应体{@link NetBean}中data字段为{@link java.util.List}
     */
    @SuppressWarnings("all")
    public static <T> Observable<NetBean<T>> doListRequest(ApiCallback<T> apiCallback) {
        ApiService api = NetManager.retrofit().create(ApiService.class);
        return apiCallback.getApi(api)
                .compose(schedulersTransformer())
                .map(netBean -> {
                    T data = netBean.getData();
                    if (data == null) {
                        //data为空处理
                        //注意：此处不检查数组元素类型，只做空列表处理
                        NetBean bean = new NetBean<>();
                        bean.copyFrom(netBean);
                        bean.setData(Collections.EMPTY_LIST);
                        return bean;
                    }
                    return netBean;
                });
    }

    /**
     * 发起网络请求，响应体{@link NetBean}中data字段为对象
     */
    public static <T> Observable<NetBean<T>> doEntityRequest2(T o, ApiCallback<T> apiCallback) {
        ApiService api = NetManager.retrofit().create(ApiService.class);
        return apiCallback.getApi(api)
                .compose(schedulersTransformer())
                .map(netBean -> {
                    T data = netBean.getData();
                    if (data == null) {
                        NetBean<T> bean = new NetBean<>();
                        bean.copyFrom(netBean);
                        bean.setData(o);
                        return bean;
                    }
                    return netBean;
                });
    }
}
