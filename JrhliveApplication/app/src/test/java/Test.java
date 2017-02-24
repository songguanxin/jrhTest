/**
 * desc:
 * Created by jiarh on 17/2/23 10:53.
 */

public class Test {

    @org.junit.Test
    public  void test1(){
        int a = 2;
        int b=3;
         b=a+(a=b)*0;
        System.out.println(a+"**"+b);
    }
}
