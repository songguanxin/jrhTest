package cn.jrhlive.basemvp;

/**
 * desc:
 * Created by jiarh on 17/1/22 11:03.
 */

public class BaseExpection extends Exception {
    String msg ;

    public BaseExpection(String detailMessage, String msg) {
        super(detailMessage);
        this.msg = msg;
    }
}
