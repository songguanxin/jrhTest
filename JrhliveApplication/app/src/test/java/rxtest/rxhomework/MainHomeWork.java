package rxtest.rxhomework;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;
import rxtest.rxhomework.bean.Group;
import rxtest.rxhomework.bean.Member;

/**
 * desc:
 * Created by jiarh on 16/12/29 10:09.
 */

public class MainHomeWork {

    DataFactory dataFactory;

    List<Group> groups;

    @Before
    public void setUp() {
        if (dataFactory == null)
            dataFactory = new DataFactory();
        if (groups == null)
            groups = dataFactory.createGroup(3);
    }

    private Observable<Group> getGroups() {
        return Observable.fromIterable(groups);
    }

    /**
     * 数据源
     */
    @Test
    public void test1() {

        getGroups().flatMap(new Function<Group, Observable<Member>>() {
            @Override
            public Observable<Member> apply(Group group) throws Exception {
                System.out.println("**********************" + group.getName() + "&&type==" + group.getType() + "&&img=" + group.getImg());
                return Observable.fromIterable(group.getDatas());
            }
        }).subscribe(new Consumer<Member>() {
            @Override
            public void accept(Member member) throws Exception {
                System.out.println(member.getName() + "==" + member.getType());
            }
        });

    }


    /**
     * 检测 ：简单输出，基本 过滤 用法
     */
    @Test
    public void test2() {

        getGroups().filter(new Predicate<Group>() {
            @Override
            public boolean test(Group group) throws Exception {
                System.out.print("**********************" + group.getName() + "&&type==" + group.getType() + "&&img=" + group.getImg());
                System.out.println();
                return group.getType() == 0;
            }
        }).subscribe(new Consumer<Group>() {
            @Override
            public void accept(Group group) throws Exception {
                System.out.println(group.getName() + "&&type==" + group.getType() + "&&img=" + group.getImg());
            }
        });

    }

    /**
     * 检测 ：获取数据 并操作属性
     */
    @Test
    public void test3() {
        test1();
        getGroups().subscribe(new Consumer<Group>() {
            @Override
            public void accept(Group group) throws Exception {

                if (group.getType() == 0)
                    group.setImg("parent.img");
                else
                    group.setImg("child.img");
            }
        });
        test1();
    }


    /**
     * 检测take，取指定的数据
     */
    @Test
    public void test4() {

        getGroups().take(2)
                .flatMap(new Function<Group, Observable<Member>>() {
                    @Override
                    public Observable<Member> apply(Group group) throws Exception {
                        System.out.println(group.getName());
                        return Observable.fromIterable(group.getDatas());
                    }
                }).subscribe(new Consumer<Member>() {
            @Override
            public void accept(Member member) throws Exception {
                System.out.println(member.getName());
            }
        });
    }

