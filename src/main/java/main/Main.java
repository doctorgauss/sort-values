package main;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Программа сортировки содержимого файла.
 * Разработана по тестовому заданию ГК ЦФТ.
 * @author Голубцов Евгений
 * @version 1.0
 */

public class Main {
    public static final String TYPE_STRING = "-s";
    public static final String TYPE_INT = "-i";
    public static final String SORT_BY_INC = "-a";
    public static final String SORT_BY_DEC = "-d";
    public static final Charset OUT_CHARSET = Charset.forName("UTF-8");
    public static final int ACTUAL_NUMBER_ARGUMENTS = 4;

    public static void main(String[] args){
        if (args.length != ACTUAL_NUMBER_ARGUMENTS){
            System.out.println("Число введённых аргументов командной строки не совпадает с актуальным.");
            return;
        }
        Path pathIn = Paths.get(args[0]);
        Path pathOut = Paths.get(args[1]);

        TypeValue typeValue = null;
        if (args[2].equalsIgnoreCase(TYPE_STRING)){
            typeValue = TypeValue.typeString;
        } else if (args[2].equalsIgnoreCase(TYPE_INT)) {
            typeValue = TypeValue.typeInt;
        } else {
            System.out.println("Неправильно указан параметр типа данных.");
            return;
        }

        TypeSorting typeSorting = null;
        if (args[3].equalsIgnoreCase(SORT_BY_INC)){
            typeSorting = TypeSorting.sortByInc;
        } else if (args[3].equalsIgnoreCase(SORT_BY_DEC)) {
            typeSorting = TypeSorting.sortByDec;
        } else {
            System.out.println("Неправильно указан параметр сортировки данных.");
            return;
        }

        try {
            List list;
            if (typeValue == TypeValue.typeInt){
                list = readFromFile(pathIn, Integer::parseInt);
            } else {
                list = readFromFile(pathIn, Function.identity());
            }
            sort(list, typeSorting);
            saveToFile(list, pathOut);
            System.out.println("Данные успешно отсортированы.");
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Метод сортировки данных
     * @param list Список с данными, подлежащий сортировке
     * @param typeSorting Тип сортировки - по возрастанию/убыванию
     */
    public static <T extends Comparable<T>> void sort(List<T> list, TypeSorting typeSorting){
        if (list == null){
            throw new IllegalArgumentException("Список данных не существует.");
        }

        if (list.isEmpty() || list.size() == 1){
            return;
        }

        int i, j;
        T newValue;
        for (i = 1; i < list.size(); i++){
            newValue = list.get(i);
            j = i;
            if (typeSorting == TypeSorting.sortByInc) {
                while (j > 0 && list.get(j - 1).compareTo(newValue) > 0) {
                    list.set(j, list.get(j - 1));
                    j--;
                }
            } else {
                while (j > 0 && list.get(j - 1).compareTo(newValue) < 0) {
                    list.set(j, list.get(j - 1));
                    j--;
                }
            }
            list.set(j, newValue);
        }
    }

    /**
     * Метод для чтения данных из файла; возвращает список данных
     * @param pathIn Путь к файлу с входными данными
     * @return Возвращает список отсортированных данных
     * @throws IOException Может возникнуть исключение при чтении данных из файла
     */

    public static <T> List<T> readFromFile(Path pathIn, Function<String, T> func) throws IOException {
        try {
            return Files.lines(pathIn)
                    .map(func)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IOException("Ошибка чтения из файла: " + pathIn);
        }
    }

    public <T> List<T> readFromFile(Path path, Function<String, T> func) throws IOException {
        return Files.lines(path)
                .map(func)
                .collect(Collectors.toList());
    }

    /**
     * Метод для записи отсортированого списка в файл
     * @param list Список с отсортированными данными
     * @param pathOut Путь к файлу для сохранения отсортированных данных
     * @throws IOException Может возникнуть исключение при записи данных в файл
     */
    public static <T> void saveToFile(List<T> list, Path pathOut) throws IOException{
        try(PrintWriter out = new PrintWriter(Files.newBufferedWriter(pathOut, OUT_CHARSET))) {
            for (T line : list) {
                out.println(line);
            }
            out.flush();
        } catch (IOException e) {
            throw new IOException("Ошибка записи в файл: " + pathOut);
        }
    }
}