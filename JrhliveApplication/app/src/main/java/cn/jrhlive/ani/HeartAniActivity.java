package cn.jrhlive.ani;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class HeartAniActivity extends BaseActivity {


    @BindView(R.id.iv_heart)
    ImageView ivHeart;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_heart_ani;
    }


    @OnClick(R.id.iv_heart)
    public void onClick() {

//       ObjectAnimator animator =  ObjectAnimator.ofFloat(ivHeart,"scale",0,0.75f,1.0f,1.25f,1.0f,0.75f,0)
//                .setDuration(500);
//                animator.setInterpolator(new AccelerateDecelerateInterpolator());
//        animator.start();


        PropertyValuesHolder pvhA = PropertyValuesHolder.ofFloat("alpha", 0f,
                1f, 0f);
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("scaleX", 0f,
                1.25f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleY", 0f,
                1.25f);

       ObjectAnimator animator=ObjectAnimator.ofPropertyValuesHolder(ivHeart, pvhA, pvhX,pvhY).setDuration(1500);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                System.out.println("duration="+animation.getDuration());
                System.out.println("value="+animation.getAnimatedValue());
            }

        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.start();

    }
}
