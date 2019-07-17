package com.xzq.component.paging;

import android.view.View;
import android.widget.TextView;

import com.xzq.module_base.adapter.BaseRecyclerViewHolder;
import com.xzq.module_base.bean.HomePageBean;

/**
 * MyViewHolder
 * Created by xzq on 2019/7/2.
 */
public class MyViewHolder extends BaseRecyclerViewHolder<HomePageBean> {

    private TextView tv;

    MyViewHolder(View itemView) {
        super(itemView);
        tv = (TextView) itemView;
    }

    @Override
    public void setData(HomePageBean data) {
        tv.setTag(data.link);
        tv.setText(position + "\t" + data.title + "\t" + data.niceDate);
    }
}

