package cn.jrhlive.main.first.presenter.imp;

import java.util.List;

import cn.jrhlive.basemvp.BasePresenterImp;
import cn.jrhlive.main.entity.MainItem;
import cn.jrhlive.main.first.interactor.MainInteractor;
import cn.jrhlive.main.first.interactor.imp.MainInteractorImp;
import cn.jrhlive.main.first.presenter.MainPresenter;
import cn.jrhlive.main.first.view.MainView;

/**
 * desc:
 * Created by jiarh on 16/9/14 16:15.
 */
public class MainPresenterImp extends BasePresenterImp<MainView,List<MainItem>> implements MainPresenter {

    private MainInteractor<List<MainItem>> mainInteractor;
    private static final String TAG = "MainPresenterImp";

    public MainPresenterImp(MainInteractorImp mainInteractorImp) {
        this.mainInteractor = mainInteractorImp;
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
    public void onSuccess(List<MainItem> data) {
        super.onSuccess(data);
        mView.initViewData(data);
        mView.showMsg("success");
    }
}
