package com.xzq.module_base.app;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xzq.module_base.utils.ClassUtils;
import com.xzq.module_base.utils.RefreshLayoutInitializer;
import com.xzq.module_base.utils.Utils;
import com.xzq.module_base.utils.XTimber;
import com.xzq.module_base.utils.XZQLog;

import java.util.Arrays;
import java.util.List;


/**
 * 要想使用BaseApplication，必须在组件中实现自己的Application，并且继承BaseApplication；
 * 组件中实现的Application必须在debug包中的AndroidManifest.xml中注册，否则无法使用；
 * 组件的Application需置于java/debug文件夹中，不得放于主代码；
 * 组件中获取Context的方法必须为:Utils.getContext()，不允许其他写法；
 *
 * @author 2016/12/2 17:02
 * @version V1.0.0
 * @name BaseApplication
 */
public class BaseApplication extends Application {

    private static final String[] ROOT_PACKAGES = {"com.xzq.module"};
    private static BaseApplication sInstance;
    private List<IApplicationDelegate> mAppDelegateList;
    protected boolean isDebug;


    public static BaseApplication getContext() {
        return sInstance;
    }

    @Override
    public void onCreate() {
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
        mAppDelegateList = ClassUtils.getObjectsWithInterface(this, IApplicationDelegate.class, Arrays.asList(ROOT_PACKAGES));
        XZQLog.debug("mAppDelegateList = " + mAppDelegateList);
        for (IApplicationDelegate delegate : mAppDelegateList) {
            delegate.onCreate();
        }

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (IApplicationDelegate delegate : mAppDelegateList) {
            delegate.onTerminate();
        }
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        for (IApplicationDelegate delegate : mAppDelegateList) {
            delegate.onLowMemory();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        for (IApplicationDelegate delegate : mAppDelegateList) {
            delegate.onTrimMemory(level);
        }
    }
}
