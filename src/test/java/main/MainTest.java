package main;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MainTest {
    String[] arrayString = {"23", "-5", "jgt", "ert", "1000"};
    Integer[] arrayInt = {45, -6, 0, 145, 2567, -6};

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testSort(){
        List<String> sourceString;
        sourceString = Arrays.asList(arrayString);
        Main.sort(sourceString, TypeSorting.sortByInc);
        String[] expectedString = {"-5", "1000", "23", "ert", "jgt"};
        assertArrayEquals("Ошибка сортировки строковых данных", expectedString, sourceString.toArray());

        List<Integer> sourceInt;
        sourceInt = Arrays.asList(arrayInt);
        Main.sort(sourceInt, TypeSorting.sortByDec);
        Integer[] expectedInteger = {2567, 145, 45, 0, -6, -6};
        assertArrayEquals("Ошибка сортировки целочисленных данных", expectedInteger, sourceInt.toArray());

        List<Integer> sourceEmpty = new ArrayList<>();
        Main.sort(sourceEmpty, TypeSorting.sortByDec);
        Integer[] expectedEmpty = {};
        assertArrayEquals("Ошибка сортировки целочисленных данных", expectedEmpty, sourceEmpty.toArray());
    }

    @Test
    public void testSortException(){
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Список данных не существует.");
        Main.sort(null, TypeSorting.sortByDec);
    }

    @Test
    public void testReadFromFileStringValue(){
        try {
            File testFile = folder.newFile("test.txt");
            Path path = Paths.get(testFile.getAbsolutePath());
            try (FileWriter fw = new FileWriter(path.toString(), true)) {
                for (String str : arrayString) {
                    fw.write(str + "\n");
                }
                fw.flush();
                List<String> list = Main.readFromFile(path, Function.identity());
                assertArrayEquals("Ошибка при чтении строковых данных", arrayString, list.toArray());
            } catch (IOException e) {
                fail("Ошибка при создании тестового файла");
                e.printStackTrace();

            }
        } catch (IOException e) {
            fail("Ошибка при создании тестового файла");
            e.printStackTrace();
        }
    }

    @Test
    public void testReadFromFileIntValue(){
        try {
            File testFile = folder.newFile("test.txt");
            Path path = Paths.get(testFile.getAbsolutePath());
            try (FileWriter fw = new FileWriter(path.toString(), true)) {
                for (int intValue : arrayInt) {
                    fw.write(intValue + "\n");
                }
                fw.flush();
                List<Integer> list = Main.readFromFile(path, Integer::parseInt);
                assertArrayEquals("Ошибка при чтении строковых данных", arrayInt, list.toArray());
            } catch (IOException e) {
                fail("Ошибка при создании тестового файла");
                e.printStackTrace();
            }
        } catch (IOException e) {
            fail("Ошибка при создании тестового файла");
            e.printStackTrace();
        }
    }

    @Test
    public void testReadFromFileException() throws IOException{
        Path path = Paths.get("notExistPath");
        exception.expect(IOException.class);
        exception.expectMessage("Ошибка чтения из файла: " + path);
        Main.readFromFile(path, Integer::parseInt);
    }

    @Test
    public void testSaveToFile() throws IOException{
        File testFile = folder.newFile("test.txt");
        Path path = Paths.get(testFile.getAbsolutePath());
        List<Integer> list = Arrays.asList(arrayInt);
        Main.saveToFile(list, path);
        List<Integer> listAfterRead = Main.readFromFile(path, Integer::parseInt);
        assertArrayEquals("Ошибка при записи данных в файл", arrayInt, listAfterRead.toArray());
    }

    @Test
    public void testSaveToFileException() throws IOException{
        File testFile = folder.newFile("test.txt");
        Path path = Paths.get(testFile.getAbsolutePath());
        testFile.setWritable(false);
        exception.expect(IOException.class);
        exception.expectMessage("Ошибка записи в файл: " + path);
        Main.saveToFile(new ArrayList<Integer>(), path);
    }

    @Test
    public void testMainWrongArgs(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Main.main(new String[0]);
        assertEquals("Число введённых аргументов командной строки не совпадает с актуальным.", outContent.toString());
        System.setOut(null);
    }

    @Test
    public void testMainWrongTypeData(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String[] args = {"in.txt", "out.txt", "-r", "-d"};
        Main.main(args);
        assertEquals("Неправильно указан параметр типа данных.", outContent.toString());
        System.setOut(null);
    }

    @Test
    public void testMainWrongTypeSort(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String[] args = {"in.txt", "out.txt", "-i", "-y"};
        Main.main(args);
        assertEquals("Неправильно указан параметр сортировки данных.", outContent.toString());
        System.setOut(null);
    }

    @Test
    public void testMainCorrectStringData() throws IOException{
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        File inFile = folder.newFile("in.txt");
        Path pathIn = Paths.get(inFile.getAbsolutePath());
        File outFile = folder.newFile("out.txt");
        Path pathOut = Paths.get(outFile.getAbsolutePath());
        try (FileWriter fw = new FileWriter(pathIn.toString(), true)) {
            for (int intValue : arrayInt) {
                fw.write(intValue + "\n");
            }
            fw.flush();
        }

        String[] args = {pathIn.toString(), pathOut.toString(), "-s", "-d"};
        Main.main(args);
        assertEquals("Данные успешно отсортированы.", outContent.toString());
        System.setOut(null);
    }

    @Test
    public void testMainCorrectIntegerData() throws IOException{
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        File inFile = folder.newFile("in.txt");
        Path pathIn = Paths.get(inFile.getAbsolutePath());
        File outFile = folder.newFile("out.txt");
        Path pathOut = Paths.get(outFile.getAbsolutePath());
        try (FileWriter fw = new FileWriter(pathIn.toString(), true)) {
            for (int intValue : arrayInt) {
                fw.write(intValue + "\n");
            }
            fw.flush();
        }

        String[] args = {pathIn.toString(), pathOut.toString(), "-i", "-a"};
        Main.main(args);
        assertEquals("Данные успешно отсортированы.", outContent.toString());
        System.setOut(null);
    }
}
