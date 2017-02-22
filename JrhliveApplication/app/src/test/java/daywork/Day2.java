package daywork;

import org.junit.Test;

/**
 * desc:给定一个数组，请通过冒泡排序分别由小到大和由大到小排列两次。
 * Created by jiarh on 17/2/21 09:21.
 */

public class Day2 {
    int[] data = {1, 2,6,83,309,23,32,12,9,48};

    @Test
    public void sortToLarge() {
        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                if (data[i] > data[j]) {
                    int temp = data[i];
                    data[i] = data[j];
                    data[j] = temp;

                }
            }

            System.out.println(data[i]);
        }
    }

    @Test
    public void sortToSmall() {
        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                if (data[i] < data[j]) {
                    int temp = data[i];
                    data[i] = data[j];
                    data[j] = temp;

                }
            }
            System.out.println(data[i]);
        }
    }


    @Test
    public void smallToLarge(){
        int left = 0;
        int right = data.length-1;
        int currentPosition=0;


        while (left<right){
            for (int i = 0; i <data.length-1 ; i++) {

                if (data[i]>data[i+1]){
                    int temp = data[i];
                    data[i] = data[i+1];
                    data[i+1] = temp;
                    currentPosition=i;
                }

            }

            right=currentPosition;
            for (int i = currentPosition; i >0 ; i--) {
                if (data[i]<data[i-1]){
                    int temp = data[i-1];
                    data[i-1] = data[i];
                    data[i] = temp;
                    currentPosition=i+1;
                }
            }
            left=currentPosition;
        }


        for (int i = 0; i <data.length ; i++) {
            System.out.print(data[i]+",");
        }
    }
}
