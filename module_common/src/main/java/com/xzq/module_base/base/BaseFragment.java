package com.xzq.module_base.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xzq.module_base.R;
import com.xzq.module_base.eventbus.EventUtil;
import com.xzq.module_base.eventbus.MessageEvent;
import com.xzq.module_base.mvp.IStateView;
import com.xzq.module_base.utils.XZQLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

import am.widget.stateframelayout.StateFrameLayout;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment基础类
 * 实现一些通用逻辑
 *
 * @author xzq
 */
public abstract class BaseFragment extends Fragment
        implements IStateView,
        StateFrameLayout.OnStateClickListener,
        OnRefreshListener {

    private Unbinder unbinder;

    public enum FragmentState {
        CREATE, START, RESUME, PAUSE, STOP, DESTROY
    }

    private FragmentState mState = FragmentState.CREATE;
    protected Activity me;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mState = FragmentState.CREATE;
        me = getActivity();
        EventUtil.register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        mState = FragmentState.START;
        super.onStart();
    }

    @Override
    public void onResume() {
        mState = FragmentState.RESUME;
        super.onResume();
    }

    @Override
    public void onPause() {
        mState = FragmentState.PAUSE;
        super.onPause();
    }

    @Override
    public void onStop() {
        mState = FragmentState.STOP;
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mState = FragmentState.DESTROY;
        super.onDestroy();
        EventUtil.unregister(this);
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null)
            unbinder.unbind();
        super.onDestroyView();
    }

    private StateFrameLayout sfl;
    protected SmartRefreshLayout refreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final int layoutId = getLayoutId(inflater, container, savedInstanceState);
        View rootView = LayoutInflater.from(me)
                .inflate(R.layout.activity_base, container, false);
        sfl = rootView.findViewById(R.id.sfl);
        sfl.setOnStateClickListener(this);
        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        if (layoutId > 0) {
            View src = LayoutInflater.from(me)
                    .inflate(layoutId, sfl);
            unbinder = ButterKnife.bind(this, src);
        }
        return rootView;
    }

    protected void hideToolbar() {
        findViewById(R.id.toolbar).setVisibility(View.GONE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideToolbar();
        initViews(savedInstanceState);
        onErrorClick(null);
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
                        getActivity().onBackPressed();
                    }
                });
            } else {
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().onBackPressed();
                    }
                });
            }
        }
    }

    public void setToolbar(@StringRes int resId) {
        setToolbar(getString(resId));
    }


    /**
     * 设置内容Layout
     * 返回的必须是资源 layout ID
     *
     * @param inflater           布局容器
     * @param container          根View
     * @param savedInstanceState 保存的状态
     * @return 资源id
     */
    @LayoutRes
    protected abstract int getLayoutId(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 初始化
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * Look for a child view with the given id. If this view has the given id,
     * return this view.
     *
     * @param id The id to search for.
     * @return The view that has the given id in the hierarchy or null
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(@IdRes int id) {
        if (getView() == null) {
            throw new IllegalStateException("Fragment " + this
                    + " not attached to Activity");
        }
        return (T) getView().findViewById(id);
    }

    /**
     * 获取是否为Resume状态
     *
     * @return 是否为Resume状态
     */
    public final boolean isResume() {
        return mState == FragmentState.RESUME;
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

}
