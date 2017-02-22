package cn.jrhlive.ani;

import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jrhlibrary.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;
import cn.jrhlive.utils.AnimationUtils;

public class AniAActivity extends BaseActivity {


    @BindView(R.id.btn_a)
    Button btnA;
    @BindView(R.id.circle_big)
    ImageView circleBig;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.bottom_lay)
    LinearLayout bottomLay;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.view_root)
    LinearLayout viewRoot;


    boolean changeSize;
    int saveWidth;
    boolean changePosition;
    @BindView(R.id.btn_to_b)
    Button btnToB;
    @BindView(R.id.btn_to_c)
    Button btnToC;

    @Override
    protected void initEvent() {

        AnimationUtils.setEnterWindowAni(this);
        AnimationUtils.setReturnWindowAni(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_ani_a;
    }

    @OnClick(R.id.btn_a)
    public void onAClick() {

        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.BOTTOM);
        slide.setDuration(200);
        TransitionManager.beginDelayedTransition(bottomLay, new Slide());
        for (int i = 0; i < bottomLay.getChildCount(); i++) {
            bottomLay.getChildAt(i).setVisibility(bottomLay.getChildAt(i).getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
        }
    }

    @OnClick(R.id.btn1)
    public void onBtn1Click() {

        TransitionManager.beginDelayedTransition(viewRoot);
        ViewGroup.LayoutParams params = circleBig.getLayoutParams();
        if (changeSize) {
            params.width = saveWidth;
        } else {
            saveWidth = params.width;
            params.width = 200;
        }

        changeSize = !changeSize;
        circleBig.setLayoutParams(params);

    }

    @OnClick(R.id.btn2)
    public void onBtn2Click() {

        TransitionManager.beginDelayedTransition(viewRoot);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) circleBig.getLayoutParams();
        if (changePosition) {
            params.gravity = Gravity.LEFT;
        } else {
            params.gravity = Gravity.RIGHT;
        }
        changePosition = !changePosition;
        circleBig.setLayoutParams(params);
    }

    @OnClick(R.id.btn_to_b)
    public void goB() {
        ActivityUtils.startTransitionActivity(this, AniBActivity.class);
    }

    @OnClick(R.id.btn_to_c)
    public void goc() {
        ActivityUtils.startTransitionActivity(this, AniCActivity.class);
    }





}
