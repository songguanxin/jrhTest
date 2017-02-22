package cn.jrhlive.main.test;

import cn.jrhlive.basemvp.RequestCallBack;

/**
 * desc:
 * Created by jiarh on 16/11/16 16:25.
 */

public class LoginPresenterImp implements LoginPresenter {

    LoginNet loginNet;
    LoginView loginView;
    String name,psd;

    public LoginPresenterImp(LoginNet loginNet,LoginView view) {
        this.loginNet = loginNet;
        this.loginView = view;

    }

    @Override
    public void login(String name, String psd) {
        this.name = name;
        this.psd = psd;
        if (isValidate()){
            loginNet.login(name, psd, new RequestCallBack<Result>() {
                @Override
                public void beforeRequest() {
                    loginView.showProgress();
                }

                @Override
                public void onSuccess(Result data) {

                    if (data.getCode()==0){
                        loginView.isloginSuccess(true);
                    }
                    else
                        loginView.isloginSuccess(false);
                    loginView.hideProgress();

                }

                @Override
                public void onError(String msg) {

                    loginView.hideProgress();
                    loginView.isloginSuccess(false);
                }
            });
        }else {
            loginView.isloginSuccess(false);
        }

    }

    @Override
    public boolean isValidate() {
        if (name.length()>90){
            return false;
        }

        return false;
    }
}
