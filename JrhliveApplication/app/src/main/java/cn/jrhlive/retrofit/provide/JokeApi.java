package cn.jrhlive.retrofit.provide;

import java.util.Map;

import cn.jrhlive.retrofit.bean.JokeBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * desc:
 * Created by jiarh on 17/1/18 15:55.
 */

public interface JokeApi {

    @GET("joke/content/list.from")
    Observable<JokeBean> getJoke(@QueryMap Map<String,String> map);
}
