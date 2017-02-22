package cn.jrhlive.main.first.interactor;


import cn.jrhlive.basemvp.BaseInteractor;
import cn.jrhlive.basemvp.RequestCallBack;

/**
 * desc:
 * Created by jiarh on 16/9/14 16:17.
 */
public interface MainInteractor<T> extends BaseInteractor {
    void loadMainItems(RequestCallBack<T> callBack);
}
