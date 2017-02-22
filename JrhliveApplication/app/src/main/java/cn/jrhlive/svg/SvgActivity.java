package cn.jrhlive.svg;

import android.graphics.drawable.Animatable;
import android.widget.ImageView;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class SvgActivity extends BaseActivity {


    @BindView(R.id.iv_svg)
    ImageView ivSvg;
    @BindView(R.id.iv_lion)
    ImageView ivLion;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

        if (ivSvg.getDrawable() instanceof Animatable) {
            ((Animatable) ivSvg.getDrawable()).start();
        }
        if (ivLion.getDrawable() instanceof Animatable) {
            ((Animatable) ivLion.getDrawable()).start();
        }
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_svg;
    }



}
