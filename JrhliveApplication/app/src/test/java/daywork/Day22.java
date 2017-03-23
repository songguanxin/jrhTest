package daywork;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * desc:
 * 有一个系统，用来接收用户的输入的数字并转化成乱序的英文串，
 如：输入"1" 可能会得到"eon"或"one"或"noe"或"eno"
 输入"7" 可能会得到"seven"或"esnve"或...
 输入"17"可能会得到"seenovne"或"veenonse"

 现在你得到一个来自系统转化后的字符串，请你写出自己的算法，将其转化成原来的数字。要求：
 1.数字可以无序，但必须完整。
 2.时间复杂度尽可能小。
 如：你得到 "etohienngie"， 需要转化成 "819"或"918"或"189"...
 * Created by jiarh on 17/3/21 10:04.
 */

public class Day22 {



    String numEng[]={"zero","one","two","three","four","five","six","seven","eight","nine"};
    String randomStr="otwthrefosinineightexureone";
    @Test
    public void testResult(){

        StringBuffer sb = new StringBuffer();
        StringBuffer ranStrSb = new StringBuffer(randomStr);
        for (int i = 0; i <numEng.length ; i++) {
            int isExist=0;
            char cc[] = numEng[i].toCharArray();
            Map<Integer,Integer> tmap = new HashMap<>();
            for (int j = 0; j <cc.length ; j++) {
                int position=-1;
                if ((position=randomStr.indexOf(cc[j]))>-1){
                    isExist++;
                    tmap.put(j,position);
                }
                if (isExist==cc.length){
                    sb.append(i);
                    for (int k =0;k<tmap.size();k++){

                        ranStrSb.setCharAt(tmap.get(k),'0');

                    }
                    randomStr = ranStrSb.toString();
                }
            }
        }
        System.out.println(sb.toString());
    }


    @Test
    public void testIndex(){

        System.out.println(randomStr.indexOf("n"));
    }


}
