package cn.jrhlive.basemvp;

/**
 * desc:
 * Created by jiarh on 16/9/14 15:28.
 */
public interface BaseView {
    void showProgress();
    void hideProgress();
    void showMsg(String msg);
}
