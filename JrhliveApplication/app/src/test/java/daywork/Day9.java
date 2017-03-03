package daywork;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * desc:
 * 每日一题：从扑克牌中随机抽 5 张牌,判断是不是顺子,即这 5 张牌是不是连续的。
 * 2-10 为数字本身,A 为 1,J 为 11,Q 为 12,K 为 13,而大小王可以看成任意的 数字。
 *
 *
 * Created by jiarh on 17/3/2 09:45.
 */

public class Day9 {
    List<Integer>  pList = new ArrayList<>(54);
    List<Integer> puke = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13);
    int[] five=new int[5];
    boolean shunZi ;
    int X;


    @Before
    public void setUp(){
        getData();
        getFive();
        sort();
        getX();
        isShunzi(X);
    }

    @Test
    public void testResult(){
        for (int i = 0; i <five.length ; i++) {
            System.out.println(five[i]+"");
        }
        if (shunZi){
            System.out.println("是顺子");
        }else {
            System.out.println("不是顺子");
        }
    }


    public void getData(){
        for (int i=0;i<4;i++){
            pList.addAll(puke);
        }
        pList.add(0);
        pList.add(0);
    }

    public void getFive(){
        for (int i = 0; i < 5; i++) {
            five[i]=pList.get(new Random().nextInt(pList.size()));
        }
    }
    private boolean isShunzi(int start) {
        for (int i = start; i <five.length-1 ; i++) {
        if (five[i+1]-five[i]>=2||five[i+1]-five[i]==0){
            return shunZi=false;

        }else {
             shunZi=true;
        }
    }
        return shunZi;
    }

    private void getX() {
        for (int in :five ){
            if (in==0)
                X++;
        }
    }

    private void sort() {
        for (int i = 0; i <five.length ; i++) {
            for (int j = i+1; j <five.length ; j++) {
                if (five[i]>five[j]){
                    int temp = five[i];
                    five[i]=five[j];
                    five[j]=temp;

                }
            }
        }
    }
}
