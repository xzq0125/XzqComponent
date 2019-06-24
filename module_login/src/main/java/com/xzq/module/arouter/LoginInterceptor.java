package com.xzq.module.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.xzq.module.User;
import com.xzq.module_base.arouter.LoginPath;
import com.xzq.module_base.arouter.RouterUtils;
import com.xzq.module_base.utils.ToastUtils;

/**
 * 登录拦截器，拦截需要登录才能开启的页面
 * Created by xzq on 2018/12/19.
 */
@Interceptor(priority = 1, name = "login")
public class LoginInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String group = postcard.getGroup();
        boolean needLogin = LoginPath.isLoginGroup(group) && !User.isLogin();
        if (needLogin) {
            //TODO
            ToastUtils.show("请先登录");
            callback.onInterrupt(null);
            RouterUtils.openLogin();
        } else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
    }
}
