package cn.jrhlive.basemvp;


/**
 * desc:
 * Created by jiarh on 16/9/14 15:26.
 */
public class BasePresenterImp<T extends BaseView,E> implements BasePresenter,RequestCallBack<E> {

    protected T mView;



    @Override
    public void onCreate() {

    }

    @Override
    public void onAttachView(BaseView view) {
        this.mView= (T) view;
    }

    @Override
    public void onDestory() {

    }

    @Override
    public void beforeRequest() {

        mView.showProgress();
    }

    @Override
    public void onSuccess(E data) {

        mView.hideProgress();
    }

    @Override
    public void onError(String msg) {
        mView.hideProgress();
        mView.showMsg(msg);

    }
}
