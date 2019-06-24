package com.xzq.module_base.mvp;

/**
 * 通用列表基类约束
 *
 * @author xzq
 */

public interface BaseListContract {

    interface View<Entity> extends IListView<Entity> {
    }

    interface Presenter {
        /**
         * 加载列表
         *
         * @param page      页码
         */
        void getList(final int page);
    }
}
