package cn.jrhlive.ani;

import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;
import cn.jrhlive.utils.AnimationUtils;

public class AniBActivity extends BaseActivity {


    @BindView(R.id.view_root)
    FrameLayout viewRoot;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn_group)
    LinearLayout btnGroup;

    List<View> btns = new ArrayList<>();


    private static final int DELAY = 100;
    Scene scene0,scene1,scene2,scene3,scene4;




    @Override
    protected void initEvent() {
        AnimationUtils.setEnterWindowAni(this);
        getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {

                getWindow().getEnterTransition().removeListener(this);
                TransitionManager.go(scene0);
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

    }

    @Override
    protected void initView() {

        btns.add(btn1);
        btns.add(btn2);
        btns.add(btn3);
        btns.add(btn4);
        scene0 = Scene.getSceneForLayout(viewRoot, R.layout.activity_animations_scene0,this);
        scene0.setEnterAction(new Runnable() {
            @Override
            public void run() {
                for (int i =0; i<btns.size();i++) {
                  btns.get(i).animate().setStartDelay(i*DELAY).scaleX(1).scaleY(1);
                }
            }
        });
        scene0.setExitAction(new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition(viewRoot);
                View title = scene0.getSceneRoot().findViewById(R.id.scene0_title);
                title.setScaleX(0);
                title.setScaleY(0);
            }
        });
        scene1 = Scene.getSceneForLayout(viewRoot,R.layout.activity_animations_scene1,this);
        scene2 = Scene.getSceneForLayout(viewRoot,R.layout.activity_animations_scene2,this);
        scene3 = Scene.getSceneForLayout(viewRoot,R.layout.activity_animations_scene3,this);
        scene4 = Scene.getSceneForLayout(viewRoot,R.layout.activity_animations_scene4,this);

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_ani_b;
    }

    @OnClick(R.id.btn1)
    public void onBtn1Click(){
        TransitionManager.go(scene1,new ChangeBounds());
    }
     @OnClick(R.id.btn2)
    public void onBtn2Click(){

         TransitionManager.go(scene2, TransitionInflater.from(this).inflateTransition(R.transition.slide_and_changebounds));
    }
     @OnClick(R.id.btn3)
    public void onBtn3Click(){
         TransitionManager.go(scene3, TransitionInflater.from(this).
                 inflateTransition(R.transition.slide_and_changebounds_sequential));
    }
     @OnClick(R.id.btn4)
    public void onBtn4Click(){
         TransitionManager.go(scene4,TransitionInflater.from(this).inflateTransition(R.transition.slide_and_changebounds_sequential_with_interpolators));
     }

}


