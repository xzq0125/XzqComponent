package com.xzq.module_base;

import android.text.TextUtils;

import com.xzq.module_base.bean.UserBean;
import com.xzq.module_base.sp.UserSPManager;
import com.xzq.module_base.utils.StringUtils;


/**
 * 登录信息管理
 * Created by Wesley on 2018/7/9.
 */

public class User {

    private static UserBean sLogin = null;

    public static void init() {
        sLogin = UserSPManager.getLoginData();
    }

    /**
     * 用户登录成功后设置登录数据
     *
     * @param data 登录数据
     */
    public static void setLoginData(UserBean data) {
        sLogin = data;
        //保存到SP
        UserSPManager.putLoginData(data);
    }

    /**
     * 用户登出、用户登录凭证失效时清除用户数据
     */
    public static void clearUserData() {
        sLogin = null;
        UserSPManager.clearUserData();
    }

    /**
     * 用户是否已经登录
     *
     * @return 是否已经登录
     */
    public static boolean isLogin() {
        return sLogin != null;
    }

    /**
     * 获取用户登录凭证
     *
     * @return 用户登录凭证
     */
    public static String getToken() {
        return sLogin != null && !TextUtils.isEmpty(sLogin.token)
                ? sLogin.token
                : "";
    }

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    public static String getId() {
        return sLogin != null && !TextUtils.isEmpty(sLogin.uid)
                ? sLogin.uid
                : "";
    }

    /**
     * 获取用户名称（注册后username跟手机号相同）
     *
     * @return 用户名称
     */
    public static String getName() {
        return sLogin != null && !TextUtils.isEmpty(sLogin.username)
                ? sLogin.username
                : "";
    }

    /**
     * 获取用户昵称
     *
     * @return 用户昵称
     */
    public static String getNickname() {
        return sLogin != null && !TextUtils.isEmpty(sLogin.nickname)
                ? sLogin.nickname
                : "";
    }

    /**
     * 获取用户电话
     *
     * @return 用户电话
     */
    public static String getPhone() {
        return sLogin != null && !TextUtils.isEmpty(sLogin.cellphone)
                ? sLogin.cellphone
                : "";
    }

    /**
     * 获取用户性别
     *
     * @return 用户性别
     */
    public static String getSex() {
        return sLogin != null && !TextUtils.isEmpty(sLogin.sex)
                ? sLogin.sex
                : "";
    }

    /**
     * 获取用户头像
     *
     * @return 用户头像
     */
    public static String getPortrait() {
        return sLogin != null && !TextUtils.isEmpty(sLogin.header)
                ? sLogin.header
                : "";
    }

    /**
     * 获取用户区域ID
     *
     * @return 用户区域ID
     */
    public static String getAId() {
        return sLogin != null && !TextUtils.isEmpty(sLogin.aid)
                ? sLogin.aid
                : "";
    }

    /**
     * 获取页面显示的昵称
     * 如果nickname为空，则用username（注册后username跟手机号相同）
     *
     * @return 页面显示的昵称
     */
    public static String getShowNickname() {
        final String nickname = getNickname();
        return !TextUtils.isEmpty(nickname) ? nickname : getName();
    }

    /**
     * 获取账户余额
     *
     * @return 账户余额
     */
    public static String getMoney() {
        return sLogin != null && !TextUtils.isEmpty(sLogin.money)
                ? sLogin.money
                : "";
    }

    /**
     * 获取个性签名
     */
    public static String getSign() {
        return sLogin != null && !TextUtils.isEmpty(sLogin.sign)
                ? sLogin.sign
                : "";
    }

    /**
     * 获取用户积分
     */
    public static String getIntegral() {
        return sLogin != null && !TextUtils.isEmpty(sLogin.integral)
                ? sLogin.integral
                : "0";
    }

    /**
     * 获取用户未读推送消息
     */
    public static String getUnreadMessage() {
        return sLogin != null && StringUtils.toInt(sLogin.unreadMessage) > 0
                ? sLogin.unreadMessage
                : null;
    }


    /**
     * 获取用户类型
     */
    public static String getUserType() {
        return sLogin != null && !TextUtils.isEmpty(sLogin.user_type)
                ? sLogin.user_type
                : "1";
    }

    /**
     * 设置用户昵称
     *
     * @param nickname 用户昵称
     */
    public static void setNickname(String nickname) {
        UserBean login = sLogin;
        if (login != null) {
            login.nickname = nickname;
            setLoginData(login);
        }
    }

    /**
     * 设置用户性别
     *
     * @param sex 用户性别
     */
    public static void setSex(String sex) {
        UserBean login = sLogin;
        if (login != null) {
            login.sex = sex;
            setLoginData(login);
        }
    }

    /**
     * 设置用户区域ID
     *
     * @param aid 用户区域ID
     */
    public static void setAId(String aid) {
        UserBean login = sLogin;
        if (login != null) {
            login.aid = aid;
            setLoginData(login);
        }
    }

    /**
     * 设置用户头像
     *
     * @param header 用户头像
     */
    public static void setPortrait(String header) {
        UserBean login = sLogin;
        if (login != null) {
            login.header = header;
            setLoginData(login);
        }
    }

    /**
     * 设置账户余额
     *
     * @param money 账户余额
     */
    public static void setMoney(String money) {
        UserBean login = sLogin;
        if (login != null) {
            login.money = money;
            setLoginData(login);
        }
    }

    /**
     * 设置个性签名
     */
    public static void setSign(String sign) {
        UserBean login = sLogin;
        if (login != null) {
            login.sign = sign;
            setLoginData(login);
        }
    }


    /**
     * 设置用户类型
     */
    public static void setUserType(String userType) {
        UserBean login = sLogin;
        if (login != null) {
            login.user_type = userType;
            setLoginData(login);
        }
    }

    /**
     * 设置用户电话
     *
     * @return 用户电话
     */
    public static void setPhone(String cellPhone) {
        UserBean login = sLogin;
        if (login != null) {
            login.cellphone = cellPhone;
            setLoginData(login);
        }
    }

    /**
     * 设置用户积分
     *
     * @return 用户积分
     */
    public static void setIntegral(String integral) {
        UserBean login = sLogin;
        if (login != null) {
            login.integral = integral;
            setLoginData(login);
        }
    }

    /**
     * 设置用户未读推送消息
     *
     * @param unreadMessage 用户未读推送消息
     */
    public static void setUnreadMessage(String unreadMessage) {
        UserBean login = sLogin;
        if (login != null) {
            login.unreadMessage = unreadMessage;
            setLoginData(login);
        }
    }

    /**
     * 当前用户是否是男
     *
     * @return 是否是男
     */
    public static boolean isMan() {
        final String sex = getSex();
        return "1".equals(sex);
    }

    /**
     * 当前用户是否是女
     *
     * @return 是否是女
     */
    public static boolean isWoman() {
        final String sex = getSex();
        return "2".equals(sex);
    }
}
