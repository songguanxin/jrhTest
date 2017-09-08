package cn.jrhlive.richeditor.events;

/**
 * desc: 事件更新
 * Created by jiarh on 17/9/7 16:52.
 */

public class Event<T> {
    int code;
    T t;

    public Event(int code, T t) {
        this.code = code;
        this.t = t;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
