package com.xzq.module_base.mvp;

import com.xzq.module_base.api.NetBean;
import com.xzq.module_base.utils.XZQLog;

/**
 * MvpContract
 * Created by xzq on 2019/7/2.
 */
public interface MvpContract {

    interface CommonView extends IStateView, IPostLoadingView {
    }

    interface ValidateCodeSucceed {
        void validateCodeSucceed();
    }

    class RXMVPPresenter extends CommonPresenter {

        @Override
        public void getList() {
            doBaseListRequest(api -> api.getWangAndroidHomePage(mPage), mPage);
        }
    }

    class CommonPresenter extends AbsPresenter<CommonView> {

        public void validateCode(String mobile, String code) {
            doObjectRequest(api -> api.validateCode(mobile, code)).subscribe(new PostLoadingCallback<Object>() {
                @Override
                protected void onSuccess(NetBean<Object> response, Object data, int page) {
                    XZQLog.debug("验证成功");
                    if (mView instanceof ValidateCodeSucceed) {
                        ((ValidateCodeSucceed) mView).validateCodeSucceed();
                    }
                }
            });
        }

        public void login(String mobile, String registerSrc, String nickname, String bindNo) {
            doObjectRequest(api -> api.login(mobile, registerSrc, nickname, bindNo)).subscribe(new PostLoadingCallback<Object>() {
                @Override
                protected void onSuccess(NetBean<Object> response, Object data, int page) {
                    XZQLog.debug("登录成功");
                }
            });
        }
    }
}
