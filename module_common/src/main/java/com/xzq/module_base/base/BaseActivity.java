package com.xzq.module_base.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xzq.module_base.R;
import com.xzq.module_base.dialog.PostLoadingDialog;
import com.xzq.module_base.eventbus.EventUtil;
import com.xzq.module_base.eventbus.MessageEvent;
import com.xzq.module_base.mvp.MvpContract;
import com.xzq.module_base.utils.XZQLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

import am.widget.stateframelayout.StateFrameLayout;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Activity基础类
 *
 * @author xzq
 */

public abstract class BaseActivity extends AppCompatActivity implements
        OnRefreshListener,
        StateFrameLayout.OnStateClickListener,
        MvpContract.CommonView {

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

    private StateFrameLayout sfl;
    protected SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mState = ActivityState.CREATE;
        XZQLog.debug("BaseActivity", getClass().getSimpleName());
        if (getClass().getSimpleName().equals("com.xzq.component.MainActivity")) {
            overridePendingTransition(R.anim.activity_open_enter_main, R.anim.activity_open_exit);
        } else {
            overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit);
        }
        EventUtil.register(this);
        final int layoutId = getLayoutId();
        ViewGroup contentParent = findViewById(android.R.id.content);
        View contentView = LayoutInflater.from(this)
                .inflate(R.layout.activity_base, contentParent, false);
        sfl = contentView.findViewById(R.id.sfl);
        sfl.setOnStateClickListener(this);
        refreshLayout = contentView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        setRefreshEnable(false);
        if (layoutId > 0) {
            View src = LayoutInflater.from(this)
                    .inflate(layoutId, sfl);
            unbinder = ButterKnife.bind(this, src);
        }
        setContentView(contentView);
        initViews(savedInstanceState);
        onErrorClick(null);
    }

    protected void hideToolbar() {
        findViewById(R.id.toolbar).setVisibility(View.GONE);
    }

    protected void setRefreshEnable(boolean enabled) {
        refreshLayout.setEnabled(enabled);
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
        hidePostLoading();
    }

    protected boolean isActivityResume() {
        return mState == ActivityState.RESUME;
    }

    @SuppressWarnings("all")
    public void setToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
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
        } else {
            TextView titleView = (TextView) findViewById(R.id.toolbar_title);
            if (titleView != null) {
                titleView.setText(title);
            }
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

    @Override
    public void onStateLoading(String loadingMessage) {
        if (!isRefresh) {
            sfl.loading();
        }
        XZQLog.debug("onStateLoading  loadingMessage = " + loadingMessage);
    }

    @Override
    public void onStateError(int page, String error) {
        this.<TextView>findViewById(R.id.tv_error_view)
                .setText(String.format(Locale.getDefault(), "%1$s\n点击重新加载", error));
        sfl.error();
        XZQLog.debug("onStateLoading  error = " + error);
    }

    @Override
    public void onStateEmpty() {
        sfl.empty();
        XZQLog.debug("onStateEmpty");
    }

    @Override
    public void onStateNormal() {
        refreshLayout.finishRefresh();
        sfl.normal();
        XZQLog.debug("onStateNormal");
    }

    @Override
    public void onStateLoadMoreEmpty() {
        XZQLog.debug("onStateLoadMoreEmpty");
    }

    @Override
    public void onStateLoadMoreError(int page, String error) {
        XZQLog.debug("onStateLoadMoreError error = " + error + " page = " + page);
    }

    @Override
    public void onShowLoading(String loadingMessage) {
        XZQLog.debug("onShowLoading loadingMessage = " + loadingMessage);
    }

    @Override
    public void onHideLoading() {
        XZQLog.debug("onHideLoading");
    }

    @Override
    public void onShowPostLoading(String loadingMessage) {
        XZQLog.debug("onShowPostLoading loadingMessage = " + loadingMessage);
        showPostLoading();
    }

    @Override
    public void onHidePostLoading() {
        XZQLog.debug("onHidePostLoading");
        hidePostLoading();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isRefresh = true;
        getFirstPageData();
    }

    @Override
    public void onErrorClick(StateFrameLayout layout) {
        isRefresh = false;
        getFirstPageData();
    }

    private boolean isRefresh = false;

    protected void getFirstPageData() {
    }

    private PostLoadingDialog mLoadingDialog;

    public void showPostLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new PostLoadingDialog(this);
        }
        mLoadingDialog.show();
    }

    public void hidePostLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.activity_close_exit);
    }
}
