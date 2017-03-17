package daywork.day14;

/**
 * desc:
 * Created by jiarh on 17/3/10 15:27.
 */

public  class StudentA extends Student {


    public StudentA(String name, String doing) {
        super(name, doing);
    }

    @Override
    public void work() {
        System.out.println(name+"正在"+doing);
    }

    @Override
    public void Stopwork() {
        System.out.println(name+"停止"+doing);
    }

}
