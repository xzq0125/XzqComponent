package com.xzq.module;

import com.xzq.module_base.app.IApplicationDelegate;

/**
 * LoginApp
 * Created by xzq on 2018/12/19.
 */
public class LoginApp implements IApplicationDelegate {

    @Override
    public void onCreate() {
        User.init();
    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int level) {

    }
}
