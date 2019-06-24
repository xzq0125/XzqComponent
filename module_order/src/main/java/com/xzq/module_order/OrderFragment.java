package com.xzq.module_order;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xzq.module_base.adapter.BaseRecyclerAdapter;
import com.xzq.module_base.adapter.BaseRecyclerViewHolder;
import com.xzq.module_base.base.BaseListFragment;
import com.xzq.module_base.bean.HomePageBean;
import com.xzq.module_base.utils.ToastUtils;
import com.xzq.module_base.utils.XZQLog;

import java.util.List;

/**
 * OrderFragment
 * Created by xzq on 2018/12/20.
 */
public class OrderFragment extends BaseListFragment<OrderPresenter, HomePageBean>  {

    public static OrderFragment newInstance() {

        Bundle args = new Bundle();

        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void isVisibleToUser(boolean isVisibleToUser) {
        super.isVisibleToUser(isVisibleToUser);
        XZQLog.debug("isVisibleToUser  = " + isVisibleToUser);
    }

    @Override
    protected void loadData() {
        XZQLog.debug("loadData");
    }

    @Override
    protected String getPageTitle() {
        return null;
    }

    @Override
    protected RecyclerView.Adapter getPageAdapter() {
        return new MyAdapter().setOnItemClickListener(new BaseRecyclerViewHolder.OnItemClickListener<HomePageBean>() {
            @Override
            public void onItemClicked(View v, HomePageBean data, int pos) {
                ToastUtils.show(data.title+pos);
            }
        });
    }

    @Override
    protected OrderPresenter createPresenter() {
        return new OrderPresenter(this);
    }


    public final class MyAdapter extends BaseRecyclerAdapter<HomePageBean, MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @Nullable View itemView, int viewType) {
            return new MyViewHolder(itemView);
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return android.R.layout.test_list_item;
        }

        @Override
        public void onConvert(@NonNull MyViewHolder holder, HomePageBean data,
                              int position, @NonNull List<Object> payload) {
            holder.setData(data);
        }

    }

    private final class MyViewHolder extends BaseRecyclerViewHolder<HomePageBean> implements View.OnClickListener {

        private TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView;
            //tv.setOnClickListener(this);
        }

        @Override
        public void setData(HomePageBean data) {
            tv.setTag(data.link);
            tv.setText(position + "\t" + data.title + "\t" + data.niceDate);
        }

        @Override
        public void onClick(View v) {
            ToastUtils.show(data.title+position);
        }
    }

}
