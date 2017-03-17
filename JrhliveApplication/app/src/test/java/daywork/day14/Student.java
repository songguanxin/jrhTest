package daywork.day14;

/**
 * desc:
 * Created by jiarh on 17/3/10 15:26.
 */

public abstract class Student {
    String name="";
    String doing="";


    public Student(String name,String doing) {
        this.name = name;
        this.doing = doing;
    }

    public abstract void work();
    public abstract void Stopwork();
}
