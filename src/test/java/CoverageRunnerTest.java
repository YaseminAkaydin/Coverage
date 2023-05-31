import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoverageRunnerTest {



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
     * Test überprüft, ob unsere Methode für MBÜÜ für Aufgabe 2a aus dem Aufgabenblatt richtig einliest
     * und die richtigen Testcases zurückgibt. Wir haben das Beispiel per Hand gelöst und
     * vergleichen hier mit unserer händischen Lösung.
     */
    @Test
    public void testAufgabe2aMbuu()  {
        String datName = pathToExercises+"Aufgabe2a.md";
        Parser.readData(datName);
        CoverageRunner.mbuuRunner();
        List<List<Integer>> result = new ArrayList<>(Arrays.asList(
                Arrays.asList(0, 0, 0, 1),
                Arrays.asList(1, 0, 0, 0),
                Arrays.asList(0, 1, 0, 0),
                Arrays.asList(0, 0, 1, 1),
                Arrays.asList(1, 0, 1, 0),
                Arrays.asList(0, 1, 1, 1),
                Arrays.asList(1, 1, 1, 0)
        ));
        boolean isEqual=  Utils.compareLists(CoverageRunner.resultMBUU, result);
        assertTrue(isEqual);
    }



    /**
     * Test überprüft, ob unsere Methode für MBÜÜ für Aufgabe 2b aus dem Aufgabenblatt richtig einliest
     * und die richtigen Testcases zurückgibt. Wir haben das Beispiel per Hand gelöst und
     * vergleichen hier mit unserer händischen Lösung.
     */
    @Test
    public void testAufgabe2bMbuu()  {
        String datName = pathToExercises+"Aufgabe2b.md";
        Parser.readData(datName);
        Parser.writeData(pathToResults+ "Aufgabe2bMbuu.md", CoverageRunner.mbuuRunner());
        List<List<Integer>> result = new ArrayList<>(Arrays.asList(
                Arrays.asList(0, 0, 1, 0),
                Arrays.asList(0, 1, 0, 0),
                Arrays.asList(1, 0, 0, 0),
                Arrays.asList(1, 0, 1, 1),
                Arrays.asList(1, 1, 0, 1),
                Arrays.asList(1, 1, 1, 0)
        ));
        boolean isEqual= Utils.compareLists(CoverageRunner.resultMBUU, result);
        assertTrue(isEqual);

    }

    //TODO
    /**
     * Test überprüft, ob unsere Methode für McDC für Aufgabe 2b aus dem Aufgabenblatt richtig einliest
     * und die richtigen Testcases zurückgibt. Wir haben das Beispiel per Hand gelöst und
     * vergleichen hier mit unserer händischen Lösung. Da unsere MCDC-Methode immer das erste Optimum findet, haben
     * wir gleichzeitig überprüft, ob die Lösung 4 Cases beinhaltet.
     */
    @Test
    public void testAufgabe2bMcDc() {
        String datName = pathToExercises+"Aufgabe2b.md";
        Parser.readData(datName);
        Parser.writeData(pathToResults+"mcdcAufgabe2b.md", CoverageRunner.mcDcRunner());

        List<List<Integer>> result = new ArrayList<>(Arrays.asList(
                Arrays.asList(0, 0, 1, 0),
                Arrays.asList(1, 0, 0, 0),
                Arrays.asList(1, 0, 1, 1),
                Arrays.asList(1, 1, 1, 0)
        ));


        boolean isEqual= Utils.compareLists(CoverageRunner.resultMcDc, result);
        assertTrue(isEqual);


    }

    @Test
    public void testExercise1Mbuu(){
        String datName = pathToExercises+"exercise1.md";
        Parser.readData(datName);
        Parser.writeData(pathToResults+"mbuuExercise1.md", CoverageRunner.mbuuRunner());

    }

    @Test
    public void testExercise2Mbuu() {
        String datName = pathToExercises+"exercise2.md";
        Parser.readData(datName);
        Parser.writeData(pathToResults+"mbuuExercise2.md", CoverageRunner.mbuuRunner());

    }

    @Test
    public void testExercise1Mcdc(){
        String datName = pathToExercises+"exercise1.md";
        Parser.readData(datName);
        Parser.writeData(pathToResults+"mcdcExercise1.md", CoverageRunner.mcDcRunner() );


    }

    @Test
    public void testExercise2Mcdc() {
        String datName = pathToExercises+"exercise2.md";
        Parser.readData(datName);
        Parser.writeData(pathToResults+"mcdcExercise2.md", CoverageRunner.mcDcRunner());

    }





    @Test
    public void testGenerateMcDC() {
        String datName = pathToExercises+"example.md";
        Parser.readData(datName);
        Parser.writeData(pathToResults+"mcdcTestResultOfExample.md", CoverageRunner.mcDcRunner());

    }

    @Test
    public void testGenerateMBUU() {
        String datName = pathToExercises+"ex0.md";
        Parser.readData(datName);
        Parser.writeData(pathToResults+"mbuuTestResultOfExample.md", CoverageRunner.mbuuRunner());

    }

    @Test
    public void testListMCDC(){
        List<String> pathNames = new ArrayList<>();
        pathNames.add(pathToExercises+"ex0.md");
        pathNames.add(pathToExercises+"ex2.md");
        CoverageRunner.generateList(pathNames, true);
        String filePathex0 = pathToResults+"ex0McDcTest.md";
        String filePathex2 = pathToResults+"ex2McDcTest.md";

        File file0 = new File(filePathex0);
        File file2 = new File(filePathex2);
        assertTrue(file0.exists());
        assertTrue(file2.exists());


    }

    @Test
    public void testListMBUU(){
        List<String> pathNames = new ArrayList<>();
        pathNames.add(pathToExercises+"ex0.md");
        pathNames.add(pathToExercises+"ex2.md");
        CoverageRunner.generateList(pathNames, false);

        String filePathex0 = pathToResults+"ex0MBUUTest.md";
        String filePathex2 = pathToResults+"ex2MBUUTest.md";

        File file0 = new File(filePathex0);
        File file2 = new File(filePathex2);
        assertTrue(file0.exists());
        assertTrue(file2.exists());

    }


}
