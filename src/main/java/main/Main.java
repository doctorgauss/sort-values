package main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static final String TYPE_STRING = "s";
    public static final String TYPE_INTEGER = "i";
    public static final String SORT_BY_INC = "a";
    public static final String SORT_BY_DEC = "d";


    public static void main(String[] args) throws Exception{
        Path pathIn = Paths.get(args[0]);
        Path pathOut = Paths.get(args[1]);

        if (args[2].equalsIgnoreCase(TYPE_STRING)){
            List<String> listValue = Files.readAllLines(pathIn);
            String[] array = new String[listValue.size()];
            listValue.toArray(array);
            sort(array);
            System.out.println(Arrays.toString(array));

        } else if (args[2].equalsIgnoreCase(TYPE_INTEGER)){
            List<Integer> listValue = new ArrayList<>();
            Scanner in = new Scanner(pathIn);
            while (in.hasNextInt()){
                listValue.add(in.nextInt());
            }
            Integer[] array = new Integer[listValue.size()];
            listValue.toArray(array);
            sort(array);
            System.out.println(Arrays.toString(array));
        }
    }

    public static void sort(Integer[] array){
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