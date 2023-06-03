import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Parser {

    static List<String > header = new ArrayList<>();
    static List<String> numbers= new ArrayList<>();
    static List<List<Integer>> numbersList= new ArrayList<>();
    static int numColums=0;

    /**
     * Die Methode schreibt das Ergebnis erneut zurück in eine Markdowndatei, aber so, dass erneut
     * eine Wahrheitstabelle entsteht
     */
    public static void writeData(String path,  List<List<Integer>> numbers) {
        try {
            // Pfad zur Markdown-Datei angeben
            Path filePath = Paths.get(path);
            List<String> lines = new ArrayList<>();


            lines.add(header.get(0));
            lines.add(header.get(1));


            for (List<Integer> innerList : numbers) {
                lines.add("| " + String.join(" | ", innerList.stream().map(Object::toString).toArray(String[]::new)) + " |");

            }

            Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Der Inhalt wurde erfolgreich in die Markdown-Datei geschrieben.");
        } catch (IOException e) {
            System.out.println("Fehler beim Schreiben in die Markdown-Datei: " + e.getMessage());
        }
    }





    /**
     * Methode liest eine Wahrheitstabelle aus einer Markdown-Datei aus
     * @param datName
     */
    public static void readData(String datName) {
        List<String > rows= new ArrayList<>();

        try {
            byte[] bytes= Files.readAllBytes(Paths.get(datName));
            String s= new String(bytes);
            rows= Arrays.asList(s.split("\n"));
            header.addAll(rows.subList(0, 2));
            numbers.addAll(rows.subList(2, rows.size()));
            System.out.println("NUmmern: "+ numbers);

            for (String nums : numbers) {
                String trimmedNums = nums.trim();
                String[] numArray = trimmedNums.split("\\|");
                for (String num : numArray) {
                    String trimmedNum = num.trim();
                    for (char c : trimmedNum.toCharArray()) {
                        if (c != '0' && c != '1') {
                            throw new IllegalArgumentException("Falsche Werte in der Markdown-Datei!");
                        }
                    }
                }
            }

            numColums= (int) (Math.log(rows.size()-2) / Math.log(2));
            numbersList= extractNumbers(numbers);
            System.out.println(numbersList.size());




        } catch (IOException e) {
            e.printStackTrace();

        }



    }


    /**
     * Hilfsmethode, um eine Liste von Listen von Integern zu erzeugen, in der jeder Eintrag eine Zeile
     * der Markdown-Tabelle darstellt. Hier spielt die Anzahl der Spalten in der Tabelle eine wichtige Rolle,
     * da die größe der einzelnen Einträge in der Liste mit der Anzahl der Spalten wachsen muss
     * @param numbers, alle extrahierten Zahlen aus der Markdown-Tablle
     * @return, Liste von Listen von Integern, wobei jeder Listeneintrag eine Zeile der Markdown-Tabelle darstellt
     */
    public static List<List<Integer>> extractNumbers(List<String> numbers) {
        List<Integer> nums = new ArrayList<>();
        for (String element : numbers) {
            for (int i = 0; i < element.length(); i++) {
                char c = element.charAt(i);
                if (Character.isDigit(c)) {
                 nums.add(Character.getNumericValue(c));
                }

            }

        }
        System.out.println(nums.size());

        List<List<Integer>> result = new ArrayList<>();
        int index = 0;
        while (index + numColums+1 <= nums.size()) {
            List<Integer> sublist = nums.subList(index, index + numColums+1);
            result.add(sublist);
            index += numColums+1;
        }

        System.out.println("Result: "+ result);
        return result;

    }


}
