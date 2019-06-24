package com.xzq.module_base.api;


import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.Collections;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * ModelService
 * Created by Wesley on 2018/7/9.
 */
public class ModelService {

    public interface MethodCallback<T> {
        /**
         * 获取调用方法
         *
         * @param api Api服务
         * @return Observable
         */
        Observable<NetBean<T>> getApi(ApiService api);
    }

    private static <T> ObservableTransformer<T, T> schedulersTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable<NetBean<T>> get(LifecycleProvider<?> provider, MethodCallback<T> callback) {
        ApiService api = NetManager.retrofit().create(ApiService.class);
        return callback.getApi(api)
                .compose(provider.bindToLifecycle())
                .compose(ModelService.schedulersTransformer());
    }

    public static <T> Observable<NetBean<T>> get(MethodCallback<T> callback) {
        ApiService api = NetManager.retrofit().create(ApiService.class);
        return callback.getApi(api)
                .compose(ModelService.schedulersTransformer());
    }

    public static <T> Observable<NetBean<T>> getEntity(LifecycleProvider<?> provider, MethodCallback<T> callback, Class<T> cls) {
        ApiService api = NetManager.retrofit().create(ApiService.class);
        return callback.getApi(api)
                .compose(provider.bindToLifecycle())
                .compose(ModelService.schedulersTransformer())
                .map(netBean -> {
                    T data = netBean.getData();
                    if (data == null) {
                        NetBean<T> bean = new NetBean<>();
                        bean.copyFrom(netBean);
                        bean.setData(cls.newInstance());
                        return bean;
                    }
                    return netBean;
                });
    }

    @SuppressWarnings("unchecked")
    public static <T> Observable<NetBean<T>> getList(LifecycleProvider<?> provider, MethodCallback<T> callback) {
        ApiService api = NetManager.retrofit().create(ApiService.class);
        return callback.getApi(api)
                .compose(provider.bindToLifecycle())
                .compose(ModelService.schedulersTransformer())
                .map(netBean -> {
                    T data = netBean.getData();
                    if (data == null) {
                        //注意：此处不检查数组元素类型，只做空列表处理
                        NetBean bean = new NetBean<>();
                        bean.copyFrom(netBean);
                        bean.setData(Collections.EMPTY_LIST);
                        return bean;
                    }
                    return netBean;
                });
    }

    public static <T> Observable<NetBean<T>> getEntity(MethodCallback<T> callback, Class<T> cls) {
        ApiService api = NetManager.retrofit().create(ApiService.class);
        return callback.getApi(api)
                .compose(ModelService.schedulersTransformer())
                .map(netBean -> {
                    T data = netBean.getData();
                    if (data == null) {
                        NetBean<T> bean = new NetBean<>();
                        bean.copyFrom(netBean);
                        bean.setData(cls.newInstance());
                        return bean;
                    }
                    return netBean;
                });
    }

    @SuppressWarnings("unchecked")
    public static <T> Observable<NetBean<T>> getList(MethodCallback<T> callback) {
        ApiService api = NetManager.retrofit().create(ApiService.class);
        return callback.getApi(api)
                .compose(ModelService.schedulersTransformer())
                .map(netBean -> {
                    T data = netBean.getData();
                    if (data == null) {
                        //注意：此处不检查数组元素类型，只做空列表处理
                        NetBean bean = new NetBean<>();
                        bean.copyFrom(netBean);
                        bean.setData(Collections.EMPTY_LIST);
                        return bean;
                    }
                    return netBean;
                });
    }
}
