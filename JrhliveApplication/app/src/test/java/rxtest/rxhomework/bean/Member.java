package rxtest.rxhomework.bean;

/**
 * desc:
 * Created by jiarh on 16/12/27 13:43.
 */

public class Member implements Comparable{



    private String name;
    /**
     *  0 群主；1 会员；2 普通；
     */
    private int type;

    private int age;


    public Member(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public Member(int age, String name, int type) {
        this.age = age;
        this.name = name;
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int compareTo(Object another) {
        return age-((Member)another).getAge();
    }
}
