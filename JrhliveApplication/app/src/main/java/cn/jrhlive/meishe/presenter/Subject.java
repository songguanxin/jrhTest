package cn.jrhlive.meishe.presenter;

/**
 * desc:
 * Created by jiarh on 17/5/11 15:19.
 */

public interface Subject {

    /**
     * 注册观察者
     */
    void registerObserver(Observer observer);

    /**
     * 移除观察者
     */
    void removeObserver(Observer observer);

    /**
     * 通知观察者
     */
    void notifyObservers();
}
