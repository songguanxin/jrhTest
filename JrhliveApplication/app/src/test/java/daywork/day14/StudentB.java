package daywork.day14;

/**
 * desc:
 * Created by jiarh on 17/3/10 15:30.
 */

public class StudentB extends Student {


    public StudentB(String name, String doing) {
        super(name, doing);
    }

    @Override
    public void work() {
        System.out.println(name + "正在" + doing);
    }

    @Override
    public void Stopwork() {

        System.out.println("老师来了,"+name+"要停止"+doing);


    }


}
