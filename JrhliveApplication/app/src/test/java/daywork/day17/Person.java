package daywork.day17;

public class Person {
    private String name;
    private int age;
    private String sex;

    public Person() {

    }

    private Person(String name) {
        this.name = name;
    }

    public Person(int age) {
        this.age = age;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "[name = " + this.name + "  age=" + this.age + "]";
    }

    private void sayHello() {
        System.out.println("hello world...");
    }

    public void sayNameAge(String name, int age) {
        System.out.println("name =" + name + " age=" + age);
    }

}
