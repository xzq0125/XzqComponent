package com.xzq.component;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xzq.module_base.utils.RefreshLayoutInitializer;
import com.xzq.module_base.utils.Utils;
import com.xzq.module_base.utils.XTimber;

/**
 * App
 * Created by xzq on 2018/12/18.
 */
public class App extends Application {

    private static Application sInstance;
    protected boolean isDebug;

    public static Application getContext() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // dex突破65535的限制
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        super.onCreate();
        sInstance = this;
        Utils.init(this);
        isDebug = Utils.isAppDebug();
        if (isDebug) {
            //初始化懒人log
            XTimber.plant(new XTimber.DebugTree());

            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        //初始化下拉刷新
        RefreshLayoutInitializer.initHeader();
    }

}
