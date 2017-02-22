package com.jrhlibrary.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;

/**
 * desc: activity 工具类
 * Created by jiarh on 16/8/29 16:52.
 */
public class ActivityUtils {

    public static void startActivity(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }

    /**
     * api 最低为21
     *
     * @param activity
     * @param clazz
     */
    @SuppressWarnings("unchecked") public static void startTransitionActivity(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,TransitionHelper.createSafeTransitionParticipants(activity,true));


        activity.startActivity(intent, options.toBundle());
    }
}
