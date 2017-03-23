package cn.jrhlive.eventbus;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.jrhlibrary.utils.ActivityUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.RxBus.RxBus;
import cn.jrhlive.activity.BaseActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.schedulers.ComputationScheduler;

public class MainEventBusActivity extends BaseActivity {


    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn)
    Button btn;


    private static final String TAG = "MainEventBusActivity";
    Disposable disposable;
    @Override
    protected void initEvent() {

        rxRefresh();
    }


    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (disposable.isDisposed())
        disposable.dispose();
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_main_event_bus;
    }


    @OnClick(R.id.btn)
    public void onClick() {
        ActivityUtils.startActivity(this, EeventBusSecondActivity.class);
    }

    /**
     * ThreadMode.POSTING (default)
     * ThreadMode.MAIN
     * ThreadMode.BACKGROUND
     * ThreadMode.ASYNC
     *
     * @param eventBusEvent
     */
    @Subscribe()
    public void onReceiveData(EventBusEvent eventBusEvent) {
        refreshUi(eventBusEvent);
    }



    /**
     * 同发起线程
     * @param eventBusEvent
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onReceive1(EventBusEvent eventBusEvent) {

        log(1,eventBusEvent);

    }

    /**
     * Ui线程
     * @param eventBusEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive2(EventBusEvent eventBusEvent) {
        log(2,eventBusEvent);
    }

    /**
     * 后台线程
     * @param eventBusEvent
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onReceive3(EventBusEvent eventBusEvent) {
        log(3,eventBusEvent);
    }

    /**
     * 在一个单独的线程
     * @param eventBusEvent
     */
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onReceive4(EventBusEvent eventBusEvent) {
        log(4,eventBusEvent);
    }

    private void log(int method,EventBusEvent eventBusEvent) {
        if (eventBusEvent != null) {

            Log.e(TAG, "onReceive"+method+": " + eventBusEvent.getMsg());
            Log.e(TAG, "onReceive"+method+": " + Thread.currentThread().getName());
            Log.e(TAG, "*******************************************************");
        }
    }


    private void rxRefresh() {

        disposable = RxBus.getDefault().toObservable(EventBusEvent.class)
                .subscribeOn(new ComputationScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .delay(3,TimeUnit.SECONDS)
                .subscribe(new Consumer<EventBusEvent>() {
                    @Override
                    public void accept(EventBusEvent eventBusEvent) throws Exception {
                        refreshUi(eventBusEvent);
                    }

                });


         RxBus.getDefault().toObservable(EventBusEvent.class)
                .subscribeOn(new ComputationScheduler())
                 .delay(5, TimeUnit.SECONDS)
                 .compose(this.<EventBusEvent>bindUntilEvent(ActivityEvent.STOP))
                 .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EventBusEvent>() {
                    @Override
                    public void accept(EventBusEvent eventBusEvent) throws Exception {
                        refreshUi2(eventBusEvent);

                    }

                });
    }
    private void refreshUi(final EventBusEvent eventBusEvent) {
        if (eventBusEvent != null) {
//            tv.setText(eventBusEvent.getMsg());
            System.out.println("aaaaaaaaaaa");

        }
    }
    private void refreshUi2(final EventBusEvent eventBusEvent) {
        if (eventBusEvent != null) {
//            tv.setText(eventBusEvent.getMsg());
            System.out.println("bbbbbbbb");

        }
    }

}
