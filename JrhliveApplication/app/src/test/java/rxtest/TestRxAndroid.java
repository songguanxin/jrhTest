package rxtest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

/**
 * 1：创建n个群，可区分主群，子群，每个群下面有群主(随机取5-10)，会员(随机取10-30)，普通成员(随机取0-100)；
 *  存入集合A（注：(以上/以下)用到的属性自己定义/添加，如：name,type...）
 * 2:输出A中所有的主群name;
 * 3:给A中所有的主群添加img="parent.img",所有子群添加img="child.img";
 * 4:取A中前2个群，并列出群name+成员name;
 * 5:取出A中群成员>50的群，在io线程中执行，主线程中输出群name;
 * 6:取出A中第1个子群，并将所有普通成员升级为会员；
 * 7:取出A中一个群，对成员的type分组输出；如：群主：... ;会员...;...
 * 8:取出A中第1个主群，并将其排序拼接在名字前面；如 有3个成员 A,B,C;输出1A,2B,3C;
 * 9:合并A中最后3个群的群成员到集合B;延迟3秒，每2秒输出B中的会员,每输出一次打印一次“hello dear,”,输出结束后打印“we are great”；
 * 10:取出A最后一个主群,输出其成员中年龄最大的成员，最小成员，所有成员的年龄总和，年龄平均值；
 * Created by RenTao on 17/1/2.
 */
public class TestRxAndroid {
    private List<Group> data = new ArrayList<>();

