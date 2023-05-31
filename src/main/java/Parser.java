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

            // Zahlen hinzufügen
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
        //String text= new String();
        //String[] elements= new String[Utils.countAllSigns(datName)];
        List<String > rows= new ArrayList<>();

        try {
            byte[] bytes= Files.readAllBytes(Paths.get(datName));
            String s= new String(bytes);
            rows= Arrays.asList(s.split("\n"));
            header.addAll(rows.subList(0, 2));
            numbers.addAll(rows.subList(2, rows.size()));
            numColums= (int) (Math.log(rows.size()-2) / Math.log(2));
            numbersList= extractNumbers(numbers);
            System.out.println(numbersList.size());

            //String s = Files.readString(Path.of(datName));
//            s.split("\\| "+ "\r\n" + " \\|");
//            text = s.replace("[", "").replace("]", "").replace(",", "");//.replace("---", "");
//            elements = text.split("\\s+");


        } catch (IOException e) {
            e.printStackTrace();

        }

        //return elements;

    }

//    public static List<List<Integer>> numbers(String[] elements){
//        List<Integer> numbers = new ArrayList<>();
//        for (String element : elements) {
//            if (element.matches("\\d+")) {
//                if(Integer.parseInt(element)==0 || Integer.parseInt(element)==1){
//                    numbers.add(Integer.parseInt(element));
//                }else {
//                    throw new IllegalArgumentException("Werte in der MarkdownFile sind nicht korrekt!");
//                }
//            }
//        }
//
//        //TODO Methode, die die Anzahl der Spalten in einer tabelle zählt, 4 bei 4 Spalten usw.
//        //auch wichtig bei der Header Methode!!!
//        List<List<Integer>> result = new ArrayList<>();
//        int index = 0;
//        int columns=3;
//        while (index + columns+1 <= numbers.size()) {
//            List<Integer> sublist = numbers.subList(index, index + columns+1);
//            result.add(sublist);
//            index += columns+1;
//        }
//        return result;
//    }
//
//
//
//    public static List<String> header(String[] elements){
//        String[] header= Arrays.copyOfRange(elements, 0, 9);
//        List<String> headerLIst= Arrays.asList(header);
//        return headerLIst;
//    }

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

    public static void main(String[] args) {
        readData("/Users/yaseminakaydin/Desktop/SE1Praktikum3/CertifiedTester3/src/test/exercises/ex0.md");


    }
}
