package com.xzq.component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.xzq.module_base.arouter.RouterUtils;
import com.xzq.module_base.base.BaseActivity;

import am.widget.gradienttabstrip.GradientTabStrip;


public class MainActivity extends BaseActivity implements OnTabSelectListener {

    private ViewPager vpFragments;
    private GradientTabStripAdapter adapter;

    @Titles
    private static final String[] mTitles = {"首页", "购物车", "订单", "我的"};

    @SeleIcons
    private static final int[] mSeleIcons = {
            R.drawable.ic_shouye_h,
            R.drawable.ic_gouwuche_h,
            R.drawable.ic_dingdan_h,
            R.drawable.ic_wode_h};

    @NorIcons
    private static final int[] mNormalIcons = {
            R.drawable.ic_shouye_n,
            R.drawable.ic_gouwuche_n,
            R.drawable.ic_dingdan_n,
            R.drawable.ic_wode_n};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        hideToolbar();
        vpFragments = findViewById(R.id.main_viewpager);
        adapter = new GradientTabStripAdapter(getSupportFragmentManager());
        vpFragments.setOffscreenPageLimit(adapter.getCount());
        vpFragments.setAdapter(adapter);
//        JPTabBar tabbar = findViewById(R.id.main_gts_tabs);
//        tabbar.setContainer(vpFragments);
//        tabbar.setTabListener(this);
//        if (tabbar.getMiddleView() != null)
//            tabbar.getMiddleView().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(me, "中间点击", Toast.LENGTH_SHORT).show();
//                }
//            });

        GradientTabStrip tab = findViewById(R.id.main_gts_tabs);
        TabAdapter tabAdapter = new TabAdapter(this);
        //tabAdapter.notifyDotChanged();
        tab.setAdapter(tabAdapter);
        //tab.setCenterDrawable(ContextCompat.getDrawable(this, R.drawable.tab_ic_join));
        //tab.setCenterPadding(500);
        //tab.setShowDividers(HorizontalLinearTabStripLayout.SHOW_DIVIDER_MIDDLE);
    }

    public void toViews(View v) {
        RouterUtils.openMvp(this, 55);
    }

    public void toLogin(View view) {
        RouterUtils.openLogin();
    }

    public void toFav(View view) {
        RouterUtils.openFav();
    }

    @Override
    public void onTabSelect(int index) {

    }

    @Override
    public boolean onInterruptSelect(int index) {
        return false;
    }

    private final class TabAdapter extends GradientTabStrip.Adapter {

        private final Drawable mNormal0;
        private final Drawable mNormal1;
        private final Drawable mNormal2;
        private final Drawable mNormal3;
        private final Drawable mSelected0;
        private final Drawable mSelected1;
        private final Drawable mSelected2;
        private final Drawable mSelected3;

        TabAdapter(Context context) {
            mNormal0 = ContextCompat.getDrawable(context,
                    R.drawable.ic_shouye_n);
            mNormal1 = ContextCompat.getDrawable(context,
                    R.drawable.ic_gouwuche_n);
            mNormal2 = ContextCompat.getDrawable(context,
                    R.drawable.ic_dingdan_n);
            mNormal3 = ContextCompat.getDrawable(context,
                    R.drawable.ic_wode_n);
            mSelected0 = ContextCompat.getDrawable(context,
                    R.drawable.ic_shouye_h);
            mSelected1 = ContextCompat.getDrawable(context,
                    R.drawable.ic_gouwuche_h);
            mSelected2 = ContextCompat.getDrawable(context,
                    R.drawable.ic_dingdan_h);
            mSelected3 = ContextCompat.getDrawable(context,
                    R.drawable.ic_wode_h);
        }

        @Nullable
        @Override
        public Drawable getDrawableNormal(int position, int count) {
            switch (position) {
                default:
                case 0:
                    return mNormal0;
                case 1:
                    return mNormal1;
                case 2:
                    return mNormal2;
                case 3:
                    return mNormal3;
            }
        }

        @Nullable
        @Override
        public Drawable getDrawableSelected(int position, int count) {
            switch (position) {
                default:
                case 0:
                    return mSelected0;
                case 1:
                    return mSelected1;
                case 2:
                    return mSelected2;
                case 3:
                    return mSelected3;
            }
        }

        @Override
        public String getDotText(int position, int count) {
            switch (position) {
                default:
                    return null;
                case 0:
                    return null;
                case 1:
                    return "1";
                case 2:
                    return null;
                case 3:
                    return null;
            }
        }
    }
}
