package cn.jrhlive.utils;

import android.app.Activity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Gravity;

import cn.jrhlive.R;

/**
 * desc:
 * Created by jiarh on 16/9/8 10:12.
 */
public class AnimationUtils {

    public static void setExitWindowAni(Activity context){



        Fade fade = (Fade) TransitionInflater.from(context).inflateTransition(R.transition.activity_fade);
        context.getWindow().setExitTransition(getslideAni());
//        context.getWindow().setReturnTransition(getFadeAni());
    }


    public static void setEnterWindowAni(Activity context){

//        Slide slide = (Slide) TransitionInflater.from(context).inflateTransition(R.transition.activity_slide);
        context.getWindow().setEnterTransition(getslideAni());
    }

    public static void setReenterWindowAni(Activity activity){
        activity.getWindow().setReenterTransition(new Slide());
    }

    public static void setReturnWindowAni(Activity activity){
        activity.getWindow().setReturnTransition(new Explode());
    }

    public static Slide getslideAni(){

        Slide slide = new Slide();
        slide.setDuration(300);
        slide.setSlideEdge(Gravity.TOP);
        return slide;

    }

    public static Fade getFadeAni(){
        Fade fade = new Fade();
        fade.setDuration(300);
        return fade;

    }
}
