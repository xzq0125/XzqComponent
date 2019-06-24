package com.xzq.module_base.api;

import io.reactivex.Observable;

/**
 * Api回调
 * Created by xzq on 2019/5/24.
 */
public interface ApiCallback<T> {
    /**
     * 获取调用方法
     *
     * @param api Api服务
     * @return Observable
     */
    Observable<NetBean<T>> getApi(ApiService api);
}
