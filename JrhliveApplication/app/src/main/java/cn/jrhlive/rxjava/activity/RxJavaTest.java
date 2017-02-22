package cn.jrhlive.rxjava.activity;

/**
 * desc:
 * Created by jiarh on 16/10/31 16:01.
 */

public class RxJavaTest {
//    Integer intData[] ={1,2,3,4,5,6};
//    List<String> data = new ArrayList<>();
//
//    public List<String> useJust() {
//        Observable.just(1, 2, 3)
//                .subscribe(new Subscriber<Integer>() {
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
//                    public void onNext(Integer integer) {
//                        data.add(integer+"");
//                    }
//                });
//        return data;
//    }
//
//    public List<String> useRange(){
//        Observable.range(20,5)
//                .subscribe(new Subscriber<Integer>() {
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
//                    public void onNext(Integer integer) {
//
//                        data.add(integer.toString());
//                    }
//                });
//        return data;
//    }
//
//    public List<String> useRepeat(){
//
//        Observable.just(9).repeat(5).subscribe(new Subscriber<Integer>() {
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
//            public void onNext(Integer integer) {
//                data.add(integer.toString());
//            }
//        });
//
//        return data;
//    }
//
//    /**
//     *
//     * @return
//     */
//    public List<String> useRepeatWhen(){
//
//      // TODO: 16/11/1 不太明白
//
//        Observable.just(9).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
//            @Override
//            public Observable<?> call(Observable<? extends Void> observable) {
//                return null;
//            }
//        }).subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                data.add(integer.toString());
//            }
//        });
//
//        return data;
//    }
//
//    public List<String> useDowhile (){
//
//        Observable.from(intData).subscribe(new Subscriber<Integer>() {
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
//            public void onNext(Integer integer) {
//
//            }
//        });
//        return data;
//    }
}
