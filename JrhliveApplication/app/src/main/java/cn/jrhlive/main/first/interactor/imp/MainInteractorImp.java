package cn.jrhlive.main.first.interactor.imp;

import java.util.ArrayList;
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
            new MainItem("KenBurnsView",10),
            new MainItem("ConstraintLayout",11),
            new MainItem("BallAniDownUp",12),
            new MainItem("testActivity",13),
            new MainItem("qiNiuPlayer",14),
            new MainItem("quPaiTest",15),
            new MainItem("NestScrollingTest",16),
            new MainItem("navigation_bottom_bar",17),
            new MainItem("RichEditText",18),
            new MainItem("ImageCrop",19),
            new MainItem("tablelayout",20),
            new MainItem("multiItemRecycleView",21),
            new MainItem("BeizerView",22),
            new MainItem("MeiShe",23),
            new MainItem("CutView",24),
            new MainItem("BoundAniActivity",25),
            new MainItem("RichEditor",26),
            new MainItem("ToastShow",27),
            new MainItem("temp",100)






    );

    public List<MainItem> getDatas(){
        List<MainItem> mainItems = new ArrayList<>();
        for (MainItem data : datas) {
           mainItems.add(0,data);
        }
        return mainItems;
    }

    @Override
    public void loadMainItems(final RequestCallBack callBack) {

//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                callBack.onSuccess(datas);
//
//            }
//        },2000);
        callBack.onSuccess(getDatas());
    }
}
