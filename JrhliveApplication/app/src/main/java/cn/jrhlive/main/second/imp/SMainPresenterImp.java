package cn.jrhlive.main.second.imp;

import java.util.List;

import cn.jrhlive.basemvp.BasePresenterImp;
import cn.jrhlive.main.entity.MainItem;
import cn.jrhlive.main.second.DoMain;

/**
 * desc:
 * Created by jiarh on 16/9/14 16:15.
 */
public class SMainPresenterImp extends BasePresenterImp<DoMain.MainView, List<MainItem>> implements DoMain.MainPresenter {

    private DoMain.MainInteractor<List<MainItem>> mainInteractor;
    private static final String TAG = "MainPresenterImp";

    public SMainPresenterImp(DoMain.MainInteractor mainInteractor) {
        this.mainInteractor = mainInteractor;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        beforeRequest();
        getDatas();
    }

    @Override
    public void getDatas() {
        mainInteractor.loadMainItems(this);
    }

    @Override
    public boolean isFilter() {


        return true;
    }

    @Override
    public boolean isShowToast() {

        return false;
    }

    @Override
    public void onSuccess(List<MainItem> data) {
        super.onSuccess(data);
        if (data != null && data.size() > 0) {
            if (isFilter())
                mView.initViewData(mainInteractor.getFiliterData(data));
            else
                mView.initViewData(data);
        }
       if (isShowToast()){
           mView.showMsg("success");
       }
    }
}
