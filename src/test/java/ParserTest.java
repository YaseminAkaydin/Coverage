import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParserTest {

    static String pathToExercises="/Users/yaseminakaydin/Desktop/SE1Praktikum3/CertifiedTester3/src/test/exercises/";
    static String pathToResults= "//Users/yaseminakaydin/Desktop/SE1Praktikum3/CertifiedTester3/src/test/results/";


    @BeforeAll
    public static void deleteResultsFiles(){
        String folderPath = pathToResults;

        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            Utils.deleteFiles(folder);
            System.out.println("Alle Dateien im Ordner wurden gelöscht.");
        } else {
            System.out.println("Der angegebene Ordner existiert nicht.");
        }
    }

    /**
     * Methode testet, ob die Headerlänge genau 9 entspricht (Bedingungen plus Markdown-Trennzeichen)
     * und ob die Zeilenanzahl 8 entspricht, wenn wir 3 Bedingungen haben
     * @throws IOException
     */
    @Test
    public void testReadDataFromFileCountElements() throws IOException {
        Parser.readData(pathToExercises+ "ex2.md");
        List<String> header= Parser.header;
        List<List<Integer>> numbers= Parser.numbersList;

        Assertions.assertEquals(8, Parser.numbersList.size());
        Assertions.assertEquals(2, Parser.header.size());


    }



    /**
     * Der Test überprüft, ob unsere Einlesemethode richtig funktioniert und überprüft,
     * ob die zurückgegebnen Elemente auch nicht null sind.
     */
    @Test
    public void testReadDataNotNull(){
        String basePath = pathToExercises+"ex2.md";
        int startNumber = 0;
        int endNumber = 7;

        for (int i = startNumber; i <= endNumber; i++) {
            String filePath = basePath + i + ".md";
            Parser.readData(filePath);
            Assertions.assertNotNull(Parser.header);
            Assertions.assertNotNull(Parser.numbers);
        }

    }

    //TODO
    @Test //(expected = IOException.class)
    public void testWriteDaa_IOException() {

    }


    //TODO
    @Test
    public void testWriteData_IOException(){
        String path = "example.md";
        List<String> header = Arrays.asList("Header1", "Header2", "Header3");
        List<List<Integer>> numbers = new ArrayList<>();
        numbers.add(Arrays.asList(1, 2, 3));
        numbers.add(Arrays.asList(4, 5, 6));

        Path nonWritablePath = Paths.get("/nonexistent/directory", path);
        Assertions.assertThrows(NullPointerException.class, ()->{
            Parser.writeData(String.valueOf(nonWritablePath),CoverageRunner.mcDcRunner() );

        });
    }

    @Test
    public void testForNumberCond(){
        String path= "ex8.md";
        Parser.readData(pathToExercises+path);
        Assertions.assertEquals(4, Parser.numColums);

    }

    @Test
    public void testWrongValues(){
        String path= "ex3.md";
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            Parser.readData(pathToExercises+path);
        });


    }




}
