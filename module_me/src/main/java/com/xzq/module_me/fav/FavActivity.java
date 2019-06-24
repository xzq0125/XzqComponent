package com.xzq.module_me.fav;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xzq.module_base.arouter.LoginPath;
import com.xzq.module_base.base.BaseActivity;
import com.xzq.module_me.R;

/**
 * 收藏页
 */
@Route(path = LoginPath.FAV)
public class FavActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fav;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {

    }
}
