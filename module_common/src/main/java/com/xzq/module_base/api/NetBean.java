package com.xzq.module_base.api;

/**
 * 请求返回实体
 *
 * @author xzq
 */
public class NetBean<T> {
    private int status;// 错误code
    private String msg;// 错误信息
    private T data;// 返回数据
    private int count;//服务端定义数组返回形式总页数

    public boolean isOk() {
        return status == 0;
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public boolean hasNextPage(int mPage) {
        return mPage < count;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void copyFrom(NetBean<T> netBean) {
        this.data = netBean.getData();
        this.status = netBean.getStatus();
        this.msg = netBean.getMsg();
    }
}
