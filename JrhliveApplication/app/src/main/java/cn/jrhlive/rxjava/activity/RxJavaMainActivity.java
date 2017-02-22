package cn.jrhlive.rxjava.activity;

import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import cn.jrhlive.R;
import cn.jrhlive.activity.BaseActivity;

public class RxJavaMainActivity extends BaseActivity {

    @BindView(R.id.btn_show)
    Button btnShow;
    private static final String TAG = "RxJavaMainActivity";
    String[] data = {"java", "android", "php", "sql", "c++"};
    @BindView(R.id.iv_show)
    ImageView ivShow;

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getViewId() {
        return R.layout.activity_rx_java_main;
    }

//    @OnClick(R.id.btn_show)
//    public void onBtnClick() {
////        logString(data);
////        logString2(data);
////        logString3(data);
////        logString4(data);
////        logString5(data);
////        logString6(data);
////        logString7(data);
////        logString8(data);
////        logString9(data);
////        logString10(data);
////        showPic();
//        doubleClick(data[0]);
//    }
//
//    public void logString(String[] str) {
//
//        Observable.from(str).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                Log.d(TAG, "call() called with: " + "s = [" + s + "]");
//            }
//        });
//
//    }
//
//    /**
//     * 1:创建消息源，Observable
//     * 2:创建观察者
//     * 3：消息源绑定观察者
//     *
//     * @param str
//     */
//    public void logString2(final String[] str) {
//
//        Observable<String[]> observable = Observable.create(new Observable.OnSubscribe<String[]>() {
//            @Override
//            public void call(Subscriber<? super String[]> subscriber) {
//                subscriber.onNext(str);
//                subscriber.onCompleted();
//            }
//        });
//        Subscriber<String[]> mySubscriber = new Subscriber<String[]>() {
//            @Override
//            public void onCompleted() {
//                Log.d(TAG, "onCompleted() called with: " + "");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "onError() called with: " + "e = [" + e + "]");
//            }
//
//            @Override
//            public void onNext(String[] strings) {
//
//                logString(strings);
//
////                Log.d(TAG, "onNext() called with: " + "strings = [" + strings.length + "]");
//            }
//        };
//
//        observable.subscribe(mySubscriber);
//    }
//
//    /**
//     * 只关心onNext(),传一个参数
//     *
//     * @param str
//     */
//    public void logString3(String[] str) {
//
//        Observable.just(str).subscribe(new Action1<String[]>() {
//            @Override
//            public void call(String[] strings) {
//                logString(strings);
//            }
//        });
//
//    }
//
//    /**
//     * map使用
//     *
//     * @param str
//     */
//    public void logString4(final String[] str) {
//
//        logString(str);
//        Observable.just(str)
//                .map(new Func1<String[], String[]>() {
//                    @Override
//                    public String[] call(String[] strings) {
//                        strings[0] = "RxJava";
//                        return strings;
//                    }
//                }).map(new Func1<String[], String[]>() {
//            @Override
//            public String[] call(String[] strings) {
//                strings[1] = "Rxandroid";
//                return strings;
//            }
//        }).subscribe(new Action1<String[]>() {
//            @Override
//            public void call(String[] strings) {
//                logString(strings);
//            }
//        });
//
//    }
//
//    /**
//     * flatmap 小试牛刀
//     *
//     * @param str
//     */
//    public void logString5(String[] str) {
//        Observable.just(str).flatMap(new Func1<String[], Observable<String>>() {
//            @Override
//            public Observable<String> call(String[] strings) {
//                return Observable.from(strings);
//            }
//        }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                Log.d(TAG, "call() called with: " + "s = [" + s + "]");
//            }
//        });
//
//    }
//
//    /**
//     * flatmap 初入江湖
//     *
//     * @param str
//     */
//    public void logString6(String[] str) {
//        Observable.just(str).flatMap(new Func1<String[], Observable<String>>() {
//            @Override
//            public Observable<String> call(String[] strings) {
//                return Observable.from(strings);
//            }
//        }).flatMap(new Func1<String, Observable<String>>() {
//            @Override
//            public Observable<String> call(String s) {
//                return getFirstChar(s);
//            }
//        }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//
//                Log.d(TAG, "call() called with: " + "s = [" + s + "]");
//            }
//        });
//
//    }
//
//    /**
//     * 线程
//     *
//     * @param str
//     */
//    public void logString10(String[] str) {
//        Observable.just(str).flatMap(new Func1<String[], Observable<String>>() {
//            @Override
//            public Observable<String> call(String[] strings) {
//                return Observable.from(strings);
//            }
//        }).subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.io())
//                .flatMap(new Func1<String, Observable<String>>() {
//                    @Override
//                    public Observable<String> call(String s) {
//                        return getFirstChar(s);
//                    }
//                }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//
//                Log.d(TAG, "call() called with: " + "s = [" + s + "]");
//            }
//        });
//
//    }
//
//    /**
//     * doOnNext() 输出数据之前执行某项操作
//     *
//     * @param str
//     */
//    public void logString9(final String[] str) {
//        Observable.just(str).flatMap(new Func1<String[], Observable<String>>() {
//            @Override
//            public Observable<String> call(String[] strings) {
//                return Observable.from(strings);
//            }
//        }).flatMap(new Func1<String, Observable<String>>() {
//            @Override
//            public Observable<String> call(String s) {
//                return getFirstChar(s);
//            }
//        }).doOnNext(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                s += "00";
//                Log.d(TAG, "call() called with: " + "s = [" + s + "]");
//            }
//        }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//
//                Log.d(TAG, "call() called with: " + "s = [" + s + "]");
//            }
//        });
//
//    }
//
//    /**
//     * fliter 过滤操作
//     *
//     * @param str
//     */
//    public void logString7(String[] str) {
//        Observable.just(str).flatMap(new Func1<String[], Observable<String>>() {
//            @Override
//            public Observable<String> call(String[] strings) {
//                return Observable.from(strings);
//            }
//        }).flatMap(new Func1<String, Observable<String>>() {
//            @Override
//            public Observable<String> call(String s) {
//                return getFirstChar(s);
//            }
//        }).filter(new Func1<String, Boolean>() {
//            @Override
//            public Boolean call(String s) {
//                //过滤条件
//                return s.equals("android");
//            }
//        }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//
//                Log.d(TAG, "call() called with: " + "s = [" + s + "]");
//            }
//        });
//
//    }
//
//    /**
//     * take 只获取特定数量的数据
//     *
//     * @param str
//     */
//    public void logString8(String[] str) {
//        Observable.just(str).flatMap(new Func1<String[], Observable<String>>() {
//            @Override
//            public Observable<String> call(String[] strings) {
//                return Observable.from(strings);
//            }
//        }).flatMap(new Func1<String, Observable<String>>() {
//            @Override
//            public Observable<String> call(String s) {
//                return getFirstChar(s);
//            }
//        }).take(3).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//
//                Log.d(TAG, "call() called with: " + "s = [" + s + "]");
//            }
//        });
//
//    }
//
//    /**
//     * 展示图片
//     */
//    public void showPic() {
////
////        Observable.create(new Observable.OnSubscribe<Drawable>() {
////            @Override
////            public void call(Subscriber<? super Drawable> subscriber) {
////
////                Drawable d = getTheme().getDrawable(R.mipmap.ic_launcher);
////                subscriber.onNext(d);
////                subscriber.onCompleted();
////
////            }
////        }).subscribeOn(Schedulers.io())
////        .observeOn(Schedulers.io())
////                .subscribe(new Observer<Drawable>() {
////            @Override
////            public void onCompleted() {
////
////            }
////
////            @Override
////            public void onError(Throwable e) {
////
////            }
////
////            @Override
////            public void onNext(Drawable drawable) {
////
////                ivShow.setImageDrawable(drawable);
////            }
////        });
//
//    }
//
//    /**
//     * throttleFirst test
//     */
//    public void doubleClick(String data){
//
//        Observable.just(data)
//                .throttleFirst(2000, TimeUnit.SECONDS)
//                .subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//
//                Log.d(TAG, "call() called with: " + "s = [" + s + "]");
//            }
//        });
//
//    }
//    private Observable<String> getFirstChar(String s) {
//
//        return Observable.just(s);
//    }
//

}
