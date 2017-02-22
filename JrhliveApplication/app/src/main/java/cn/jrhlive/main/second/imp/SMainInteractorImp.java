package cn.jrhlive.main.second.imp;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.jrhlive.basemvp.RequestCallBack;
import cn.jrhlive.main.entity.MainItem;
import cn.jrhlive.main.second.DoMain;

/**
 * desc:
 * Created by jiarh on 16/9/14 16:20.
 */
public class SMainInteractorImp implements DoMain.MainInteractor<MainItem> {

    List<MainItem>  datas = Arrays.asList(
            new MainItem("RxJava",0),
            new MainItem("scene_animation",1),
            new MainItem("surfaceView",2),
            new MainItem("svg",3),
            new MainItem("scrollActivity",4)
    );

    /**
     * 数据过滤处理
     * @param oldList
     * @return
     */
    @Override
    public List<MainItem> getFiliterData(List<MainItem> oldList) {
        List<MainItem>  filiterList = new ArrayList<>();
        if (oldList!=null&&oldList.size()>0){
            for (MainItem item : oldList){
                if (item.getItemName().length()>4)
                filiterList.add(item);
            }
        }

            return filiterList;
    }

    /**
     * 获取网络数据 对原始数据的解析处理
     * @param callBack
     */
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
