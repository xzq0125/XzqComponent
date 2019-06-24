package com.xzq.module_base.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xzq.module_base.R;
import com.xzq.module_base.eventbus.EventUtil;
import com.xzq.module_base.eventbus.MessageEvent;
import com.xzq.module_base.utils.XZQLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Activity基础类
 *
 * @author xzq
 */

public abstract class BaseActivity extends SimpleLoadingActivity {

    //Activity生命周期
    private ActivityState mState = ActivityState.CREATE;
    protected final BaseActivity me = this;
    private Unbinder unbinder;

    /**
     * Activity生命周期
     */
    private enum ActivityState {
        CREATE, START, RESTART, RESUME, PAUSE, STOP, DESTROY
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mState = ActivityState.CREATE;
        XZQLog.debug("BaseActivity", getClass().getSimpleName());
        EventUtil.register(this);
        final int layoutId = getLayoutId();
        if (layoutId > 0) {
            setContentView(layoutId);
            unbinder = ButterKnife.bind(this);
        }
        initViews(savedInstanceState);
    }

    /**
     * 获取布局资源ID
     *
     * @return 布局资源ID
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化资源
     *
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initViews(@Nullable Bundle savedInstanceState);

    @Override
    protected void onStart() {
        super.onStart();
        mState = ActivityState.START;
    }

    @Override
    protected void onRestart() {
        mState = ActivityState.RESTART;
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mState = ActivityState.RESUME;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mState = ActivityState.PAUSE;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mState = ActivityState.STOP;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventUtil.unregister(this);
        mState = ActivityState.DESTROY;
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    protected boolean isActivityResume() {
        return mState == ActivityState.RESUME;
    }

    @SuppressWarnings("all")
    public void setToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            //标题
            TextView titleView = (TextView) toolbar.findViewById(R.id.toolbar_title);
            if (titleView != null) {
                titleView.setText(title);
            }
            //返回按钮
            View btnBack = toolbar.findViewById(R.id.toolbar_btn_back);
            if (btnBack != null) {
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackClick();
                    }
                });
            } else {
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackClick();
                    }
                });
            }

            setSupportActionBar(toolbar);
        }
    }

    public void setToolbar(@StringRes int resId) {
        setToolbar(getString(resId));
    }

    /**
     * Toolbar 返回按钮点击,若要做返回逻辑，子Activity需重写onBackPressed()
     */
    protected void onBackClick() {
        onBackPressed();
    }

    /**
     * EventBus订阅方法
     *
     * @param event 消息实体
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(@NonNull MessageEvent event) {
    }

    /**
     * EventBus粘性订阅方法
     *
     * @param event 消息实体
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageStickyEvent(@NonNull MessageEvent event) {
    }
}
