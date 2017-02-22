package daywork;

import org.junit.Test;

/**
 * desc:用二分法查找数组中某个数字的index
 * Created by jiarh on 17/2/20 09:23.
 */

public class Day1 {

    int[] data = {1, 2,6,83,3,23,32};

    @Test
    public void sort() {
        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                if (data[i] > data[j]) {
                    int temp = data[i];
                    data[i] = data[j];
                    data[j] = temp;

                }
            }

        }
    }

    public void indexOfNum(int[] nums, int targetNum) {

        if (nums == null || nums.length == 0) return;
        int a = 0;
        int b = nums.length-1;
        while (a<b){
            int m = (a+b)/2;
            if (targetNum<nums[m]){
                b = m-1;
            }else if (targetNum>nums[m]){
                a=m+1;
            }else {

             printIndex(targetNum,m);

                return;
            }
        }
        if (a==b||a>b){
            if (targetNum==nums[a])
                printIndex(targetNum,a);
            else
                System.out.println("非法数字");
        }

    }

    private void printIndex(int targetNum,int index) {
        System.out.println("排序后"+targetNum + "的index=" + index);
    }


    @Test
    public void testIndex(){
        sort();
        indexOfNum(data,3);

    }
}
