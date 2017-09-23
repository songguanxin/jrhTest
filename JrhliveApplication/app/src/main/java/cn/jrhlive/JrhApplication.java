package cn.jrhlive;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.jrhlibrary.utils.Mobile;

/**
 * desc:
 * Created by jiarh on 16/11/14 10:49.
 */

public class JrhApplication extends Application {

    public static JrhApplication APP;
    @Override
    public void onCreate() {

//        FreelineCore.init(this);
        super.onCreate();
        APP=this;
//        MultiDex.install(this);
        Stetho.initializeWithDefaults(this);
//        JrhApplicationContext.application = this;
//        JrhApplicationContext.context = this;
//        QuPaiManagerUtil.init();

        Mobile.init(getApplicationContext());

    }
    public JrhApplication getApplicationContext(){
        return APP;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

    }
}
