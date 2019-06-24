package com.xzq.module.login;

import com.xzq.module.bean.UserBean;
import com.xzq.module_base.mvp.IEntityView;

/**
 * LoginContract
 * Created by xzq on 2018/12/19.
 */
public interface LoginContract {

    interface LView extends IEntityView<UserBean> {

        void validateCodeSucceed();

    }

    interface Presenter {

        /**
         * 验证验证码的有效性
         *
         * @param mobile 电话
         * @param code   验证码
         */
        void validateCode(String mobile, String code);

        /**
         * 登录
         *
         * @param mobile      电话
         * @param registerSrc 验证码
         * @param nickname    昵称
         * @param bindNo      设备id
         */
        void login(String mobile, String registerSrc, String nickname, String bindNo);
    }
}
