package com.xzq.module_base.mvp;

/**
 * 网络响应以实体形式返回
 * 实体接口
 *
 * @author xzq
 */
public interface IEntityView<Entity> extends IStateView {

    /**
     * 设置数据
     *
     * @param entity 实体
     */
    void setData(Entity entity);
}
