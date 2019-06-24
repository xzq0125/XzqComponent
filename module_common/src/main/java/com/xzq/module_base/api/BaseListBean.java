package com.xzq.module_base.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 列表基类
 *
 * @author xzq
 */

public class BaseListBean<T> {

    @SerializedName(value = "snPageCount", alternate = {"pageCount"})
    private int snPageCount;
    public int count;

    /**
     * 是否还有下一页
     *
     * @param page 当前页码
     * @return 是否还有下一页
     */
    public boolean hasNextPage(final int page) {
        return page < snPageCount;
    }

    @SerializedName(value = "snData", alternate = {"data", "rows", "datas"})
    private List<T> snData;

    public List<T> getList() {
        return snData;
    }

    public boolean isEmpty() {
        return snData == null || snData.isEmpty();
    }
}
