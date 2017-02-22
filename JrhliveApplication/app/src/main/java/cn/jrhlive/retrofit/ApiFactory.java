package cn.jrhlive.retrofit;

/**
 * desc:
 * Created by jiarh on 17/1/20 14:56.
 */

public  class ApiFactory {

    public static  <T> T getApi(Class<T> t){
        return RetrofitHelper.getRetrofit().create(t);
    }
 }
