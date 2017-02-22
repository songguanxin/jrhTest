package cn.jrhlive.main.first.interactor.imp;

import android.os.Handler;
import android.os.Looper;

import java.util.Arrays;
import java.util.List;

import cn.jrhlive.basemvp.RequestCallBack;
import cn.jrhlive.main.entity.MainItem;
import cn.jrhlive.main.first.interactor.MainInteractor;

/**
 * desc:
 * Created by jiarh on 16/9/14 16:20.
 */
public class MainInteractorImp implements MainInteractor {

    List<MainItem>  datas = Arrays.asList(
            new MainItem("RxJava",0),
            new MainItem("scene_animation",1),
            new MainItem("surfaceView",2),
            new MainItem("svg",3),
            new MainItem("scrollActivity",4),
            new MainItem("DoubleClickTestActivity",5),
            new MainItem("ChromeCustomeTabsActivity",6),
            new MainItem("EventBus",7),
            new MainItem("RxAndroid",8),
            new MainItem("Retrofit",9),
            new MainItem("KenBurnsView",10)
    );

    public List<MainItem> getDatas(){
        return datas;
    }

    @Override
    public void loadMainItems(final RequestCallBack callBack) {

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(datas);
              
            }
        },2000);

    }
}
