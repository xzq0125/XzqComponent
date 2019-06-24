package com.xzq.module.login;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.xzq.module_base.mvp.AbsPresenter;
import com.xzq.module_base.utils.XZQLog;

/**
 * LoginPresenter
 * Created by xzq on 2018/12/19.
 */
public class LoginPresenter extends AbsPresenter<LoginContract.LView>
        implements LoginContract.Presenter {

    LoginPresenter(@NonNull LoginContract.LView view) {
        super(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void validateCode(String mobile, String code) {
        doEntityRequest(api -> api.validateCode(mobile, code), Object.class).subscribe(new PostLoadingCallback<Object>() {
            @Override
            protected void onSuccess(Object data, String msg, int code, int page, boolean hasNextPage) {
                XZQLog.debug("验证成功");
                mView.validateCodeSucceed();
            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void login(String mobile, String registerSrc, String nickname, String bindNo) {
        doEntityRequest(api -> api.login(mobile, registerSrc, nickname, bindNo), Object.class)
                .subscribe(new PostLoadingCallback<Object>() {
                    @Override
                    protected void onSuccess(Object data, String msg, int code, int page, boolean hasNextPage) {
                        XZQLog.debug("登录成功");
                    }
                });
    }
}
