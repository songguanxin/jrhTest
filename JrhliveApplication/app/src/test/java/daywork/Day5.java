package daywork;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:
 * <p>
 * <p>
 * Given a string, find the length of the longest substring without repeating characters.
 * <p>
 * Examples:
 * <p>
 * Given "abcabcbb", the answer is "abc", which the length is 3.
 * <p>
 * Given "bbbbb", the answer is "b", with the length of 1.
 * <p>
 * Given "pwwkew", the answer is "wke", with the length of 3.
 * Note that the answer must be a substring,"pwke" is a subsequence and not a substring.
 * <p>
 * <p>
 * <p>
 * Created by jiarh on 17/2/23 13:52.
 */

public class Day5 {


    String oldStr1 = "abcabcbb";
    String oldStr2 = "bbbbbb";
    String oldStr3 = "pwwkew";
    String oldStr4 = "okdodopcihdopooijfadjjidlakdjaidjlwo";

    /**
     * 1:先获取其字符数组
     * 2：创建截取字段的集合，存起来
     * 3：变量length存储长度，变量str 存储其对应的截取字段
     */
    public void method1(String res) {

        if (null==res||res.equals(""))return;

        char oldChar[] = res.toCharArray();

        int length = 0, maxLen = 0;
        String str = "";

        List<Character> list = new ArrayList<>();

        for (int i = 0; i < oldChar.length; i++) {
            System.out.println("走了"+i);
                if (!list.contains(oldChar[i])) {
                    list.add(oldChar[i]);
                    length = list.size();
                    if (maxLen < length) {
                        maxLen = length;
                        StringBuffer sb = new StringBuffer();
                        for (Character c : list){
                            sb.append(c.toString());
                        }
                        str=sb.toString();
                    }

                } else {
                    i--;
                    list.clear();

                }
            }
        System.out.println(res+"截取字段最大长度="+maxLen+"字符串="+str);
    }


    int currentPos;
    public void method2(String res){
        if (null==res||res.equals(""))return;
        char oldChar[] = res.toCharArray();

        int length = 0, maxLen = 0;
        String str = "";

        List<Character> list = new ArrayList<>();

        for (int i = 0; i < oldChar.length; i++) {

            System.out.println("走了"+i);
            if (!list.contains(oldChar[i])) {
                list.add(oldChar[i]);
                length = list.size();
                if (maxLen < length) {
                    maxLen = length;
                    StringBuffer sb = new StringBuffer();
                    for (Character c : list){
                        sb.append(c.toString());
                    }
                    str=sb.toString();
                }

                currentPos=i;
            } else {
                i+=currentPos;
                i--;
                list.clear();

            }
        }
        System.out.println(res+"截取字段最大长度="+maxLen+"字符串="+str);
    }

    @Test
    public void test(){
        method1(oldStr1);
        method1(oldStr2);
        method1(oldStr3);
        method1(oldStr4);

        System.out.println("##############################");
        method2(oldStr1);
        method2(oldStr2);
        method2(oldStr3);
        method2(oldStr4);
    }
}
