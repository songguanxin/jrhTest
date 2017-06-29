package cn.jrhlive.meishe.adapter;

import android.support.v4.app.FragmentManager;

import java.util.List;

import cn.jrhlive.common.BaseFragment;

/**
 * desc:
 * Created by jiarh on 17/5/5 11:08.
 */

public class MeiSheVpAdapter extends FragmentAdapter {
    String[] titles;
    public MeiSheVpAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm, fragments);
    }

    public MeiSheVpAdapter(FragmentManager fm, List<BaseFragment> fragments, String[] titles) {
        super(fm, fragments);
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
