package cn.jrhlive.svg;

import android.graphics.drawable.Animatable;
import android.widget.Button;
import android.widget.ImageView;

import com.jrhlibrary.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class SvgActivity extends BaseActivity {


    @BindView(R.id.iv_svg)
    ImageView ivSvg;
    @BindView(R.id.iv_lion)
    ImageView ivLion;
    @BindView(R.id.btn_to_second)
    Button btnToSecond;

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



    @OnClick(R.id.btn_to_second)
    public void onClick() {

        ActivityUtils.startActivity(this,SvgSecondActivity.class);
    }
}
