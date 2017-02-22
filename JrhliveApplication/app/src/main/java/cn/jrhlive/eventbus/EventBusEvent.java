package cn.jrhlive.eventbus;

/**
 * desc:
 * Created by jiarh on 16/12/12 10:27.
 */

public class EventBusEvent {
    String msg;

    public EventBusEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
