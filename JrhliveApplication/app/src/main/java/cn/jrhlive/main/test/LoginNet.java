package cn.jrhlive.main.test;

import android.text.TextUtils;

import cn.jrhlive.basemvp.RequestCallBack;

/**
 * desc:
 * Created by jiarh on 16/11/16 16:26.
 */

public class LoginNet implements LoginInter {

    @Override
    public void login(String name, String psd, RequestCallBack<Result> callBack) {
        if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(psd)){
            if(name.equals("android")&&psd.equals("123456")){
                if (callBack!=null)
                    callBack.onSuccess(new Result(0,"success"));

            }else {
                callBack.onError("fail");
            }
        }
    }
}
