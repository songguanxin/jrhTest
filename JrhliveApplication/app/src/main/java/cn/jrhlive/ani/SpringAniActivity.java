package cn.jrhlive.ani;

import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class SpringAniActivity extends BaseActivity {


    @BindView(R.id.btn_spring)
    Button btnSpring;
    @BindView(R.id.tv_damping_ratio)
    TextView tvDampingRatio;
    @BindView(R.id.sk_dampingratio)
    SeekBar skDampingratio;
    @BindView(R.id.tv_stiffness)
    TextView tvStiffness;

    float dampingValue = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY;
    float stiffnessValue = SpringForce.STIFFNESS_HIGH;
    @BindView(R.id.sk_stiffness)
    SeekBar skStiffness;


    @Override
    protected void initEvent() {

        skDampingratio.setMax(100);


        skDampingratio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress>0)
                dampingValue = progress/100f;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        skStiffness.setMax((int)SpringForce.STIFFNESS_HIGH);
        skStiffness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress>0)
                stiffnessValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    @Override
    protected void initView() {

        setSwipeBackEnable(false);


    }

    @Override
    protected int getViewId() {
        return R.layout.activity_spring_ani;
    }


    @OnClick(R.id.btn_spring)
    public void onViewClicked(View v) {

        SpringForce springForce = new SpringForce(1f)
                .setDampingRatio(dampingValue)
                .setStiffness(stiffnessValue);
        final SpringAnimation anim = new SpringAnimation(v, SpringAnimation.SCALE_X).setSpring(springForce);
        anim.setStartValue(0.5f);
        anim.start();

        final SpringAnimation animY = new SpringAnimation(v, SpringAnimation.SCALE_Y).setSpring(springForce);
        animY.setStartValue(0.5f);
        animY.start();


    }


    ImageView img;

    float defaultX, defaultY;


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
}
