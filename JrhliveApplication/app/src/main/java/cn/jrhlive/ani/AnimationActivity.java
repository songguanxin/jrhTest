package cn.jrhlive.ani;

import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.jrhlibrary.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;
import cn.jrhlive.utils.AnimationUtils;

public class AnimationActivity extends BaseActivity {


    @BindView(R.id.btn_go)
    Button btnGo;
    @BindView(R.id.lay_root)
    LinearLayout layRoot;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;
    @BindView(R.id.tv5)
    TextView tv5;
    @BindView(R.id.tv6)
    TextView tv6;

    List<TextView> tvs = new ArrayList<>();
    @BindView(R.id.circle)
    ImageView circle;
    @BindView(R.id.btn_heart)
    Button btnHeart;
    @BindView(R.id.sv_search)
    EditText svSearch;
    @BindView(R.id.btn_spring)
    Button btnSpring;
    @BindView(R.id.btn_behavior)
    Button btnBehavior;

    @Override
    protected void initEvent() {

        AnimationUtils.setExitWindowAni(this);
        AnimationUtils.setReenterWindowAni(this);


    }

    @OnClick(R.id.sv_search)
    public void goSearch() {

        ActivityUtils.startActivity(this, SearchBarActivity.class);
    }

    @Override
    protected void initView() {

        tvs.add(tv1);
        tvs.add(tv2);
        tvs.add(tv3);
        tvs.add(tv4);
        tvs.add(tv5);
        tvs.add(tv6);


        DrawableCompat.setTint(circle.getDrawable(), getResources().getColor(R.color.color1));


    }

    @Override
    protected int getViewId() {
        return R.layout.activity_animation;
    }

    @OnClick(R.id.btn_go)
    public void onGoClick() {
        SpringSystem springSystem = SpringSystem.create();

        Spring spring = springSystem.createSpring();

        spring.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                btnGo.setScaleX(value);
                btnGo.setScaleY(value);
            }

            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {

            }
        });
        spring.setEndValue(1f);


//        TransitionManager.beginDelayedTransition(layRoot, new Explode());
//        for (TextView tv : tvs) {
//            tv.setVisibility(tv.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
//        }
//        ActivityUtils.startActivity(this, AniAActivity.class);

//        Intent intent = new Intent(this, AniAActivity.class);
//        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, new Pair<View, String>(circle, circle.getTransitionName()));
//        startActivity(intent, activityOptions.toBundle());

    }


    @OnClick(R.id.btn_heart)
    public void onClick() {
        ActivityUtils.startActivity(this, HeartAniActivity.class);
    }


    @OnClick(R.id.btn_spring)
    public void onViewClicked() {

        ActivityUtils.startActivity(this, SpringAniActivity.class);
    }



    @OnClick(R.id.btn_behavior)
    public void onBehaviorClicked() {
        ActivityUtils.startActivity(this,FllowBehaviorActivity.class);
    }
}
