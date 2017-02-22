package rxtest.rxhomework;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import rxtest.rxhomework.bean.Group;
import rxtest.rxhomework.bean.Member;

/**
 * desc:
 * Created by jiarh on 16/12/27 13:49.
 */

public class DataFactory {

    /**
     * 生成群
     *
     * @param num
     * @return
     */
    public List<Group> createGroup(int num) {
       final List<Group> groups = new ArrayList<>();
        Observable.range(0,num)
                .flatMap(new Function<Integer, Observable<Group>>() {
                    @Override
                    public Observable<Group> apply(Integer integer) throws Exception {
                        return createMembers()
                                .map(new Function<List<Member>, Group>() {
                                    @Override
                                    public Group apply(List<Member> members) throws Exception {
                                        int i= new Random().nextInt(2);
                                        String name = i==0?"主群":"子群";
                                        return new Group(members, name + createName(), i);
                                    }
                                });
                    }
                }).subscribe(new Consumer<Group>() {
            @Override
            public void accept(Group group) throws Exception {
                groups.add(group);
            }
        });
        return groups;
    }

    /**
     * 生成所有成员
     *
     * @return
     */
    private Observable<List<Member>> createMembers() {
       final List<Member> gmembers = new ArrayList<>();

        Observable.merge((createMember(0, 5, 5)), createMember(1, 10, 20),
                createMember(2, 0, 100)).subscribe(new Consumer<List<Member>>() {
            @Override
            public void accept(List<Member> members) throws Exception {
                gmembers.addAll(members);
            }
        });
        return Observable.just(gmembers);
    }

    /**
     * 生成指定数量，指定类型的成员
     *
     * @param min       最小数
     * @param type      成员类型
     * @param randomNum 成员数
     * @return
     */
    public Observable<List<Member>> createMember(final int type, int min, int randomNum) {

        final List<Member> members = new ArrayList<>();
        Observable.range(0, min + new Random().nextInt(randomNum))
                .flatMap(new Function<Integer, Observable<Member>>() {
                    @Override
                    public Observable<Member> apply(Integer integer) throws Exception {
                        return Observable.just(createName())
                                .map(new Function<String, Member>() {
                                    @Override
                                    public Member apply(String s) throws Exception {
                                        return new Member(s, type);
                                    }
                                });
                    }
                }).subscribe(new Consumer<Member>() {
            @Override
            public void accept(Member member) throws Exception {
                members.add(member);
            }
        });
        return Observable.just(members);
    }


    /**
     * 随机生成姓名
     */
    StringBuffer ms = new StringBuffer();

    public String createName() {
        ms.setLength(0);
        Observable.just(0)
                .repeat(6)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return String.valueOf((char) ((int) (Math.random() * 26 + 97)));
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                ms.append(s);
            }
        });

        return ms.toString();
    }
}
