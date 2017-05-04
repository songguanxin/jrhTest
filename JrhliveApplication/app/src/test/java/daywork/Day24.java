package daywork;

import org.junit.Test;

/**
 * desc:一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。
 * 求该青蛙跳上一个n级的台阶总共有多少种跳法

 * Created by jiarh on 17/3/24 10:30.
 */

public class Day24 {


    public int testJump(int num){

        int count=0;
        if (num<=0)
            return count;
        else if (num==1)
            return 1;
        else if (num==2)
            return 2;
        else{
            for (int i = 1; i <num ; i++) {
                count+=testJump(num-i);
            }
            count++;
        }

        return count;

    }


    @Test
    public void testres(){
        System.out.println(testJump(1));
        System.out.println(testJump(2));
        System.out.println(testJump(3));
        System.out.println(testJump(4));
        System.out.println(testJump(5));

    }
}