    /**
     * 创建n个群，可区分主群，子群，每个群下面有群主(随机取5-10)，会员(随机取10-30)，普通成员(随机取0-100)；
     *  存入集合A（注：(以上/以下)用到的属性自己定义/添加，如：name,type...）
     */
//    @Test
    @Before
    public void createData() throws Exception {
        Observable.range(0, 10).flatMap(new Function<Integer, ObservableSource<Group>>() {
            @Override
            public ObservableSource<Group> apply(Integer integer) throws Exception {
                Group group = new Group();
                group.id = integer + 1;
                group.name = "group_" + group.id;
                group.pid = integer % 2 == 0 ? integer : 0;
                data.add(group);
                return Observable.just(group);
            }
        }).subscribe(new Consumer<Group>() {

            @Override
            public void accept(Group group) throws Exception {
                group.members = new ArrayList<>();
                // 群主
                group.members.addAll(createMemberList(group, 1, 5 + (int) (Math.random() * 5)));
                // 会员
                group.members.addAll(createMemberList(group, 2, 10 + (int) (Math.random() * 20)));
                // 成员
                group.members.addAll(createMemberList(group, 3, (int) (Math.random() * 100)));
            }
        });
        for (int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i));
        }
    }

    private List<Member> createMemberList(final Group group, final int memberType, final int size) {
        final List<Member> list = new ArrayList<>(size);
        Observable.range(0, size).subscribe(new Consumer<Integer>() {

            @Override
            public void accept(Integer i) throws Exception {
                list.add(createMember(group, memberType, i));
            }
        });
        return list;
    }

    private Member createMember(Group group, int memberType, int i) {
        Member member = new Member();
        member.id = i + 1;
        member.name = group.id + "_member_" + member.id;
        member.type = memberType;
        member.age = (int) (100 * Math.random());
        return member;
    }

    /**
     * 输出A中所有的主群name
     */
    @Test
    public void question2() throws Exception {
        System.out.println("answer------------------->");
        Observable.fromIterable(data).filter(new Predicate<Group>() {
            @Override
            public boolean test(Group group) throws Exception {
                return group.pid == 0;
            }
        }).subscribe(new Consumer<Group>() {
            @Override
            public void accept(Group group) throws Exception {
                System.out.println(group);
            }
        });
    }

    /**
     * 给A中所有的主群添加img="parent.img",所有子群添加img="child.img"
     */
    @Test
    public void question3() throws Exception {
        System.out.println("answer------------------->");
        Observable.fromIterable(data).map(new Function<Group, Group>() {

            @Override
            public Group apply(Group group) throws Exception {
                group.img = group.pid == 0 ? "parent.img" : "child.img";
                return group;
            }
        }).subscribe(new Consumer<Group>() {
            @Override
            public void accept(Group group) throws Exception {
                System.out.println(group);
            }
        });
    }

    /**
     * 取A中前2个群，并列出群name+成员name
     */
    @Test
    public void question4() throws Exception {
        System.out.println("answer------------------->");
        Observable.range(0, 2).flatMap(new Function<Integer, ObservableSource<Member>>() {

            @Override
            public ObservableSource<Member> apply(Integer integer) throws Exception {
                Group group = data.get(integer);
                System.out.println("Group " + integer + ", name=" + group.name);
                return Observable.fromIterable(group.members);
            }
        }).forEach(new Consumer<Member>() {
            @Override
            public void accept(Member member) throws Exception {
                System.out.println("Member " + member.name);
            }
        });
    }

    /**
     * 取出A中群成员>50的群，在io线程中执行，主线程中输出群name
     */
    @Test
    public void question5() throws Exception {
        System.out.println("answer------------------->");
        Observable.fromIterable(data).subscribeOn(Schedulers.io()).filter(new Predicate<Group>() {
            @Override
            public boolean test(Group group) throws Exception {
                System.out.println("filter " + Thread.currentThread());
                return group.members.size() > 50;
            }
        }).map(new Function<Group, String>() {
            @Override
            public String apply(Group group) throws Exception {
                System.out.println("map " + Thread.currentThread());
                return group.name;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s + " " + Thread.currentThread());
            }
        });
    }

    /**
     * 取出A中第1个子群，并将所有普通成员升级为会员
     */
    @Test
    public void question6() throws Exception {
        System.out.println("answer------------------->");
        Observable.fromIterable(data).take(1).flatMap(new Function<Group, ObservableSource<Member>>() {
            @Override
            public ObservableSource<Member> apply(Group group) throws Exception {
                System.out.println(group);
                return Observable.fromIterable(group.members).filter(new Predicate<Member>() {
                    @Override
                    public boolean test(Member member) throws Exception {
                        return member.type == 1;
                    }
                });
            }
        }).subscribe(new Consumer<Member>() {
            @Override
            public void accept(Member member) throws Exception {
                System.out.println("before----------------");
                System.out.println(member);
                member.type = 2;
                System.out.println("after----------------");
                System.out.println(member);
            }
        });
    }

    /**
     * 取出A中一个群，对成员的type分组输出；如：群主：... ;会员...;...
     */
    @Test
    public void question7() throws Exception {
        System.out.println("answer------------------->");
        Observable.fromIterable(data).take(1).flatMap(new Function<Group, ObservableSource<GroupedObservable<String, Member>>>() {
            @Override
            public ObservableSource<GroupedObservable<String, Member>> apply(Group group) throws Exception {
                return Observable.fromIterable(group.members).groupBy(new Function<Member, String>() {
                    @Override
                    public String apply(Member member) throws Exception {
                        return member.getType();
                    }
                });
            }
        }).subscribe(new Consumer<GroupedObservable<String, Member>>() {
            @Override
            public void accept(final GroupedObservable<String, Member> observable) throws Exception {
                observable.subscribe(new Consumer<Member>() {
                    @Override
                    public void accept(Member member) throws Exception {
                        System.out.println(observable.getKey() + ": " + member);
                    }
                });
            }
        });
    }

    /**
     * 取出A中第1个主群，并将其排序拼接在名字前面；如 有3个成员 A,B,C;输出1A,2B,3C
     */
    @Test
    public void question8() throws Exception {
        System.out.println("answer------------------->");
        Observable<List<Member>> observable = Observable.fromIterable(data)
                .filter(new Predicate<Group>() {
                    @Override
                    public boolean test(Group group) throws Exception {
                        return group.pid == 0;
                    }
                })
                .take(1)
                .flatMap(new Function<Group, ObservableSource<List<Member>>>() {
                    @Override
                    public ObservableSource<List<Member>> apply(Group group) throws Exception {
                        return Observable.just(group.members);
                    }
                });
        Observable.zip(observable.flatMap(new Function<List<Member>, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(List<Member> list) throws Exception {
                        return Observable.range(0, list.size());
                    }
                }),
                observable.flatMap(new Function<List<Member>, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(List<Member> list) throws Exception {
                        return Observable.fromIterable(list).map(new Function<Member, String>() {
                            @Override
                            public String apply(Member member) throws Exception {
                                return member.name;
                            }
                        });
                    }
                }),
                new BiFunction<Integer, String, String>() {
                    @Override
                    public String apply(Integer integer, String s) throws Exception {
                        return integer + s;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });
    }

    /**
     * 合并A中最后3个群的群成员到集合B;延迟3秒，每2秒输出B中的会员,每输出一次打印一次“hello dear,”,输出结束后打印“we are great”
     */
    @Test
    public void question9() throws Exception {
        System.out.println("answer------------------->");
        Observable.zip(Observable.interval(3, 2, TimeUnit.SECONDS),
                Observable.fromIterable(data).takeLast(3)
                        .flatMap(new Function<Group, ObservableSource<Member>>() {
                            @Override
                            public ObservableSource<Member> apply(Group group) throws Exception {
                                return Observable.fromIterable(group.members);
                            }
                        }),
                new BiFunction<Long, Member, String>() {
                    @Override
                    public String apply(Long aLong, Member member) throws Exception {
                        return "hello dear," + member.name + ". we are great";
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }

    /**
     * 取出A最后一个主群,输出其成员中年龄最大的成员，最小成员，所有成员的年龄总和，年龄平均值
     */
    @Test
    public void question10() throws Exception {
        System.out.println("answer------------------->");
        Observable.fromIterable(data).takeLast(1).flatMap(new Function<Group, ObservableSource<List<Member>>>() {
            @Override
            public ObservableSource<List<Member>> apply(Group group) throws Exception {
                System.out.println("take last: " + group);
                return Observable.just(group.members);
            }
        }).subscribe(new Consumer<List<Member>>() {
            @Override
            public void accept(List<Member> members) throws Exception {
                Observable.fromIterable(members).toSortedList(new Comparator<Member>() {
                    @Override
                    public int compare(Member lhs, Member rhs) {
                        if (lhs.age == rhs.age) {
                            return 0;
                        } else if (lhs.age > rhs.age) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }).subscribe(new Consumer<List<Member>>() {
                    @Override
                    public void accept(List<Member> members) throws Exception {
                        Member min = members.get(0);
                        Member max = members.get(members.size() - 1);
                        int total = 0;
                        for (int i = 0; i < members.size(); i++) {
                            total += members.get(i).age;
                        }
                        int avg = total / members.size();
                        System.out.println("min age is " + min.name);
                        System.out.println("max age is " + max.name);
                        System.out.println("total age is " + total);
                        System.out.println("avg age is " + avg);
                    }
                });
            }
        });
    }

    private class Group {
        int id;
        int pid;
        String name;
        String img;
        List<Member> members;

        @Override
        public String toString() {
            return "Group{" +
                    "id=" + id +
                    ", pid=" + pid +
                    ", name='" + name + '\'' +
                    ", img='" + img + '\'' +
                    ", members=" + members.size() +
                    '}';
        }
    }

    private class Member {
        int id;
        String name;
        /**
         * 1.群主；2.会员；3.普通成员
         */
        int type;
        int age;

        String getType() {
            switch (type) {
                case 1:
                    return "群主";
                case 2:
                    return "会员";
                case 3:
                    return "普通成员";
                default:
                    return null;
            }
        }

        @Override
        public String toString() {
            return "Member{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", type=" + type +
                    ", age=" + age +
                    '}';
        }
    }
}