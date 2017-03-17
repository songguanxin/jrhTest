package cn.jrhlive.ani;

import android.animation.ObjectAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class BallDownUpActivity extends BaseActivity {


    @BindView(R.id.img_ball)
    ImageView imgBall;
    @BindView(R.id.activity_ball_down_up)
    RelativeLayout activityBallDownUp;
    @BindView(R.id.btn_toggle)
    Button btnToggle;

    boolean isReset;

    float startPos,stopPos;
    float radius;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {


        imgBall.post(new Runnable() {
            @Override
            public void run() {
                radius = imgBall.getWidth()/2;
                startPos = 0;
                stopPos = getResources().getDisplayMetrics().heightPixels-imgBall.getHeight()*3/2;
            }
        });


    }

    @Override
    protected int getViewId() {
        return R.layout.activity_ball_down_up;
    }


    @OnClick(R.id.btn_toggle)
    public void onClick() {


        if (!isReset){
            start();
        }else {
            stop();
        }
        isReset=!isReset;
        btnToggle.setText(isReset?"reset":"start");
    }

    public void start(){

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imgBall,"translationY",startPos,stopPos);
        objectAnimator.setDuration(1500);
        objectAnimator.setInterpolator(new BounceInterpolator());
        objectAnimator.start();


    }

    public void stop(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imgBall,"translationY",stopPos,startPos);
        objectAnimator.setDuration(300);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();

    }
}
