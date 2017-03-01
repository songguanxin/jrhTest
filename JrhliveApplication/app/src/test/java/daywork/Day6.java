package daywork;

import org.junit.Test;

/**
 * desc: 用快速排序对一个无序数组进行排序
 * Created by jiarh on 17/2/27 17:41.
 */

public class Day6 {

    int[] data = {13, 5,6, 53, 4,3, 44};


    public void testSort(int[] ints, int l, int r) {

        int left = l;
        int right = r;
        int current = ints[l];

        while (left < right) {

            while (left < right && ints[right] >= current) {

                right--;
            }
            if (left < right) {
                int temp = ints[right];
                ints[right] = ints[left];
                ints[left] = temp;
                left++;
            }


            while (left < right && ints[left] <= current) {
                left++;
            }
            if (left < right) {
                int temp = ints[right];
                ints[right] = ints[left];
                ints[left] = temp;
                right--;
            }


        }

        if (left > l) {
            testSort(ints, l, left - 1);
        }
        if (right < r) {
            testSort(ints, left + 1, r);
        }


    }

    @Test
    public void testS() {
        testSort(data, 0, data.length - 1);
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + ",");
        }
    }
}
