package cn.jrhlive.ani;

import android.animation.Animator;
import android.support.v4.content.ContextCompat;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class AniCActivity extends BaseActivity {


    @BindView(R.id.sample_body)
    TextView sampleBody;
    @BindView(R.id.square_green)
    ImageView squareGreen;
    @BindView(R.id.square_red)
    ImageView squareRed;
    @BindView(R.id.square_blue)
    ImageView squareBlue;
    @BindView(R.id.square_yellow)
    ImageView squareYellow;
    @BindView(R.id.reveal_root)
    RelativeLayout revealRoot;
    private Interpolator interpolator;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

        interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);

        animateButtonsIn();

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_ani_c;
    }

    @OnClick(R.id.square_red)
    public void onR() {
        final ViewGroup.LayoutParams originalParams = squareRed.getLayoutParams();
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                animateRevealColor(revealRoot, R.color.sample_red);
                sampleBody.setText("ddddddd");
                sampleBody.setTextColor(ContextCompat.getColor(AniCActivity.this, R.color.theme_red_background));
                squareRed.setLayoutParams(originalParams);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });

        TransitionManager.beginDelayedTransition(revealRoot, transition);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        squareRed.setLayoutParams(params);


    }

    private void animateRevealColor(RelativeLayout revealRoot, int color) {

        int cx = (revealRoot.getLeft() + revealRoot.getRight()) / 2;
        int cy = (revealRoot.getTop() + revealRoot.getBottom()) / 2;

        boundsAni(revealRoot, cx, cy, color);

    }

    private Animator boundsAni(RelativeLayout revealRoot, int x, int y, int color) {
        float radius = (float) Math.hypot(revealRoot.getWidth(), revealRoot.getHeight());
        Animator ani = ViewAnimationUtils.createCircularReveal(revealRoot, x, y, 0, radius);
        revealRoot.setBackgroundColor(ContextCompat.getColor(this, color));
        ani.setDuration(500);
        ani.setInterpolator(new AccelerateDecelerateInterpolator());
        ani.start();
        return ani;
    }

    @OnClick(R.id.square_green)
    public void onG() {

        animateRevealColor(revealRoot, R.color.theme_green_background);
    }

    @OnClick(R.id.square_yellow)
    public void onY() {
        boundsAni(revealRoot, revealRoot.getWidth(), revealRoot.getHeight(), R.color.theme_yellow_background);

    }

    @OnClick(R.id.square_blue)
    public void onB() {
        boundsAni(revealRoot, revealRoot.getWidth() / 2, 0, R.color.theme_blue_background);

    }

    private void animateButtonsIn() {
        for (int i = 0; i < revealRoot.getChildCount(); i++) {
            View child = revealRoot.getChildAt(i);
            child.animate()
                    .setStartDelay(100 + i * 200)
                    .setInterpolator(interpolator)
                    .alpha(1)
                    .scaleX(1)
                    .scaleY(1);
        }
    }
}
