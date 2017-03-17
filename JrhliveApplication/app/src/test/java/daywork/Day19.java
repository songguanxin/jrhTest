package daywork;

import org.junit.Test;

/**
 * desc:
 * Createdby jiarh on 17/3/17 09:26.
 */

public class Day19 {


    @Test
    public void testResult(){

        SingleTon singleTon = SingleTon.getInstance();
        System.out.println("count1="+SingleTon.count1);
        System.out.println("count2="+SingleTon.count2);
    }

    static class SingleTon{

        private static SingleTon singleTon = new SingleTon();
        public static int count1;
        public static int count2=0;
        private SingleTon(){
            count1++;
            count2++;
        }

        public static SingleTon getInstance(){
            return singleTon;
        }
    }
}
