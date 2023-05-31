import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Parser {

    /**
     * Die Methode schreibt das Ergebnis erneut zurück in eine Markdowndatei, aber so, dass erneut
     * eine Wahrheitstabelle entsteht
     */
    public static void writeData(String path, List<String> header, List<List<Integer>> numbers) {
        try {
            // Pfad zur Markdown-Datei angeben
            Path filePath = Paths.get(path);
            List<String> lines = new ArrayList<>();

            // Header hinzufügen
            lines.add(String.join("",  header));
            lines.add("| --- | --- | --- | --- |");

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
    public static String[] readData(String datName) {
        String text= new String();
        String[] elements= new String[100];

        try {
            byte[] bytes= Files.readAllBytes(Paths.get(datName));
            String s= new String(bytes);
            //String s = Files.readString(Path.of(datName));
            s.split("\\| "+ "\r\n" + " \\|");
            text = s.replace("[", "").replace("]", "").replace(",", "");//.replace("---", "");
            elements = text.split("\\s+");


        } catch (IOException e) {
            e.printStackTrace();

        }

        return elements;

    }

    public static List<List<Integer>> numbers(String[] elements){
        List<Integer> numbers = new ArrayList<>();
        for (String element : elements) {
            if (element.matches("\\d+")) {
                if(Integer.parseInt(element)==0 || Integer.parseInt(element)==1){
                    numbers.add(Integer.parseInt(element));
                }else {
                    throw new IllegalArgumentException("Werte in der MarkdownFile sind nicht korrekt!");
                }
            }
        }

        List<List<Integer>> result = new ArrayList<>();
        int index = 0;
        while (index + 4 <= numbers.size()) {
            List<Integer> sublist = numbers.subList(index, index + 4);
            result.add(sublist);
            index += 4;
        }
        return result;
    }



    public static List<String> header(String[] elements){
        String[] header= Arrays.copyOfRange(elements, 0, 9);
        List<String> headerLIst= Arrays.asList(header);
        return headerLIst;
    }
}
