package cn.jrhlive.rxandroid;

import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;


public class MainRxAndroidActivity extends BaseActivity {


    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.button4)
    Button button4;
    StringBuffer sb = new StringBuffer();
    private static final String TAG = "MainRxAndroidActivity";


    AsyncSubject<Integer> asyncSubject = AsyncSubject.create();
    BehaviorSubject<Integer> behaviorSubject = BehaviorSubject.createDefault(-1);
    @BindView(R.id.btn_leak)
    Button btnLeak;
    @BindView(R.id.activity_main_rx_android)
    RelativeLayout activityMainRxAndroid;

    @Override
    protected void initEvent() {

//        subOn(asyncSubject);
//        subOn(behaviorSubject);

    }

    private void subOn(Subject subject) {
        subject.onNext(1);
        subject.onNext(2);
        subject.onNext(3);
        subject.onComplete();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_main_rx_android;
    }


    @OnClick(R.id.button4)
    public void onClick() {

//        refreshTv();
//        testInterval();

//        testSubjct(asyncSubject);
        testSubjct(behaviorSubject);
        subOn(behaviorSubject);

    }

    @OnClick(R.id.btn_leak)
    public void onLeakClicked() {

        Observable.interval(1, 1, TimeUnit.SECONDS)
                .compose(this.<Long>bindToLifecycle())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println(new Date().toString());
                    }
                });

    }

    private void testSubjct(Subject subject) {
        subject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                textView3.setText(integer + "");
            }
        });


    }

    public void testInterval() {
        Observable.interval(1, 2, TimeUnit.SECONDS, Schedulers.io()).subscribe(

                new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("$$$$$$");
                        System.out.println(String.valueOf(aLong));
                    }
                });
    }

    public void refreshTv() {

        sb.setLength(0);
        Observer<String> observer = new Observer<String>() {


            @Override
            public void onError(Throwable e) {

                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {

                sb.append(s);
                Log.d(TAG, "onNext: " + s);
                textView3.setText(sb.toString());
            }
        };
        Observable.just("1", "2", "3", "4")
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

}

