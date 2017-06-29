package cn.jrhlive.meishe.ui;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.RxBus.RxBus;
import cn.jrhlive.meishe.bean.SpecialEffect;


/**
 * desc:
 * Created by jiarh on 17/5/5 10:30.
 */

public class ThemeFragment extends ShortVideoParentFragment {

    @BindView(R.id.tv_add_theme)
    TextView tvAddTheme;
    @BindView(R.id.theme_rcview)
    RecyclerView themeRcview;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_theme;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @OnClick(R.id.tv_add_theme)
    public void onViewClicked() {
        RxBus.getDefault().post(new SpecialEffect("ED51E571-2650-4754-A45D-AA1CFEA14A81.theme"));
    }

}
