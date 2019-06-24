package com.xzq.module_base.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xzq.module_base.eventbus.EventUtil;
import com.xzq.module_base.eventbus.MessageEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment基础类
 * 实现一些通用逻辑
 *
 * @author xzq
 */
public abstract class BaseFragment extends SimpleLoadingFragment {

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(getLayoutId(inflater, container, savedInstanceState),
                container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(savedInstanceState);
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
}
