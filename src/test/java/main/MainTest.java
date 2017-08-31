package main;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

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
    public void testReadFromFile() throws IOException{
        File testFile = folder.newFile("test.txt");
        try (FileWriter fw = new FileWriter(testFile.getAbsolutePath())){

        }



    }
}
