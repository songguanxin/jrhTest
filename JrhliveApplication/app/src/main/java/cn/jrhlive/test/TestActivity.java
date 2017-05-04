package cn.jrhlive.test;


import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class TestActivity extends BaseActivity {
    ImageView img;

    float defaultX, defaultY;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

        img = (ImageView) this.findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAnim(true).start();
                createAnim(false).start();
            }
        });

        img.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                defaultX = img.getScaleX();
                defaultY = img.getScaleY();

                img.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }

    private SpringAnimation createAnim(boolean x) {
        SpringAnimation springAnimation = new SpringAnimation(img, x ? SpringAnimation.SCALE_X : SpringAnimation.SCALE_Y);
        SpringForce springForce = new SpringForce();
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        springForce.setStiffness(SpringForce.STIFFNESS_MEDIUM);
        springForce.setFinalPosition(x ? defaultX : defaultY);
        springAnimation.setStartValue(0.5f);
        springAnimation.setSpring(springForce);
        return springAnimation;
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_spring;
    }


}
