package cn.jrhlive.basemvp;

/**
 * desc: 请求回调
 * Created by jiarh on 16/9/14 15:22.
 */
public interface RequestCallBack<T>{
    void beforeRequest();
    void onSuccess(T data);
    void onError(String msg);
}
