package daywork;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
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
    String oldStr5 = "abbcaecdbb";
    String a = "aa";

    /**
     * 1:先获取其字符数组
     * 2：创建截取字段的集合，存起来
     * 3：变量length存储长度，变量str 存储其对应的截取字段
     */
    public void method1(String res) {

        if (null==res||res.equals(""))return;

        char oldChar[] = res.toCharArray();

        int length , maxLen =0;
        String str = "";

        List<Character> list = new ArrayList<>();

        for (int i = 0; i < oldChar.length; i++) {
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


    /**
     * 周
     */
    private List<Integer> ints = new ArrayList<>();
    @Test
    public void TestA1(){

        String str = "ababcdefad";
        StringBuffer sb = new StringBuffer();
        char[] chars = str.toCharArray();

        for(int i = 0; i < chars.length; i ++){
            int index = sb.indexOf(String.valueOf(chars[i]));
            if(index == -1){
                sb.append(chars[i]);
                System.out.println("count = " + sb.length() + ",sb = " + sb.toString());
            }else{
                ints.add(sb.length());
                System.out.println("count = " + sb.length() +",i =" +i + ",sb = " + sb.toString());
                String s = sb.substring(index + 1,sb.length());
                sb.setLength(0);
                sb.append(s);
                sb.append(chars[i]);
                System.out.println("count = " + sb.length() + ",s = " + s +",index =" +index + ",sb = " + sb.toString());
            }
            if(i == chars.length -1){
                System.out.println("sb = " + sb.toString());
                ints.add(sb.length());
            }
        }
//        Collections.sort(list);                                              //升序排列
        Collections.sort(ints,Collections.reverseOrder());                 //降序排列
        System.out.println("----count = " + ints.get(0) +",");
    }


    /**
     * 任
     */
    @Test
    public void findLongestString() {
        final String str = "abbcaecdbb";
        char[] chars = str.toCharArray();
        String result = null;
        StringBuilder temp = new StringBuilder();
        for (char c : chars) {
            if (temp.indexOf(String.valueOf(c)) >= 0) {
                if (result == null || temp.length() > result.length()) {
                    result = temp.toString();
                }
                temp.delete(0, temp.length());
            }
            temp.append(c);
        }
        System.out.println(result);
    }

    /**
     * 宋
     * @param str
     * @return
     */
    public static String[] getNonRepet3(String str) {
        char[] chars = str.toCharArray();
        System.out.println(Arrays.toString(chars));
        // 存放结果
        HashSet<String> results = new HashSet<>();

        HashSet<Character> room = new HashSet<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (!room.add(chars[i])) {
                room.clear();
                room.add(chars[i]);
                results.add(builder.toString());
                builder = new StringBuilder();
                builder.append(chars[i]);
                continue;
            }
            builder.append(chars[i]);
        }
        results.add(builder.toString());
        String[] values = results.toArray(new String[] {});
        System.out.println("排序前： "+Arrays.toString(values));
        Arrays.sort(values, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        System.out.println("排序后：  "+Arrays.toString(values));
        for (int i = values.length - 1; i >= 1; i--) {
            if (values[i].length() > values[i - 1].length()) {
                values =Arrays.copyOfRange(values,i, values.length);
                break;
            }
        }
        System.out.println("最终结果 == "+Arrays.toString(values));
        return values;

    }


    private String str = "ajkda";
    int length;
    boolean b =false;

    @Test
    public void testBin() {
        byte[] arr = str.getBytes();
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }

        int size = list.size();
        List<Byte> listByte = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (listByte.size() == 0) {
                listByte.add(list.get(i));
            } else {
                for (int j = 0; j <listByte.size() ; j++) {
                    if (list.get(i).equals(listByte.get(j))) {
                        b = true;
                        break;
                    }
                }
                if (b) {
                    if (listByte.size()>length) {
                        length = listByte.size();
                    }
                    listByte.clear();
                    listByte.add(list.get(i));
                    b = false;
                }else {
                    listByte.add(list.get(i));
                }
            }
        }
        System.out.println(length);

    }



    @Test
    public void test(){
//        method1(oldStr1);
//        method1(oldStr2);
//        method1(oldStr3);
//        method1(oldStr4);
        method1(oldStr5);
        System.out.println(getNonRepet3(oldStr5));

    }
}


