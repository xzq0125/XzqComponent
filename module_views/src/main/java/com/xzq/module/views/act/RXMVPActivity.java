package com.xzq.module.views.act;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.xzq.module.views.R;
import com.xzq.module_base.adapter.BaseRecyclerAdapter;
import com.xzq.module_base.adapter.BaseRecyclerViewHolder;
import com.xzq.module_base.arouter.Router;
import com.xzq.module_base.arouter.RouterUtils;
import com.xzq.module_base.base.BaseListActivity;
import com.xzq.module_base.bean.HomePageBean;
import com.xzq.module_base.utils.ToastUtils;
import com.xzq.module_base.utils.XZQLog;

import java.util.List;

@Route(path = Router.Path.MVP)
public class RXMVPActivity extends BaseListActivity<RXMVPPresenter, HomePageBean> implements RXMVPContract.View {

    @Autowired(name = Router.Extra.MVP_POS)
    int pos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        RouterUtils.inject(this);
        XZQLog.debug("Router pos = " + pos);
        int pos = getIntent().getIntExtra(Router.Extra.MVP_POS,-1);
        XZQLog.debug("getIntent pos = " + pos);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected RXMVPPresenter createPresenter() {
        return new RXMVPPresenter(this);
    }

    @Override
    protected String getPageTitle() {
        return "分页加载";
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

    public final class MyAdapter extends BaseRecyclerAdapter<HomePageBean, MyViewHolder> {

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
