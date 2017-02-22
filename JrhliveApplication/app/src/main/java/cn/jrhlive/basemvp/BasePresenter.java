package cn.jrhlive.basemvp;

import android.support.annotation.NonNull;


/**
 * desc:
 * Created by jiarh on 16/9/14 15:26.
 */
public interface BasePresenter {
    void onCreate();
    void onAttachView(@NonNull BaseView view);
    void onDestory();
}
