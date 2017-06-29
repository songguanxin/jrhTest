package cn.jrhlive.meishe.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cn.jrhlive.common.BaseFragment;

/**
 * 通用FragmentPagerAdapter
 *
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    List<BaseFragment> fragments ;

    public FragmentAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments==null?0:fragments.size();
    }

}
