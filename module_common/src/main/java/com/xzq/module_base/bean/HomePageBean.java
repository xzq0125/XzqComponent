package com.xzq.module_base.bean;

import com.xzq.module_base.mvp.BaseListBean;

import java.util.List;

/**
 * HomePageBean
 * Created by xzq on 2018/8/1.
 */

public class HomePageBean extends BaseListBean {

    public List<Datas> datas;

    public static class Datas {
        public String title;
        public String niceDate;
        public String link;
    }

}
