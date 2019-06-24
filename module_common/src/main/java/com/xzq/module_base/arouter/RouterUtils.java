package com.xzq.module_base.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xzq.module_base.utils.XZQLog;

/**
 * 路由工具
 * Created by xzq on 2018/12/19.
 */
public class RouterUtils {

    public static void inject(Object thiz) {
        ARouter.getInstance().inject(thiz);
    }

    private static Postcard build(String path) {
        return ARouter.getInstance().build(path);
    }

    public static void openMvp(Context context, int pos) {
        build(Router.Path.MVP).withInt(Router.Extra.MVP_POS, pos).navigation(context, new NavigationCallback() {
            @Override
            public void onFound(Postcard postcard) {
                XZQLog.debug("onFound  Postcard= " + postcard);
            }

            @Override
            public void onLost(Postcard postcard) {
                XZQLog.debug("onLost  Postcard= " + postcard);
            }

            @Override
            public void onArrival(Postcard postcard) {
                XZQLog.debug("onArrival  Postcard= " + postcard);
            }

            @Override
            public void onInterrupt(Postcard postcard) {
                XZQLog.debug("onInterrupt  Postcard= " + postcard);
            }
        });
    }

    /**
     * 打开登录页
     */
    public static void openLogin() {
        build(Router.Path.LOGIN).navigation();
    }

    /**
     * 打开收藏页
     */
    public static void openFav() {
        build(LoginPath.FAV).navigation();
    }

}
