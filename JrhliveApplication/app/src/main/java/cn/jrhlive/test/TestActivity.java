package cn.jrhlive.test;


import android.graphics.drawable.Animatable;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class TestActivity extends BaseActivity {


    @BindView(R.id.iv_menu)
    ImageView ivMenu;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

    }

    public void menu(View v) {
        if (ivMenu.getDrawable() instanceof Animatable) {
            ((Animatable) ivMenu.getDrawable()).start();
        }
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_test;
    }



}
