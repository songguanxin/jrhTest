package daywork;

import org.junit.Test;

/**
 * desc:张立彬、贾瑞华和任涛 每人都有3辆车：一辆双门跑车、
 * 一辆四门轿车、一辆五门SUV。每个人以都分别有一辆别克、一辆现代、
 * 一辆奥迪。但同一品牌的汽车门的数量却各不相同：张立彬的别克汽车的门的数量与贾瑞华的现代汽车的门的数量一样；
 * 任涛的别克汽车的门的数量与张立彬的现代汽车的门的数量一样；张立彬的奥迪汽车为双门，
 * 而贾瑞华的奥迪汽车为四门。请列出每个人各个品牌的汽车门数。
 * Created by jiarh on 17/4/27 21:01.
 */

public class Day38 {

    /**
     *
     *
     *            zhang        jia         ren
     *
     *  bieke       5           2          4
     *
     *
     *  xiandai     4          5           2
     *
     *
     *   aodi       2         4            5
     *
     *
     *
     *
     *
     */


    @Test
    public void testChange(){
        String aa = "aabbcc";
        System.out.println(aa.replaceAll("bb",""));

    }
}
