package cn.jrhlive.main.test;

/**
 * desc:
 * Created by jiarh on 16/11/16 16:24.
 */

public interface LoginPresenter {
    void login(String name, String psd);
    boolean  isValidate();
}
