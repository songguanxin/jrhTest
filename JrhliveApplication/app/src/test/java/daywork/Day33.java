package daywork;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * desc:现在给你一个长度为n的序列, 你需要：1.移除里面重复的元素2.保留最后出现的元素。
 * Created by jiarh on 17/4/27 10:25.
 */

public class Day33 {

    String res[] ={"qqq","aaa","ccc","sss","bbb","ccc","aaa"};


    @Test
    public void testDo(){

        System.out.println(deal());

    }

    private String deal() {
        List<String> source = Arrays.asList(res);
        List<String> result = new ArrayList<>();


        for (int i = source.size()-1; i >=0; i--) {

            if (result.contains(source.get(i))){
                continue;
            }else {
                result.add(0,source.get(i));
            }

        }


        StringBuffer sb = new StringBuffer();
        for (String s : result) {
            sb.append(s+",");
        }

        return sb.toString();
    }

}
