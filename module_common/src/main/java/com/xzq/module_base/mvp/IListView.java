package com.xzq.module_base.mvp;

import java.util.List;

/**
 * 网络响应以列表形式返回
 * 列表接口
 *
 * @author xzq
 */
public interface IListView<Entity> {

    /**
     * 设置数据
     *
     * @param list        数据列表
     * @param hasNextPage 是否有下一页列表
     */
    void setData(List<Entity> list, int page, boolean hasNextPage);

    /**
     * 追加数据
     *
     * @param list        数据列表
     * @param hasNextPage 是否有下一页列表
     */
    void addData(List<Entity> list, int page, boolean hasNextPage);
}
