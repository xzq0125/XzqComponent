package com.xzq.component;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.xzq.module_homepage.HomePageFragment;
import com.xzq.module_me.MeFragment;
import com.xzq.module_order.OrderFragment;
import com.xzq.module_shoppingcart.ShoppingCartFragment;

/**
 * GradientTabStripAdapter
 * Created by xzq on 2018/12/20.
 */
public class GradientTabStripAdapter extends FragmentStatePagerAdapter  {

    public GradientTabStripAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default:
            case 0:
                return HomePageFragment.newInstance();
            case 1:
                return ShoppingCartFragment.newInstance();
            case 2:
                return OrderFragment.newInstance();
            case 3:
                return MeFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            default:
            case 0:
                return "首页";
            case 1:
                return "购物车";
            case 2:
                return "订单";
            case 3:
                return "我的";
        }
    }

}
