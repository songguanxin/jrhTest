package daywork;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * desc:
 *
 * 位运算学习
 学习&按位与、|按位或、^按位异或、<<左移、>>右移、~取反运算符，求解以下题目，写出简单的运算过程
 1.9&5
 2.9|5
 3.9^5
 4.~9
 5.3<<4
 6.32>>3
 * Created by jiarh on 17/3/13 14:01.
 */

public class Day16 {

    /**
     * 9: 00001001
     * 5: 00000101
     * 3: 00000011
     * 4: 00000100
     * 32:00100000
     */

    /**
     * 9&5=1 都为1则为1
     */
    @Test
    public void test1(){
        /**
         * 1001
         * 0101
         * 0001
         * =1
         *
         */

        assertEquals(1,9&5);
    }

    /**
     * 9|5=13 有1则1
     */
    @Test
    public void test2(){
        /**
         * 1001
         * 0101
         * 1101
         * =13
         *
         */
        assertEquals(13,9|5);

    }

    /**
     * 9^5=12  相同为0，不同为1
     */
    @Test
    public void test3(){

        /**
         * 1001
         * 0101
         * 1100
         * =12
         *
         */
        assertEquals(12,9^5);
    }

    /**
     * ~9=-10  取非 补码+1，高位不操作，符合位
     */
    @Test
    public void test4(){

        /**
         * 00000000 00000000 00000000 00001001
         * 11111111 11111111 11111111 11110110
         * 10000000 00000000 00000000 00001001
         * +1
         * 10000000 00000000 00000000 00001010
         *
         * =-10
         */
        assertEquals(-10,~9);
    }


    /**
     * 3<<4=48  左移4位 低位补0
     */
    @Test
    public void test5(){

        /**
         * 00000011
         * 00110000
         *=48
         */
        assertEquals((int)(Math.pow(2,5)+Math.pow(2,4)),3<<4);
    }

    /**
     * 32>>3=4  右移3位 高位补0
     */
    @Test
    public void test6(){

        /**
         * 00100000
         * 00000100
         * =4
         */
        assertEquals(4,32>>3);

    }



    @Test
    public void test7(){
    int x =2,y=8;
        x^=y;
        y^=x;
        x^=y;
        System.out.println("x="+x+"**y="+y);
    }
}
