package ua.com.juja.serzh.sqlcmd;

import java.util.Arrays;

/**
 * Created by Serzh on 9/12/16.
 */
public class Temp {
    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 5, 7};
        bubbleSort(array, 4);
    }

    private static void bubbleSort(int[] array, int num) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (num < array[middle]) {
                high = middle - 1;
            } else if (num > array[middle]) {
                low = middle + 1;
            } else {
                System.out.println("ans index: " + middle );
                return;
            }
        }
        System.out.println("don't");
    }
}
