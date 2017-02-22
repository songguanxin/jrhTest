//package rxtest;
//
//
//import android.util.Log;
//
//import org.junit.Test;
//
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import resource.Person;
//import resource.RxData;
//import resource.Student;
//import resource.Subject;
//import rx.Observable;
//import rx.Observer;
//import rx.Subscriber;
//import rx.Subscription;
//import rx.functions.Action0;
//import rx.functions.Action1;
//import rx.functions.Func1;
//import rx.functions.Func2;
//import rx.observables.GroupedObservable;
//import rx.schedulers.Schedulers;
//import rx.subjects.AsyncSubject;
//import rx.subjects.BehaviorSubject;
//import rx.subjects.PublishSubject;
//import rx.subjects.ReplaySubject;
//import rxtest.rxhomework.DataFactory;
//import rxtest.rxhomework.bean.Group;
//import rxtest.rxhomework.bean.Member;
//
///**
// * desc:
// * Created by jiarh on 16/12/13 14:43.
// */
//
//public class RxAndroid {
//
//    private static final String TAG = "RxAndroid";
//
//    List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
//
//    @Test
//    public void testFlatMap() {
//        Observable.from(new RxData().getStudent())
//                .flatMap(new Func1<Student, Observable<Subject>>() {
//                    @Override
//                    public Observable<Subject> call(Student student) {
//                        System.out.println(student.getName() + "&&&&");
//                        return Observable.from(student.getSubjects());
//                    }
//                }).subscribe(new Action1<Subject>() {
//            @Override
//            public void call(Subject subject) {
//
//                System.out.print(subject.getName() + "##");
//            }
//        });
//
//    }
//
//
//    @Test
//    public void testJust() {
//        Observable.just("1", "2", "3")
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//
//                        System.out.println(s);
//                    }
//                });
//    }
//
//    @Test
//    public void testFrom() {
//        Observable.from(new String[]{"1", "2", "3"})
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onNext(String s) {
//                        System.out.println(s);
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });
//    }
//
//    @Test
//    public void testRepeat() {
//        Observable.just("1")
//                .repeat(3)
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        System.out.println(s);
//                    }
//                });
//    }
//
//    @Test
//    public void testRepeatWhen() {
//        Observable.just("1")
//                .repeatWhen(new Func1<Observable, Observable<?>>() {
//                    @Override
//                    public Observable<?> call(Observable observable) {
//                        return observable;
//                    }
//                }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                System.out.println(s);
//            }
//        });
//    }
//
//    @Test
//    public void testCreate() {
//        Observable.create(new Observable.OnSubscribe<String>() {
//
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("hello");
//                subscriber.onNext("world");
//                subscriber.onNext("!");
//                subscriber.onCompleted();
//            }
//        }).subscribe(new Subscriber<String>() {
//            @Override
//            public void onNext(String s) {
//                System.out.println(s);
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("ok");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//        });
//    }
//
//
//    @Test
//    public void testRange() {
//        Observable.range(5, 30)
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.println(integer);
//                    }
//                });
//
//
//    }
//
//    @Test
//    public void testTimer() {
//
//        Observable.timer(2000, TimeUnit.SECONDS)
//                .subscribe(new Action1<Long>() {
//                    @Override
//                    public void call(Long aLong) {
//                        System.out.println(aLong + "");
//                    }
//                });
//    }
//
//    @Test
//    public void testMap() {
//        Observable.just("1", "2")
//                .map(new Func1<String, Integer>() {
//
//                    @Override
//                    public Integer call(String s) {
//                        return Integer.parseInt(s);
//                    }
//                }).subscribe(new Action1<Integer>() {
//            @Override
//            public void call(Integer integer) {
//                System.out.println(integer);
//            }
//        });
//    }
//
//    @Test
//    public void testData() {
//
//        Observable.from(new RxData().getStudent())
//                .flatMap(new Func1<Student, Observable<Subject>>() {
//                    @Override
//                    public Observable<Subject> call(Student student) {
//                        return Observable.from(student.getSubjects());
//                    }
//                })
//                .subscribe(new Action1<Subject>() {
//                    @Override
//                    public void call(Subject subject) {
//                        System.out.println(subject.getName());
//                    }
//                });
//
//    }
//
//    @Test
//    public void testFilter() {
//        Observable.just(1, 2, 3, 4, 5)
//                .filter(new Func1<Integer, Boolean>() {
//                    @Override
//                    public Boolean call(Integer integer) {
//                        return integer > 3;
//                    }
//                }).subscribe(new Action1<Integer>() {
//            @Override
//            public void call(Integer integer) {
//
//                System.out.println(integer);
//
//            }
//        });
//    }
//
//    @Test
//    public void testOfType() {
//        Observable.just(Person.class, RxData.class, Student.class, Subject.class)
//                .ofType(Subject.class)
//                .subscribe(new Action1<Subject>() {
//                    @Override
//                    public void call(Subject subject) {
//                        System.out.println(subject);
//                    }
//                });
//    }
//
//    @Test
//    public void testFirst() {
//        Observable.just(1, 2, 3, 4, 5)
//                .firstOrDefault(10, new Func1<Integer, Boolean>() {
//                    @Override
//                    public Boolean call(Integer integer) {
//                        return integer > 8;
//                    }
//                }).subscribe(new Action1<Integer>() {
//            @Override
//            public void call(Integer integer) {
//                System.out.println(integer);
//            }
//        });
//    }
//
//    @Test
//    public void testIgNore() {
//
//        Observable.just(1)
//                .ignoreElements()
//                .subscribe(new Subscriber<Object>() {
//                    @Override
//                    public void onNext(Object o) {
//                        System.out.println("onNext");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        System.out.println("onerror");
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        System.out.println("onComlete");
//                    }
//                });
//    }
//
//
//    @Test
//    public void testSkip() {
//
//
//        Observable.from(data)
//                .skip(3)
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.print(integer);
//                    }
//                });
//        System.out.println();
//        Observable.from(data)
//                .take(3)
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.print(integer);
//                    }
//                });
//        System.out.println();
//        Observable.from(data)
//                .skipLast(3)
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.print(integer);
//                    }
//                });
//    }
//
//    @Test
//    public void testAnd() {
//
//        Observable.combineLatest(Observable.just("1", "2"),
//                Observable.just("a", "b"), new Func2<String, String, String>() {
//                    @Override
//                    public String call(String s, String s2) {
//                        return s + s2;
//                    }
//                }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                System.out.println(s);
//            }
//        });
//    }
//
//    @Test
//    public void testMerage() {
//        Observable.range(1, 6)
//                .mergeWith(Observable.from(data))
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.print(integer);
//                    }
//                });
//        System.out.println();
//        Observable.merge(Observable.just("1", "2"), Observable.just("q", "m"))
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//
//                        System.out.print(s);
//                    }
//                });
//
//    }
//
//    @Test
//    public void testStart() {
//        Observable.just(1, 2)
//                .startWith(33, 23)
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.print(integer);
//                    }
//                });
//    }
//
//    @Test
//    public void testZip() {
//        Observable.zip(Observable.just("1", "2", "3"), Observable.just("a", "b", "c", "d"),
//                new Func2<String, String, String>() {
//                    @Override
//                    public String call(String s, String s2) {
//                        return s + s2;
//                    }
//                })
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onCompleted() {
//                        System.out.println("comp");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                        System.out.println(e);
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        System.out.println(s);
//                    }
//                });
//
//    }
//
//    @Test
//    public void testDelay() {
//
//
//        Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("aa");
//            }
//        }).subscribeOn(Schedulers.newThread()).delay(5, TimeUnit.SECONDS)
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        System.out.println(s);
//                    }
//                });
//
//    }
//
//    @Test
//    public void testDo() {
//        Observable.just("1", "2")
//                .doOnNext(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        System.out.println(s + "pp");
//                    }
//                }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                System.out.println(s);
//            }
//        });
//    }
//
//    @Test
//    public void testTime() {
//        Observable.just(1, 2, 3)
//                .all(new Func1<Integer, Boolean>() {
//                    @Override
//                    public Boolean call(Integer integer) {
//                        return integer > 0;
//                    }
//                }).subscribe(new Action1<Boolean>() {
//            @Override
//            public void call(Boolean aBoolean) {
//                System.out.println(aBoolean);
//            }
//        });
//
//
//    }
//
//    @Test
//    public void testCall() {
//
//
//        Observer<String> observer = new Observer<String>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//
//            }
//        };
//
//    }
//
//    @Test
//    public void testDelayN() {
//        Observable.create(new Observable.OnSubscribe<Integer>() {
//            @Override
//            public void call(Subscriber<? super Integer> subscriber) {
//                System.out.println("call: " + new SimpleDateFormat("yyyy/MM/dd HH:MM:ss").format(new Date()));
//                subscriber.onNext(1);
//                subscriber.onNext(2);
//                subscriber.onNext(3);
//                subscriber.onNext(4);
//                subscriber.onCompleted();
//            }
//        }).delay(2, TimeUnit.SECONDS)
//                .subscribe(new Subscriber<Integer>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.e(TAG, "onCompleted: " + new SimpleDateFormat("yyyy/MM/dd HH:MM:ss").format(new Date()));
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        System.out.println("onError: " + new SimpleDateFormat("yyyy/MM/dd HH:MM:ss").format(new Date()) + e.toString());
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        System.out.println("onNext: " + new SimpleDateFormat("yyyy/MM/dd HH:MM:ss").format(new Date()) + integer);
//                    }
//                });
//
//    }
//
//    @Test
//    public void test128() {
//
//
//        Integer a = 128, b = 128, c = 127, d = 127;
//        if (a == b) {
//            System.out.println("hello");
//
//        }
//        if (c == d) {
//            System.out.println("word");
//
//        }
//
//    }
//
//    @Test
//    public void testGroupBy() {
//
//        Observable.range(10, 10).groupBy(new Func1<Integer, Integer>() {
//            @Override
//            public Integer call(Integer integer) {
//                return integer % 3;
//            }
//        }).subscribe(new Action1<GroupedObservable<Integer, Integer>>() {
//            @Override
//            public void call(final GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {
//                integerIntegerGroupedObservable.subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.println(integerIntegerGroupedObservable.getKey() + "==" + integer);
//
//                    }
//                });
//            }
//        });
//
//        Observable.range(10, 10).groupBy(new Func1<Integer, Boolean>() {
//            @Override
//            public Boolean call(Integer integer) {
//                return integer > 16;
//            }
//        }).subscribe(new Action1<GroupedObservable<Boolean, Integer>>() {
//            @Override
//            public void call(final GroupedObservable<Boolean, Integer> booleanIntegerGroupedObservable) {
//
//                booleanIntegerGroupedObservable.subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//
//                        System.out.println(booleanIntegerGroupedObservable.getKey() + "==" + integer);
//                    }
//                });
//            }
//        });
//        System.out.println("******************************************");
//        Observable.from(new RxData().getStudent())
//                .groupBy(new Func1<Student, Integer>() {
//                    @Override
//                    public Integer call(Student student) {
//                        return student.getType();
//                    }
//                }).subscribe(new Action1<GroupedObservable<Integer, Student>>() {
//            @Override
//            public void call(final GroupedObservable<Integer, Student> integerStudentGroupedObservable) {
//
//                integerStudentGroupedObservable.subscribe(new Action1<Student>() {
//                    @Override
//                    public void call(Student student) {
//                        System.out.println(integerStudentGroupedObservable.getKey() + "=" + student.getName());
//                    }
//                });
//
//            }
//        });
//    }
//
//
//    @Test
//    public void testInterval() {
//        Observable.interval(1, 2, TimeUnit.SECONDS, Schedulers.io()).subscribe(new Action1<Long>() {
//            @Override
//            public void call(Long aLong) {
//                System.out.println("$$$$$$");
//                System.out.println(String.valueOf(aLong));
//            }
//        });
//    }
//
//    @Test
//    public void testScan() {
//        Observable.range(0, 10)
//                .doOnNext(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.print(integer);
//                    }
//                })
//                .scan(new Func2<Integer, Integer, Integer>() {
//                    @Override
//                    public Integer call(Integer integer, Integer integer2) {
//
//                        return integer + integer2;
//                    }
//                }).subscribe(new Action1<Integer>() {
//            @Override
//            public void call(Integer integer) {
//                System.out.println(integer);
//            }
//        });
//
//    }
//
//    @Test
//    public void testBuffer() {
//        Observable.range(0, 10)
//                .buffer(3)
//                .subscribe(new Action1<List<Integer>>() {
//                    @Override
//                    public void call(List<Integer> integers) {
//
//                        System.out.println(integers);
//                    }
//                });
//
//
//    }
//
//    @Test
//    public void testTakeLast() {
//
//        Observable.range(0, 10)
//                .takeLast(3)
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.println(integer);
//                    }
//                });
//
//    }
//
//    @Test
//    public void testJoin() {
//        Observable<String> left =
//                Observable.interval(100, TimeUnit.MILLISECONDS)
//                        .map(new Func1<Long, String>() {
//                            @Override
//                            public String call(Long aLong) {
//                                return "L" + aLong;
//                            }
//                        });
//        Observable<String> right =
//                Observable.interval(200, TimeUnit.MILLISECONDS)
//                        .map(new Func1<Long, String>() {
//                            @Override
//                            public String call(Long aLong) {
//                                return "R" + aLong;
//                            }
//                        });
//        left.join(right, new Func1<String, Observable<Integer>>() {
//                    @Override
//                    public Observable<Integer> call(String s) {
//                        Log.i(TAG, s + "==");
//                        return Observable.never();
//                    }
//                },
//                new Func1<String, Observable<Long>>() {
//                    @Override
//                    public Observable<Long> call(String s) {
//                        Log.i(TAG, s + "=====");
//                        return Observable.timer(0, TimeUnit.MILLISECONDS);
//                    }
//                }
//                , new Func2<String, String, String>() {
//                    @Override
//                    public String call(String s, String s2) {
//                        return s + "-" + s2;
//                    }
//                })
//                .take(10)
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.i(TAG, s);
//                    }
//                });
//
//    }
//
//    @Test
//    public void testRetry() {
//
//        Observable<String> observable = Observable.range(10, 3).map(new Func1<Integer, String>() {
//            @Override
//            public String call(Integer integer) {
//                if (integer == 11) {
//                    throw new IllegalArgumentException("exception");
//                }
//                return integer.toString() + "retry";
//            }
//        });
//        observable.retry(2).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                System.out.println(s);
//            }
//        }, new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                System.out.println(throwable.getMessage());
//            }
//        });
//
//
//    }
//
//
//    @Test
//    public void testDemo() {
//
//        Observable<String> switcher = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                System.out.println("call==" + "open1");
//                subscriber.onNext("open1");
//                System.out.println("call==" + "close");
//                subscriber.onNext("close");
//                System.out.println("call==" + "open2");
//                subscriber.onNext("open2");
//                subscriber.onCompleted();
//                subscriber.onNext("over");
//
//
//            }
//        });
//
//        Observer<String> light = new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                System.out.println("onCompleted");
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//                System.out.println(e);
//
//            }
//
//            @Override
//            public void onNext(String s) {
//
//                System.out.println(s);
//                if (s.startsWith("open"))
//                    System.out.println("灯开");
//            }
//        };
//
//        switcher.subscribe(light);
//
//    }
//
//    @Test
//    public void testD2() {
//
//        Observable.just("1", "2", "3")
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        System.out.println(s);
//                    }
//                });
//
//        Observable.just("2").subscribe(new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//
//            }
//        });
//
//
//    }
//
//
//    @Test
//    public void testFrom2() {
//        Observable.from(data)
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.println(integer);
//                    }
//                });
//
//
//    }
//
//    @Test
//    public void testMap2() {
//
//        Observable.from(data)
//                .takeFirst(new Func1<Integer, Boolean>() {
//                    @Override
//                    public Boolean call(Integer integer) {
//                        return integer > 3;
//                    }
//                })
//                .map(new Func1<Integer, String>() {
//                    @Override
//                    public String call(Integer integer) {
//                        System.out.println(integer);
//                        return integer + 10 + "";
//                    }
//                }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                System.out.println(s);
//            }
//        });
//
//    }
//
//
//    @Test
//    public void testFlatMap2() {
//        Observable.from(new RxData().getStudent())
//                .flatMap(new Func1<Student, Observable<Subject>>() {
//                    @Override
//                    public Observable<Subject> call(Student student) {
//                        System.out.print(student.getName() + "*************");
//                        System.out.println();
//                        return Observable.from(student.getSubjects());
//                    }
//                }).subscribe(new Action1<Subject>() {
//            @Override
//            public void call(Subject subject) {
//                System.out.println(subject.getName());
//            }
//        });
//
//    }
//
//
//    @Test
//    public void testSch() {
//        Observable.just("21")
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        System.out.println(Thread.currentThread().getName() + "onSubcribe");
//                    }
//                })
//                .doOnNext(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        System.out.println(Thread.currentThread().getName() + "onNext");
//                    }
//                })
//                .doOnCompleted(new Action0() {
//                    @Override
//                    public void call() {
//                        System.out.println(Thread.currentThread().getName() + "com");
//                    }
//                })
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.io())
//                .map(new Func1<String, String>() {
//                    @Override
//                    public String call(String s) {
//
//                        return s;
//                    }
//                }).observeOn(Schedulers.computation())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        System.out.println(s);
//                    }
//                });
//
//    }
//
//    @Test
//    public void testZip2() {
//        Observable.zip(Observable.range(10, 5), Observable.just("a", "b", "c"), new Func2<Integer, String, String>() {
//            @Override
//            public String call(Integer integer, String s) {
//                return integer + s;
//            }
//        }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                System.out.println(s);
//            }
//        });
//
//    }
//
//    @Test
//    public void testAsyncObject() {
//
//        AsyncSubject<Integer> asyncSubject = AsyncSubject.create();
//        asyncSubject.onNext(1);
//        asyncSubject.onNext(2);
//        asyncSubject.onNext(3);
//        asyncSubject.onCompleted();
//        asyncSubject.subscribe(new Action1<Integer>() {
//            @Override
//            public void call(Integer integer) {
//                System.out.println(integer);
//            }
//        });
//
//    }
//
//    @Test
//    public void testBehavior() {
//
//        BehaviorSubject behaviorSubject = BehaviorSubject.create(-1);
//        behaviorSubject.onNext(1);
//        behaviorSubject.onNext(2);
//        behaviorSubject.onNext(3);
//        behaviorSubject.onNext(4);
//        behaviorSubject.onNext(5);
//        behaviorSubject.subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onCompleted() {
//                System.out.println("com");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//                System.out.println(e);
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//
//                System.out.println(integer);
//            }
//        });
//
//
//        behaviorSubject.onNext(6);
//        behaviorSubject.onNext(7);
//        behaviorSubject.onCompleted();
//
//
//    }
//
//    @Test
//    public void testPublish() {
//
//        PublishSubject bs = PublishSubject.create();
//// 这里订阅接收1， 2， 3
//        bs.onNext(1);
//// 这里订阅接收2， 3
//        bs.onNext(2);
//// 这里订阅接收3
//        bs.onNext(3);
//
//// 这里订阅无接收
//        bs.subscribe(
//                new Action1<Integer>() {
//                    @Override
//                    public void call(Integer o) {
//                        System.out.println(o);
//                    }
//                });
//        bs.onNext(4);
//        bs.onNext(5);
//        bs.onCompleted();
//
//    }
//
//    @Test
//    public void testReplay() {
//
//        ReplaySubject<Integer> re = ReplaySubject.create();
//        re.onNext(1);
//        re.onNext(2);
//        re.onNext(3);
//        re.subscribe(new Action1<Integer>() {
//            @Override
//            public void call(Integer integer) {
//                System.out.println(integer);
//            }
//        });
//        re.onNext(4);
//        re.onNext(5);
//        re.onCompleted();
//
//    }
//
//
//    @Test
//    public void testSubObserver() {
//
//        PublishSubject<Integer> p = PublishSubject.create();
//
//
//        Subscription subscribe = Observable.just(1)
//                .subscribe(p);
//    }
//
//
//    @Test
//    public void testGroupData() {
//        DataFactory d = new DataFactory();
//        d.createGroup(3)
//                .flatMap(new Func1<List<Group>, Observable<Group>>() {
//                    @Override
//                    public Observable<Group> call(List<Group> groups) {
//                        return Observable.from(groups);
//                    }
//                }).flatMap(new Func1<Group, Observable<Member>>() {
//            @Override
//            public Observable<Member> call(Group group) {
//                System.out.println(group.getName() + ":" + group.getType());
//                return Observable.from(group.getDatas());
//            }
//        }).subscribe(new Action1<Member>() {
//            @Override
//            public void call(Member member) {
//                System.out.println(member.getName() + ":" + member.getType());
//            }
//        });
//
//
//    }
//
//
//    @Test
//    public void testObserver1() {
//
//        Observable<Integer> observerable = Observable.create(new Observable.OnSubscribe<Integer>() {
//            @Override
//            public void call(Subscriber<? super Integer> subscriber) {
//                subscriber.onNext(0);
//                subscriber.onNext(1);
//                subscriber.onNext(2);
//                subscriber.onCompleted();
//            }
//        });
//
//        Observer<Integer> observer = new Observer<Integer>() {
//            @Override
//            public void onCompleted() {
//
//                System.out.println("complete");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                System.out.println(integer);
//
//            }
//        };
//
//        observerable.subscribe(observer);
//
//
//    }
//
//    @Test
//    public void testRange1() {
//
//        Observable.range(0, 3)
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.println(integer);
//                    }
//                });
//    }
//}
