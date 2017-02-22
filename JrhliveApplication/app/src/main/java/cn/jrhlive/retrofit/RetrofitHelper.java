package cn.jrhlive.retrofit;

import android.support.annotation.NonNull;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import cn.jrhlive.common.Constant;
import cn.jrhlive.okhttp.OkhttpHelper;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * desc:
 * Created by jiarh on 17/1/20 14:51.
 */

public class RetrofitHelper {

    private static Retrofit mRetrofit;

    public static Retrofit getRetrofit(){
        if (mRetrofit==null){
            synchronized (RetrofitHelper.class){
                if (mRetrofit==null){
                    mRetrofit = getRetrifit();
                }
            }
        }
        return mRetrofit;
    }

    @NonNull
    private static Retrofit getRetrifit() {
        return new Retrofit.Builder()
                .baseUrl(Constant.Joke.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkhttpHelper.getOkHttpClient())
                .build();
    }
}
