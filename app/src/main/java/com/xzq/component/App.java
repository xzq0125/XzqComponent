package com.xzq.component;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.xzq.module_base.app.BaseApplication;

/**
 * App
 * Created by xzq on 2018/12/18.
 */
public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // dex突破65535的限制
        MultiDex.install(this);
    }

}
