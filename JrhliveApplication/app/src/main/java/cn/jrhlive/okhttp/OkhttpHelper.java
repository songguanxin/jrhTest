package cn.jrhlive.okhttp;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * desc:
 * Created by jiarh on 17/1/19 13:51.
 */

public class OkhttpHelper {
    public static final long DEFAULT_TIMEOUT=10;
    private static  OkHttpClient mHttpClient;
    public static OkHttpClient getOkHttpClient(){
        if (mHttpClient==null){
            synchronized (OkhttpHelper.class){
                if (mHttpClient==null){

                    mHttpClient =  getClient();
                }
            }
        }
        return mHttpClient;
    }

    private static OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //为所有请求添加头部 Header 配置的拦截器
        Interceptor headerIntercept = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("X-Client-Platform", "Android");
                builder.removeHeader("Accept");

                Request request = builder.build();

                return chain.proceed(request);
            }
        };


        Interceptor logInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();
                Response response = chain.proceed(request);
                ResponseBody responseBody = response.body();
                BufferedSource bufferedSource = responseBody.source();
                bufferedSource.request(Long.MAX_VALUE);
                Buffer buffer = bufferedSource.buffer();
                Charset UTF8 = Charset.forName("UTF-8");
                Log.e("REQUEST_JSON", buffer.clone().readString(UTF8));
                Log.e("REQUEST_URL", request.toString());
                return response;

            }
        };
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(logInterceptor);
        builder.addInterceptor(headerIntercept);
        return  builder.build();
    }
}
