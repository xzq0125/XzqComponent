package com.xzq.module_base.arouter;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 路由工具
 * Created by xzq on 2018/12/19.
 */
public class RouterUtils {

    private static Postcard build(String path) {
        return ARouter.getInstance().build(path);
    }

    public static void openMvp() {
        build(RouterPath.PAGING).navigation();
    }

    /**
     * 打开登录页
     */
    public static void openLogin() {
        build(RouterPath.LOGIN).navigation();
    }

    /**
     * 打开收藏页
     */
    public static void openFav() {
        build(LoginPath.FAV).navigation();
    }

}
