package com.xzq.module_base.api;

import com.xzq.module_base.bean.HomePageBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * ApiService
 * Created by Wesley on 2018/7/10.
 */
public interface ApiService {

    /**
     * method：网络请求的方法（区分大小写）
     * path：网络请求地址路径
     * hasBody：是否有请求体
     */
    @HTTP(method = "GET", path = "/article/list/{page}/json", hasBody = false)
    Observable<NetBean<HomePageBean>> getWangAndroidHomePage(
            @Path("page") int page);

    @FormUrlEncoded
    @POST("/yingbao/api/msg/validate")
    Observable<NetBean<Object>> validateCode(
            @Field("mobile") String mobile,
            @Field("code") String code
    );

    @FormUrlEncoded
    @POST("/yingbao/api/member/mobilelogin")
    Observable<NetBean<Object>> login(
            @Field("mobile") String mobile,
            @Field("registerSrc") String registerSrc,
            @Field("nickname") String nickname,
            @Field("bindNo") String bindNo
    );
}
