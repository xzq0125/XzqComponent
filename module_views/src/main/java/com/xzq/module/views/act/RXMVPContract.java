package com.xzq.module.views.act;


import com.xzq.module_base.bean.HomePageBean;
import com.xzq.module_base.mvp.IListView;

/**
 * RXMVPContract
 * Created by Wesley on 2018/7/10.
 */
public interface RXMVPContract {

    interface View extends IListView<HomePageBean.Datas> {
    }

    interface Presenter {
        void getData(int page);
    }

}
