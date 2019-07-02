package com.xzq.module_order;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.xzq.module_base.adapter.BaseRecyclerAdapter;
import com.xzq.module_base.bean.HomePageBean;

import java.util.List;

/**
 * MyAdapter
 * Created by xzq on 2019/7/2.
 */
public class MyAdapter extends BaseRecyclerAdapter<HomePageBean, MyViewHolder> {

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @Nullable View itemView, int viewType) {
        return new MyViewHolder(itemView);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_song_list;
    }

    @Override
    public void onConvert(@NonNull MyViewHolder holder, HomePageBean data,
                          int position, @NonNull List<Object> payload) {
        holder.setData(data);
    }

}