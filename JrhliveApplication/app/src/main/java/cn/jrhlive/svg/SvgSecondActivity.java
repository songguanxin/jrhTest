package cn.jrhlive.svg;

import android.graphics.drawable.Animatable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class SvgSecondActivity extends BaseActivity {

    @BindView(R.id.iv_svg_wine)
    ImageView ivSvgWine;
    @BindView(R.id.activity_svg_second)
    RelativeLayout activitySvgSecond;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

        if (ivSvgWine.getDrawable() instanceof Animatable) {
            ((Animatable) ivSvgWine.getDrawable()).start();
        }
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_svg_second;
    }

}
