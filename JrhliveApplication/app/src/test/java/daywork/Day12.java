package daywork;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * desc:
 * 假设有一支股票，在过去的一天里看到一组价格(单位;元)分别为[6,3,9,2,5,9,1,4,6]。
 * 根据这组数据计算出，什么时候买入，什么时候卖出收益最大？
 * 比如这题答案为下标为3(2元)买入，下标为5(9元)的时候卖出，收益最大
 * Created by jiarh on 17/3/7 09:24.
 */

public class Day12 {
    //复杂度 n2

    int[] ints = new int[]{6, 3, 9, 2, 5, 9, 1, 4, 6};

    @Test
    public Map<Integer, Integer> sort() {

        Map<Integer, Integer> temp = new HashMap<>();
        int max = 0;
        for (int i = 0; i < ints.length; i++) {
            int Dvalue = 0;
            for (int j = i + 1; j < ints.length - 1; j++) {

                if (Dvalue < ints[j] - ints[i]) {
                    Dvalue = ints[j] - ints[i];
                    if (max < Dvalue) {
                        temp.clear();
                        max = Dvalue;
                        temp.put(i, j);
                    }
                }
            }
        }
        return temp;
    }



}
