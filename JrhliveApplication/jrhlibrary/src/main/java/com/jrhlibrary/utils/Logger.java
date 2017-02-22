package com.jrhlibrary.utils;

import android.util.Log;

/**
 * desc:
 * Created by jiarh on 16/12/13 14:51.
 */

public class Logger {

    public static void logE(Object clazz,String msg){
        Log.e(clazz.getClass().getSimpleName(),msg);
    }
}
