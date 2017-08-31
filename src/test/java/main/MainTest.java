package main;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertArrayEquals;
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
    }

    @Test
    public void testSortException(){
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Список данных не существует.");
        Main.sort(null, TypeSorting.sortByDec);
    }

    @Test
    public void testReadFromFileStringValue(){
        File testFile;
        try {
            testFile = folder.newFile("test.txt");
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
        File testFile;
        try {
            testFile = folder.newFile("test.txt");
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
}
