package main;

import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        int[] array1 = { 9, 5, 8, 6, 1, 3, 7, 4, 2 };
        sort(array1);
        System.out.println(Arrays.toString(array1));
       String[] array2 = { "9", "5", "8", "6", "1", "3", "7", "4", "2" };
        sort(array2);
        System.out.println(Arrays.toString(array2));
    }

    public static void sort(int[] array){
        int i, j, newValue;
        for (i = 1; i < array.length; i++){
            newValue = array[i];
            j = i;
            while (j > 0 && array[j - 1] > newValue){
                array[j] = array[j - 1];
                j--;
            }
            array[j] = newValue;
        }
    }

    public static void sort(String[] array){
        int i, j;
        String newValue;
        for (i = 1; i < array.length; i++){
            newValue = array[i];
            j = i;
            while (j > 0 && array[j - 1].compareTo(newValue) > 0){
                    array[j] = array[j - 1];
                    j--;
            }
            array[j] = newValue;
        }
    }
}
