import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasse für alle Hilfsmethode, die beim CoverageRunner sowie beim Parser benötigt werden, um gewissen Umwandlungen
 * oder Extrahierungen von Sachen durchzuführen
 */
public class Utils {


    /**
     * Hilfsmethode, die eine Liste zu einer Map umformt. Der Key stellt hier die Werte der Bedingungen dar und der
     * Value ist das Ergebnis einer Zeile.
     * @param numbers, Liste von Listen von Integern, die die einzelnen EInträge in der Tabelle darstellen
     * @return, eine Map, die jede Zeile einer Markdosn-Datei mit Key-Value-Paaren repräsentiert
     */
    public static Map<List<Integer>, Integer> convertListToMap(List<List<Integer>> numbers){
        Map<List<Integer>, Integer> map = new HashMap<>();

        for (List<Integer> innerList : numbers) {
            List<Integer> key = innerList.subList(0, 3);
            Integer value = innerList.get(3);
            map.put(key, value);
        }
        return map;
    }

    public static List<List<Integer>> mapToList(  Map<List<Integer>, Integer> cases  ){
        List<List<Integer>> num= new ArrayList<>();
        List<Integer> nums= new ArrayList<>();
        Map<List<Integer>, Integer> tmp = new HashMap<>(cases);
        for (Map.Entry<List<Integer>, Integer> entry : tmp.entrySet()) {
            List<Integer> entryList = new ArrayList<>(entry.getKey());
            entryList.add(entry.getValue()); num.add(entryList);
        }
        return num;

    }

    /**
     * Hilfsmethode, um den Namen der Markdown-Datei zu extrahieren vom absoluten Pfad
     * @param path, Ausgangspfad, in der sich die Markdown-Datei befindet
     * @return, Name der Md-Datei
     */
    public static String extractFileName(String path) {
        int lastIndex = path.lastIndexOf("/");

        if (lastIndex != -1 && lastIndex < path.length() - 1) {
            return path.substring(lastIndex + 1, path.length()-3);
        } else {
            return null;
        }
    }

    /**
     * Hilfsmethode, die überprüft, ob der Inhalt zweier Listen aus Listen von Integern
     * gleich ist, obwohl diese in einer anderen Reihenfolge vorkommen.
     * @param list1
     * @param list2
     * @return true, falls beide Listen die gleichen Einträge haben
     */
    public static boolean compareLists(List<List<Integer>> list1, List<List<Integer>> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }

        for (List<Integer> innerList1 : list1) {
            boolean foundMatch = false;

            for (List<Integer> innerList2 : list2) {
                if (innerList1.equals(innerList2)) {
                    foundMatch = true;
                    break;
                }
            }

            if (!foundMatch) {
                return false;
            }
        }

        return true;
    }

    /**
     * Hilfsmethode, um vor allen Tests einmal den Ordner "Results" zu leeren
     * @param folder
     */
    public static void deleteFiles(File folder) {
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFiles(file);
                } else {
                    file.delete();
                }
            }
        }
    }

//    public static int countAllSigns(String datName){
//        int count=0;
//        Path filePath= Paths.get(datName);
//        try {
//            byte[] bytes= Files.readAllBytes(filePath);
//            count= bytes.length;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return count;
//
//    }








}
