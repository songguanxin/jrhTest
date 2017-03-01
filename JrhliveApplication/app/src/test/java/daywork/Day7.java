package daywork;

import org.junit.Test;

/**
 * desc:
 * 把一个给定一个字符串，字符串的大写字母放到字符串的后面，各个字符的相对位置不变，
 * 且不能申请额外的空间。给出你的算法，然后求出算法的时间复杂度。例如：
 * OkhaoPingCeilXu 转换后 khaoingeiluOPCX
 *
 * Created by jiarh on 17/2/28 10:25.
 */

public class Day7 {
    String str = "OkhaoPingCeilXu";


    public String testStr(String str) {
        StringBuilder lowerBuider = new StringBuilder();
        StringBuilder upperBuider = new StringBuilder();
        char[] chars = str.toCharArray();
        for (int i = 0; i <str.length() ; i++) {

            if (Character.isUpperCase(chars[i])){
                upperBuider.append(chars[i]);
            }else {
                lowerBuider.append(chars[i]);
            }
        }

      return  lowerBuider.append(upperBuider.toString()).toString();
    }


    @Test
    public void testResult(){

        System.out.println(testStr(str)+"时间复杂度O(n)");
    }

}
