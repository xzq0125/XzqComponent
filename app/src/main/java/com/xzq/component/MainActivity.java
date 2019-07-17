package com.xzq.component;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.xzq.module_base.api.NetCallback;
import com.xzq.module_base.arouter.RouterUtils;
import com.xzq.module_base.base.BaseActivity;


public class MainActivity extends BaseActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        hideToolbar();
    }

    @Override
    protected void activityExit() {
    }

    public void pagingLoad(View view) {
        NetCallback.FIRST_PAGE_INDEX = 0;
        RouterUtils.openMvp();
    }

    public void pagingLoadError(View view) {
        NetCallback.FIRST_PAGE_INDEX = -1;
        RouterUtils.openMvp();
    }

    public void pagingLoadEmpty(View view) {
        NetCallback.FIRST_PAGE_INDEX = 10000;
        RouterUtils.openMvp();
    }

    public void pagingLoadCustom(View view) {
        NetCallback.FIRST_PAGE_INDEX = 0;
        RouterUtils.openMvp();
    }
}
