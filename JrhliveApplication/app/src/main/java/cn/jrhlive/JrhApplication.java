package cn.jrhlive;

import android.app.Application;

import com.antfortune.freeline.FreelineCore;
import com.facebook.stetho.Stetho;

/**
 * desc:
 * Created by jiarh on 16/11/14 10:49.
 */

public class JrhApplication extends Application {

    public static JrhApplication APP;
    @Override
    public void onCreate() {

        FreelineCore.init(this);
        super.onCreate();
        APP=this;
//        MultiDex.install(this);
        Stetho.initializeWithDefaults(this);
//        JrhApplicationContext.application = this;
//        JrhApplicationContext.context = this;
//        QuPaiManagerUtil.init();

    }
    public JrhApplication getApplicationContext(){
        return APP;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

    }
}
