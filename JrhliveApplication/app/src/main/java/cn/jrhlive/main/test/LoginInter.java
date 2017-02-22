package cn.jrhlive.main.test;

import cn.jrhlive.basemvp.RequestCallBack;

/**
 * desc:
 * Created by jiarh on 16/11/16 16:27.
 */

public interface LoginInter {
    void login(String name, String psd, RequestCallBack<Result> callBack);
}