    /**
     * 检测 线程调度
     */
    @Test
    public void test5() {

        getGroups().filter(new Predicate<Group>() {
            @Override
            public boolean test(Group group) throws Exception {
                System.out.println(group.getName() + "大小=" + group.getDatas().size() + "");
                return group.getDatas().size() > 50;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<Group>() {
                    @Override
                    public void accept(Group group) throws Exception {
                        System.out.println(group.getName());
                    }
                });
    }

    /**
     * 检测take ,过滤操作符
     */
    @Test
    public void test6() {
        test1();
        getGroups().filter(new Predicate<Group>() {
            @Override
            public boolean test(Group group) throws Exception {
                return group.getType() == 1;
            }
        }).take(1).flatMap(new Function<Group, Observable<Member>>() {
            @Override
            public Observable<Member> apply(Group group) throws Exception {
                return Observable.fromIterable(group.getDatas());
            }
        }).subscribe(new Consumer<Member>() {
            @Override
            public void accept(Member member) throws Exception {
                if (member.getType() == 2) member.setType(1);
            }
        });
        test1();

    }

    /**
     * 检测分组策略
     */
    @Test
    public void test7(){

        test1();
        getGroups().take(1)
                .flatMap(new Function<Group, Observable<Member>>() {
                    @Override
                    public Observable<Member> apply(Group group) throws Exception {
                        return Observable.fromIterable(group.getDatas());
                    }
                }).groupBy(new Function<Member, Integer>() {
            @Override
            public Integer apply(Member member) throws Exception {
                return member.getType();
            }
        }).subscribe(new Consumer<GroupedObservable<Integer, Member>>() {
            @Override
            public void accept(GroupedObservable<Integer, Member> integerMemberGroupedObservable) throws Exception {
                System.out.println(integerMemberGroupedObservable.getKey()+"==");
                integerMemberGroupedObservable.subscribe(new Consumer<Member>() {
                    @Override
                    public void accept(Member member) throws Exception {
                        System.out.println(member.getName());
                    }
                });
            }
        });

    }

    /**
     * 检测合并输出
     */
    @Test
    public void test8(){
        Observable<Group> groupObsevable = getGroups().filter(new Predicate<Group>() {
            @Override
            public boolean test(Group group) throws Exception {
                return group.getType()==0;
            }
        }).take(1);

        Observable.zip(groupObsevable.flatMap(new Function<Group, Observable<Integer>>() {
            @Override
            public Observable<Integer> apply(Group group) throws Exception {
                return Observable.range(0, group.getDatas().size());
            }
        }), groupObsevable.flatMap(new Function<Group, Observable<Member>>() {
            @Override
            public Observable<Member> apply(Group group) throws Exception {
                return Observable.fromIterable(group.getDatas());
            }
        }), new BiFunction<Integer, Member, String>() {
            @Override
            public String apply(Integer integer, Member member) throws Exception {
                return integer+member.getName();
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }

    /**
     * 主要检测 时间 策略
     * 需要在正式代码中运行，观察一下时间
     */
    @Test
    public void test9(){

        final List<Member> groupB = new ArrayList<>();
        getGroups().takeLast(3).flatMap(new Function<Group, Observable<Member>>() {
            @Override
            public Observable<Member> apply(Group group) throws Exception {
                return Observable.fromIterable(group.getDatas());
            }
        }).subscribe(new Consumer<Member>() {
            @Override
            public void accept(Member member) throws Exception {
                groupB.add(member);
            }
        });

        Observable
                .interval(3,2, TimeUnit.SECONDS)
                .fromIterable(groupB)
                .filter(new Predicate<Member>() {
                    @Override
                    public boolean test(Member member) throws Exception {
                        return member.getType()==1;
                    }
                })
                .doOnNext(new Consumer<Member>() {
                    @Override
                    public void accept(Member member) throws Exception {
                        System.out.println("hello dear,");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        System.out.println("we are great.");
                    }
                })
                .subscribe(new Observer<Member>() {
                    Disposable dispose = null;
                    @Override
                    public void onSubscribe(Disposable d) {
                        dispose=d;
                    }

                    @Override
                    public void onNext(Member member) {

                        System.out.println(member.getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                        dispose.dispose();
                    }
                });

    }

    /**
     * 主要是对算术操作符检测
     * 需要在gradle引入 rxjava-math 架包
     * 且其只能针对 rxjava 1.0 版本 ，对2.0 暂时不支持
     */
    @Test
    public void test10(){

//        MathObservable.max(rx.Observable.range(0,10)).subscribe(new Action1<Integer>() {
//            @Override
//            public void call(Integer integer) {
//                System.out.println("最大值="+integer);
//            }
//        });
//        MathObservable.min(rx.Observable.range(0,10)).subscribe(new Action1<Integer>() {
//            @Override
//            public void call(Integer integer) {
//                System.out.println("最小值="+integer);
//            }
//        });
//        MathObservable.sumInteger(rx.Observable.range(0,10)).subscribe(new Action1<Integer>() {
//            @Override
//            public void call(Integer integer) {
//                System.out.println("和="+integer);
//            }
//        });
//        MathObservable.averageInteger(rx.Observable.range(0,10)).subscribe(new Action1<Integer>() {
//            @Override
//            public void call(Integer integer) {
//                System.out.println("平均值="+integer);
//            }
//        });

    }

}
