package cn.jrhlive.basemvp;

import com.blankj.utilcode.utils.StringUtils;

import cn.jrhlive.utils.ToastUtil;

/**
 * desc:
 * Created by jiarh on 17/1/20 16:22.
 */

public abstract class BaseRxPresenterImpl<V extends BaseView, T> implements RequestCallBack<T> {

    protected V mView;


    public BaseRxPresenterImpl(V v) {
        this.mView = v;
    }


    @Override
    public void beforeRequest() {
        showDialog();
    }

    private void showDialog() {
        if (mView != null)
            mView.showProgress();
    }

    @Override
    public void onSuccess(T t) {
        closeDialog();
        doOnSuccess(t);

    }

    private void closeDialog() {
        if (mView!= null)
            mView.hideProgress();
    }

    @Override
    public void onError(String msg) {
        closeDialog();
        if (!StringUtils.isEmpty(msg)) ToastUtil.showMessage(msg);
        doOnError(msg);
    }

    protected abstract void doGetData();

    protected abstract void doOnSuccess(T t);

    protected abstract void doOnError(String msg);
}
