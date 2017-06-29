package cn.jrhlive.meishe.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.common.BaseFragment;
import cn.jrhlive.meishe.adapter.FragmentAdapter;
import cn.jrhlive.meishe.adapter.MeiSheVpAdapter;
import cn.jrhlive.meishe.bean.ParamShortVideo;

/**
 * desc:
 * Created by jiarh on 17/5/4 15:13.
 */

public class MeisheMainFragment extends BaseFragment {


    @BindView(R.id.tablay)
    TabLayout tablay;
    @BindView(R.id.vp)
    ViewPager vp;
    String[] titles={"主题","音乐","字幕","高级"};

    FragmentAdapter mAdapter;
    List<BaseFragment> fragments;
    ParamShortVideo paramShortVideo;
    CaptionFragment mCaptionFragment;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_meishe_main;
    }

    @Override
    public void initView() {

        initTabs();
        initVp();
    }

    private void initVp() {

        fragments = new ArrayList<>(titles.length);
        fragments.add(new ThemeFragment());
        fragments.add(new MusicFragment());
        fragments.add(mCaptionFragment=new CaptionFragment());
        fragments.add(new HighLevelFragment());
        mAdapter = new MeiSheVpAdapter(getFragmentManager(),fragments,titles);
        vp.setAdapter(mAdapter);

    }

    private void initTabs() {
        for (String title:titles){
            tablay.addTab(tablay.newTab().setText(title));
        }
        tablay.getChildAt(0).setSelected(true);

    }

    @Override
    public void initEvent() {
        tablay.setupWithViewPager(vp);

    }


    public void setParamShortVideo(ParamShortVideo paramShortVideo) {
        this.paramShortVideo = paramShortVideo;
        mCaptionFragment.setParamShortVideo(paramShortVideo);
    }
}
